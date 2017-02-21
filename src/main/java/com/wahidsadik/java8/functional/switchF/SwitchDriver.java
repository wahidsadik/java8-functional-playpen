package com.wahidsadik.java8.functional.switchF;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Functional way of creating `switch`. All the levels must return the same output. Each level is guarded by a predicate.
 *
 * Example:
 * <pre>
 * 		SwitchDriver<String, Integer> underTest = SwitchDriver.<String, Integer> builder()
 * 			.defaultClause(word -> word.length())
 * 			.addCase(word -> word.equals("BCD"), word -> word.length() + 10)
 * 			.addCase(word -> word.equals("CDE"), word -> word.length() + 100)
 * 			.build();
 * 		
 * 		//Use a supplier to pass a value to SwitchDriver
 * 		underTest.apply(() -> "ABC").intValue();//returns 3
 * 		
 * 		//Or, use a direct value to SwitchDriver
 * 		underTest.apply("ABC").intValue();//returns 3
 * </pre>
 * 
 * @author wsadik
 * @version 1.0.0
 *
 * @param <T> Type parameter of the return type when the driver is used.
 */
public class SwitchDriver<T, U> {
	private final Function<T, U> defaultMapper;
	private Map<Predicate<T>, Function<T, U>> caseFunctions;

	public static <TS, US> Builder<TS, US> builder() {
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
