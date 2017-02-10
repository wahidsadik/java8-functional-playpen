package com.wahidsadik.java8.functional.switchF;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class can be used as multilevel if statement or switch. Though, it won't give you the same performance.
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

	private SwitchDriver(Function<T, U> defaultMapper) {
		this.defaultMapper = defaultMapper;
		this.caseFunctions = new LinkedHashMap<Predicate<T>, Function<T,U>>();
	}

	public static <T,V> SwitchDriver<T,V> from(Function<T, V> defaultMapper) {
		return new SwitchDriver<T,V>(defaultMapper);
	}

	public SwitchDriver<T, U> addCase(Predicate<T> predicate, Function<T, U> mapFunction) {
		this.caseFunctions.put(predicate, mapFunction);
		return this;
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

}
