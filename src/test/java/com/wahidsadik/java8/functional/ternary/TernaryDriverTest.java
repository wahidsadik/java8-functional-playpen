package com.wahidsadik.java8.functional.ternary;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TernaryDriverTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testTrue() {
		String value = "ABC";

		TernaryDriver<String> underTest = TernaryDriver.<String> test(() -> true)
			.whenTrue(() -> value)
			.whenFalse(() -> "DEF");

		Assert.assertEquals(value, underTest.apply());
	}

	@Test
	public void testFalse() {
		String value = "DEF";

		TernaryDriver<String> underTest = TernaryDriver.<String> test(() -> false)
			.whenTrue(() -> "ABC")
			.whenFalse(() -> value);

		Assert.assertEquals(value, underTest.apply());
	}

	@Test
	public void driverIsReusable() {
		String value = "DEF";

		TernaryDriver<String> underTest = TernaryDriver.<String> test(() -> false)
			.whenTrue(() -> "ABC")
			.whenFalse(() -> value);

		Assert.assertEquals(value, underTest.apply());
		Assert.assertEquals(value, underTest.apply());
	}

	@Test
	public void testArray() {

		int[] value = { 4, 5 };

		TernaryDriver<int[]> underTest = TernaryDriver.<int[]> test(() -> false)
			.whenTrue(() -> new int[] { 1, 2, 3 })
			.whenFalse(() -> new int[] { 4, 5 });

		Assert.assertEquals(value, underTest.apply());
	}

	@Test
	public void cannotReassignConstructorParameters() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("trueSupplier has already been set");
		
		TernaryDriver.<String> test(() -> false)
			.whenTrue(() -> "ABC")
			.whenTrue(() -> "ABC");
	}


}