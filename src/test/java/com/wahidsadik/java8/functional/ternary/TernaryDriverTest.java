package com.wahidsadik.java8.functional.ternary;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TernaryDriverTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void cannotReassignTrueSupplier() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("trueSupplier has already been set");

        TernaryDriver.<String>builder().whenTrue(() -> "ABC").whenTrue(() -> "ABC");
    }

    @Test
    public void cannotReassignFalseSupplier() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("falseSupplier has already been set");

        TernaryDriver.<String>builder().whenFalse(() -> "ABC").whenFalse(() -> "ABC");
    }

    @Test
    public void mustProvideTrueSupplier() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Not all parameters have been provided");

        TernaryDriver.<String>builder().whenFalse(() -> "ABC").build();
    }

    @Test
    public void mustProvideFalseSupplier() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Not all parameters have been provided");

        TernaryDriver.<String>builder().whenTrue(() -> "ABC").build();
    }

    @Test
    public void testTrue() {
        String value = "ABC";

        TernaryDriver<String> underTest = TernaryDriver.<String>builder().whenTrue(() -> value).whenFalse(() -> "DEF")
                .build();

        Assert.assertEquals(value, underTest.apply(() -> true));
    }

    @Test
    public void testFalse() {
        String value = "DEF";

        TernaryDriver<String> underTest = TernaryDriver.<String>builder().whenTrue(() -> "ABC").whenFalse(() -> value)
                .build();

        Assert.assertEquals(value, underTest.apply(() -> false));
    }

    @Test
    public void driverIsReusable() {
        String value = "DEF";

        TernaryDriver<String> underTest = TernaryDriver.<String>builder().whenTrue(() -> "ABC").whenFalse(() -> value)
                .build();

        Assert.assertEquals(value, underTest.apply(() -> false));
        Assert.assertEquals(value, underTest.apply(() -> false));
    }

    @Test
    public void testArray() {

        int[] value = { 4, 5 };

        TernaryDriver<int[]> underTest = TernaryDriver.<int[]>builder().whenTrue(() -> new int[] { 1, 2, 3 })
                .whenFalse(() -> new int[] { 4, 5 }).build();

        Assert.assertArrayEquals(value, underTest.apply(() -> false));
    }

    @Test
    public void throwsExceptionFromWhenClause() {
        thrown.expect(RuntimeException.class);

        TernaryDriver<String> underTest = TernaryDriver.<String>builder().whenTrue(() -> "ABC").whenFalse(() -> {
            throw new RuntimeException();
        }).build();

        underTest.apply(() -> false);
    }

    @Test
    public void throwsExceptionFromApplyClause() {
        thrown.expect(RuntimeException.class);

        TernaryDriver<String> underTest = TernaryDriver.<String>builder().whenTrue(() -> "ABC").whenFalse(() -> "DEF")
                .build();

        underTest.apply(() -> {
            throw new RuntimeException();
        });
    }

}