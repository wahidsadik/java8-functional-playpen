package com.wahidsadik.java8.functional.switchF;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SwitchDriverTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testBasics() {
		SwitchDriver<String, Integer> underTest = SwitchDriver.<String, Integer> from(word -> word.length())
			.addCase(word -> word.equals("BCD"), word -> word.length() + 10)
			.addCase(word -> word.equals("CDE"), word -> word.length() + 100);
		
		assertEquals(3, underTest.apply(() -> "ABC").intValue());
		assertEquals(13, underTest.apply(() -> "BCD").intValue());
		assertEquals(103, underTest.apply(() -> "CDE").intValue());
		
		assertEquals(103, underTest.apply("CDE").intValue());
	}
	
	//TODO: Add checks in implementation for duplicate predicate
	//TODO: Test with null
	//TODO: Test with void type
	//TODO: Test with unchecked exception
	//TODO: Test with checked exception

//	@Test
//	public void testFalse() {
//		String value = "DEF";
//
//		TernaryDriver<String> underTest = TernaryDriver.<String> test(() -> false)
//			.whenTrue(() -> "ABC")
//			.whenFalse(() -> value);
//
//		Assert.assertEquals(value, underTest.apply());
//	}
//
//	@Test
//	public void driverIsReusable() {
//		String value = "DEF";
//
//		TernaryDriver<String> underTest = TernaryDriver.<String> test(() -> false)
//			.whenTrue(() -> "ABC")
//			.whenFalse(() -> value);
//
//		Assert.assertEquals(value, underTest.apply());
//		Assert.assertEquals(value, underTest.apply());
//	}
//
//	@Test
//	public void testArray() {
//
//		int[] value = { 4, 5 };
//
//		TernaryDriver<int[]> underTest = TernaryDriver.<int[]> test(() -> false)
//			.whenTrue(() -> new int[] { 1, 2, 3 })
//			.whenFalse(() -> new int[] { 4, 5 });
//
//		Assert.assertEquals(value, underTest.apply());
//	}
//
//	@Test
//	public void cannotReassignConstructorParameters() {
//		thrown.expect(IllegalArgumentException.class);
//		thrown.expectMessage("trueSupplier has already been set");
//		
//		TernaryDriver.<String> test(() -> false)
//			.whenTrue(() -> "ABC")
//			.whenTrue(() -> "ABC");
//	}


}