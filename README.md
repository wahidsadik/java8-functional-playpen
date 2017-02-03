# java8-functional-playpen
I experiment my Java 8 functional adventures here

## TernaryDriver

This is to scratch an itch I had. With Java, I can do this:


	String name = (hasLastName) 
		? createFullName() 
		: useFirstName(); 

Only one expression is allowed for each of those. There is no way to in-line more than one lines of code. I will have to extract that as method and to do that. Sometimes I don't want to do that.

So here my attempt:

	TernaryDriver<String> underTest = TernaryDriver.<String> test(() -> "ABC".length() == 1)
		.whenTrue(() -> {
			// lots of code
			return "Hello World!"
		})
		.whenFalse(() -> {
			// lots of code
			return "Goodbye World!"
		});

Itch scratched.

