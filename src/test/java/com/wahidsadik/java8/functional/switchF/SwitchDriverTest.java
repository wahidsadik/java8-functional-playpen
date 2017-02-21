package com.wahidsadik.java8.functional.switchF;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SwitchDriverTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void mustProvideDefaultMapper() {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Not all parameters have been provided");

		SwitchDriver.<String, Integer> builder().build();
	}

	@Test
	public void cannotReassignParameters() {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("defaultMapper has already been set");

		SwitchDriver.<String, Integer> builder().defaultClause(word -> word.length()).defaultClause(word -> word.length());
	}

	@Test
	public void testBasics() {
		SwitchDriver<String, Integer> underTest = SwitchDriver.<String, Integer> builder()
			.defaultClause(word -> word.length())
			.addCase(word -> word.equals("BCD"), word -> word.length() + 10)
			.addCase(word -> word.equals("CDE"), word -> word.length() + 100)
			.build();

		assertEquals(3, underTest.apply(() -> "ABC").intValue());
		assertEquals(13, underTest.apply(() -> "BCD").intValue());
		assertEquals(103, underTest.apply(() -> "CDE").intValue());

		assertEquals(103, underTest.apply("CDE").intValue());
	}

	@Test
	public void throwsExceptionFromDefaultMapper() {
		thrown.expect(RuntimeException.class);
		
		SwitchDriver<String, Integer> underTest = SwitchDriver.<String, Integer> builder()
			.defaultClause(word -> {
				throw new RuntimeException();
			})
			.build();
		
		underTest.apply("ABC");
	}
	
	@Test
	public void throwsExceptionFromCaseClausePredicate() {
		thrown.expect(RuntimeException.class);
		
		SwitchDriver<String, Integer> underTest = SwitchDriver.<String, Integer> builder()
			.defaultClause(word -> word.length())
			.addCase(word -> {
				throw new RuntimeException();
			}, word -> word.length() + 10)
			.build();
		
		underTest.apply("ABC");
	}

	@Test
	public void throwsExceptionFromCaseClauseMap() {
		thrown.expect(RuntimeException.class);
		
		SwitchDriver<String, Integer> underTest = SwitchDriver.<String, Integer> builder()
			.defaultClause(word -> word.length())
			.addCase(word -> word.equals("BCD"), word -> {
				throw new RuntimeException();
			})
			.build();
		
		underTest.apply("BCD");
	}

	@Test
	public void throwsExceptionFromApply() {
		thrown.expect(RuntimeException.class);
		
		SwitchDriver<String, Integer> underTest = SwitchDriver.<String, Integer> builder()
			.defaultClause(word -> word.length())
			.addCase(word -> word.equals("BCD"), word -> word.length() + 10)
			.build();
		
		underTest.apply(() -> {
			throw new RuntimeException();
		});
	}

}