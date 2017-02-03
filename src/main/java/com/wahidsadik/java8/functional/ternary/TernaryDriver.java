package com.wahidsadik.java8.functional.ternary;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * 
 * @author wsadik
 *
 * @param <T>
 */
public class TernaryDriver<T> {
	private final BooleanSupplier booleanSupplier;
	private Optional<Supplier<T>> trueSupplier;
	private Optional<Supplier<T>> falseSupplier;

	private TernaryDriver(BooleanSupplier booleanSupplier) {
		this.booleanSupplier = booleanSupplier;
		this.trueSupplier = Optional.empty();
		this.falseSupplier = Optional.empty();
	}

	public static <U> TernaryDriver<U> test(BooleanSupplier booleanSupplier) {
		return new TernaryDriver<U>(booleanSupplier);
	}

	public TernaryDriver<T> whenTrue(Supplier<T> trueSupplier) {
		if (this.trueSupplier.isPresent()) {
			throw new IllegalArgumentException("trueSupplier has already been set");
		} else {
			this.trueSupplier = Optional.of(trueSupplier);
		}
		return this;
	}

	public TernaryDriver<T> whenFalse(Supplier<T> falseSupplier) {
		if (this.falseSupplier.isPresent()) {
			throw new IllegalArgumentException("falseSupplier has already been set");
		} else {
			this.falseSupplier = Optional.of(falseSupplier);
		}
		return this;
	}

	public T apply() {
		boolean result = booleanSupplier.getAsBoolean();
		return result ? trueSupplier.get().get() : falseSupplier.get().get();
	}

}
