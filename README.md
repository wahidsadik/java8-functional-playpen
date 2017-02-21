# java8-functional-playpen
I experiment my Java 8 functional adventures here

## TernaryDriver

This is to scratch an itch I had. With Java, I can do this:


	String name = (hasLastName)
		? createFullName()
		: useFirstName();

Only one expression is allowed for each of those. There is no way to in-line more than one lines of code. I will have to extract that as method and to do that. Sometimes I don't want to do that.

So here is my attempt:

	TernaryDriver<String> underTest = TernaryDriver.<String> builder()
		.whenTrue(() -> {
			// lots of code
			return "Hello World!"
		})
		.whenFalse(() -> {
			// lots of code
			return "Goodbye World!"
		})
		.build();

	String response = underTest.apply(() -> "ABC".length() == 1); // response == "Goodbye World!"

Itch scratched. This also makes the code block reusable.

## SwitchDriver

Functional way of creating `switch`. 

Example:

		SwitchDriver<String, Integer> underTest = SwitchDriver.<String, Integer> builder()
			.defaultClause(word -> word.length())
			.addCase(word -> word.equals("BCD"), word -> word.length() + 10)
			.addCase(word -> word.equals("CDE"), word -> word.length() + 100)
			.build();
		
		//Use a supplier to pass a value to SwitchDriver
		underTest.apply(() -> "ABC").intValue();//returns 3
		
		//Or, use a direct value to SwitchDriver
		underTest.apply("ABC").intValue();//returns 3

## IfDriver

Coming up!

