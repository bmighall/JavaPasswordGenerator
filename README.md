# Java Secure Password Generator
Creator: Ben Mighall

This secure password generator is built using Java's "SecureRandom" functionality, and generates valid, secure passwords for use on websites. 

The code is relatively simple, with four methods. First, the two helper methods: 
* mergeCharLists(): Merges an array of char arrays (or charList, as it's referred to in the program) passed to it in the parameters into a single character list, randomizes it using SecureRandom, then returns it
* validatePassword(): Most websites require at least one uppercase letter, one number, and one lowercase if alphanumeric passwords are supported, and at least one symbol if symbols are allowed. The method validates the password String passed through parameters based on those conditions. The method requires that a "symbols" boolean is marked as true or false when the method is called to ensure that symbols should be included in the validation (true) or if the password is only alphanumeric (false). If the password fails to meet a requirement (at least one of each type mentioned above), then the password has one random character replaced with a randomly selected character from the pertinent list (randomized using SecureRandom), then is revalidated with a recursive call to ensure that the new password meets the requirements. The password is returned once validated.

Finally, the two password generating methods:
* alphaNumeric(): This generates a randomized password using the character lists for uppercase letters, numerals, and lowercase letters- which are merged using mergeCharLists()- using a simple for loop and SecureRandom functionality. The password is then validated using validatePassword(). The length of the password is specified using the parameters.
* standardMode(): This generates a randomized password using the character lists for uppercase letters, numerals, lowercase letters, and the most common symbols allowed for website passwords- which are merged using mergeCharLists()- using a simple for loop and SecureRandom functionality. The password is then validated using validatePassword(). The length of the password is specified using the parameters.

