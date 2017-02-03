package com.wahidsadik.java8.functional.ternary;

import org.junit.Assert;
import org.junit.Test;

public class TernaryDriverTest {
	@Test
	public void testTrue() {
		String value = "ABC";

		TernaryDriver<String> underTest = TernaryDriver.<String> test(() -> true)
			.whenTrue(() -> value)
			.whenFalse(() -> "DEF");

		Object result = underTest.apply();

		Assert.assertEquals(value, result);
	}

	@Test
	public void testFalse() {
		String value = "DEF";

		TernaryDriver<String> underTest = TernaryDriver.<String> test(() -> false)
			.whenTrue(() -> "ABC")
			.whenFalse(() -> value);

		Object result = underTest.apply();

		Assert.assertEquals(value, result);
	}

	@Test
	public void testArray() {

		int[] value = { 4, 5 };

		TernaryDriver<int[]> underTest = TernaryDriver.<int[]> test(() -> false)
			.whenTrue(() -> new int[] { 1, 2, 3 })
			.whenFalse(() -> new int[] { 4, 5 });

		Object result = underTest.apply();

		Assert.assertEquals(value, result);
	}

}