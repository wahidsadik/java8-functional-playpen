package com.wahidsadik.java8.functional.switchF;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class can be used as switch. Though, it won't give you the same performance.
 * 
 * One issue with multilevel if approach is this: it forces use the same object type to pass thru the predicate. We need any kind of predicate.
 * 
 * @author wsadik
 *
 * @param <T>
 */
public class SwitchDriver<T, U> {
	private final Function<T, U> defaultMapper;
	private Map<Predicate<T>, Function<T, U>> caseFunctions;

	public static <TS, US> Builder<TS, US> of() {
		return new Builder<TS, US>();
	}
	
	private SwitchDriver(Function<T, U> defaultMapper, Map<Predicate<T>, Function<T, U>> caseFunctions) {
		this.defaultMapper = defaultMapper;
		this.caseFunctions = caseFunctions;
	}

	public U apply(T t) {		
		for(Map.Entry<Predicate<T>, Function<T, U>> entry: caseFunctions.entrySet()) {
			Predicate<T> predicate = entry.getKey();
			if(predicate.test(t)) {
				Function<T, U> mapFunction = entry.getValue();
				return mapFunction.apply(t);
			}
		}
		
		return defaultMapper.apply(t);
	}

	public U apply(Supplier<T> valueSupplier) {
		return apply(valueSupplier.get());
	}
	
	public static class Builder<T, U> {
		private Optional<Function<T, U>> defaultMapper = Optional.empty();
		private Map<Predicate<T>, Function<T, U>> caseFunctions = new LinkedHashMap<Predicate<T>, Function<T,U>>();
		
		private Builder() {}
		
		public Builder<T, U> defaultClause(Function<T, U> defaultMapper) {
			if (this.defaultMapper.isPresent()) {
				throw new IllegalStateException("defaultMapper has already been set");
			} else {
				this.defaultMapper = Optional.of(defaultMapper);
			}
			return this;
		}
		
		public Builder<T, U> addCase(Predicate<T> predicate, Function<T, U> mapFunction) {
			this.caseFunctions.put(predicate, mapFunction);
			return this;
		}

		public SwitchDriver<T, U> build() {
			if (!defaultMapper.isPresent()) {
				throw new IllegalStateException("Not all parameters have been provided");
			} else {
				return new SwitchDriver<>(defaultMapper.get(), caseFunctions);
			}
		}

	}
	
	

}
