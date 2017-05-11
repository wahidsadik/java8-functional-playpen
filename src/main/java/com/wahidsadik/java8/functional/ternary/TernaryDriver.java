package com.wahidsadik.java8.functional.ternary;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * A functional way of creating ternary comparison.
 * 
 * Sample code:
 * 
 * <pre>
 *     TernaryDriver<String> underTest = TernaryDriver.<String> builder()
 *        .whenTrue(() -> {
 *            // lots of code
 *            return "Hello World!"
 *        })
 *        .whenFalse(() -> {
 *            // lots of code
 *            return "Goodbye World!"
 *        })
 *        .build();
 *
 *    String response = underTest.apply(() -> "ABC".length() == 1); // response == "Goodbye World!"
 * </pre>
 * 
 * 
 * @author wsadik
 * @version 1.0.0
 *
 * @param <T>
 *            Type parameter of the return type when the driver is used.
 */
public class TernaryDriver<T> {
    private final Supplier<T> trueSupplier;
    private final Supplier<T> falseSupplier;

    public static <TS> Builder<TS> builder() {
        return new Builder<TS>();
    }

    private TernaryDriver(Supplier<T> trueSupplier, Supplier<T> falseSupplier) {
        this.trueSupplier = trueSupplier;
        this.falseSupplier = falseSupplier;
    }

    public T apply(Supplier<Boolean> booleanSupplier) {
        boolean result = booleanSupplier.get();
        return result ? trueSupplier.get() : falseSupplier.get();
    }

    public static class Builder<T> {
        private Optional<Supplier<T>> trueSupplier = Optional.empty();
        private Optional<Supplier<T>> falseSupplier = Optional.empty();

        private Builder() {
        }

        public Builder<T> whenTrue(Supplier<T> trueSupplier) {
            if (this.trueSupplier.isPresent()) {
                throw new IllegalStateException("trueSupplier has already been set");
            } else {
                this.trueSupplier = Optional.of(trueSupplier);
            }
            return this;
        }

        public Builder<T> whenFalse(Supplier<T> falseSupplier) {
            if (this.falseSupplier.isPresent()) {
                throw new IllegalStateException("falseSupplier has already been set");
            } else {
                this.falseSupplier = Optional.of(falseSupplier);
            }
            return this;
        }

        public TernaryDriver<T> build() {
            if (!trueSupplier.isPresent() || !falseSupplier.isPresent()) {
                throw new IllegalStateException("Not all parameters have been provided");
            } else {
                return new TernaryDriver<>(trueSupplier.get(), falseSupplier.get());
            }
        }

    }

}
