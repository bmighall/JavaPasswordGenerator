/* 
* Password Generator
* Ben Mighall
* July 1, 2019
*/

import java.security.SecureRandom;

public class PasswordGenerator {
	
	private final char [] uppercase = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private final char [] lowercase = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private final char [] numList = {'0','1','2','3','4','5','6','7','8','9'};
	private final char [] limitedSymbols = {'+','-','#','$','%','&','*','!','@','=','?','^','_','~'};
	
	//Generate alphanumeric password from passed-in length and CSPRNG functionality (only a-z, A-Z, 0-9)
	public String alphaNumeric(int length) {
		
		String generated = "";
		char [][] toMerge = {uppercase,lowercase,numList};
		char [] charList = mergeCharLists(toMerge);
		
		SecureRandom rand = new SecureRandom();
		
		for(int i = 0; i < length; i++) {
			generated += charList[rand.nextInt(charList.length)];
		}
		
		generated = validatePassword(generated,false);
		
		return generated;
	}

	//Generate password from passed-in length, boolean array of symbol choices and CSPRNG functionality 
	//alphanumeric with limited symbols + - # $ % & * ! @ = ? ^ _ ~
	public String standardMode(int length) {
		
		String generated = "";
		char [][] toMerge = {uppercase,lowercase,numList,limitedSymbols};
		char [] charList = mergeCharLists(toMerge);
		
		SecureRandom rand = new SecureRandom();
		
		for(int i = 0; i < length; i++) {
			generated += charList[rand.nextInt(charList.length)];
		}
		
		generated = validatePassword(generated,true);
		
		return generated;
	}
	
	//method to merge char lists
	private char[] mergeCharLists(char [][] lists) {
		
		int totalLength = 0;
		
		for(int i = 0; i < lists.length; i++) {
			totalLength += lists[i].length;
		}
		
		char [] charList = new char[totalLength];
		int index = 0;
		
		for(int i = 0; i < lists.length; i++) {
			for(int j = 0; j < lists[i].length; j++) {
				charList[index] = lists[i][j];
				index++;
			}
		}
		
		//Randomizing the entries in the charlist to add additional level of security
		SecureRandom rand = new SecureRandom();
		
		for(int i = 0; i < charList.length; i++) {
			int swap = rand.nextInt(charList.length);
			char temp = charList[swap];
			charList[swap] = charList[i];
			charList[i] = temp;
		}
		
		return charList;
	}
	
	//method to ensure that the password meets the requirements for most sites- at least one uppercase, one lowercase, one number
	//boolean in parameters is whether or not symbols should be checked for in the password validation (namely, if the password is alphanumeric only, it will be false; else, true)
	public String validatePassword(String p, boolean symbols) {
		String password = p;
		SecureRandom rand = new SecureRandom();
		boolean hasNumbers = false;
		boolean hasUppercase = false;
		boolean hasLowercase = false;
		boolean hasSymbols = false;
		
		for(int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);
			if(Character.isDigit(c)) {
				hasNumbers = true;
			}else if(Character.isUpperCase(c)) {
				hasUppercase = true;
			}else if(Character.isLowerCase(c)) {
				hasLowercase = true;
			}else {
				hasSymbols = true;
			}
		}
		
		int replaceIndex = -1;
		int charSelect = -1;
		
		if(symbols && !hasSymbols) {
			replaceIndex = rand.nextInt(password.length());
			charSelect = rand.nextInt(limitedSymbols.length);
			if(charSelect == password.length()-1) { //to prevent problems with the character replacement
				replaceIndex = rand.nextInt(password.length()-1);
			}
			password = password.substring(0, replaceIndex) + limitedSymbols[charSelect] + password.substring((replaceIndex+1));
		}
		
		if(!hasNumbers) {
			replaceIndex = rand.nextInt(password.length());
			charSelect = rand.nextInt(numList.length);
			if(charSelect == password.length()-1) { //to prevent problems with the character replacement
				replaceIndex = rand.nextInt(password.length()-1);
			}
			password = password.substring(0, replaceIndex) + numList[charSelect] + password.substring((replaceIndex+1));
		}
		
		if(!hasUppercase) {
			replaceIndex = rand.nextInt(password.length());
			charSelect = rand.nextInt(uppercase.length);
			if(charSelect == password.length()-1) { //to prevent problems with the character replacement
				replaceIndex = rand.nextInt(password.length()-1);
			}
			password = password.substring(0, replaceIndex) + uppercase[charSelect] + password.substring((replaceIndex+1));
		}
		
		if(!hasLowercase) {
			replaceIndex = rand.nextInt(password.length());
			charSelect = rand.nextInt(lowercase.length);
			if(charSelect == password.length()-1) { //to prevent problems with the character replacement
				replaceIndex = rand.nextInt(password.length()-1);
			}
			password = password.substring(0, replaceIndex) + lowercase[charSelect] + password.substring((replaceIndex+1));
			
		}
		
		if(!hasLowercase || !hasNumbers || !hasUppercase || (!hasSymbols&&symbols)) {
			//To make sure that the password really is the way it should be, return is a recursive call to validatePassword; it will re-validate the password to make sure that the correct standards are met.
			System.out.println("Revalidating password.");
			password = validatePassword(password,symbols);
		}
		
		return password;
	}
	
}
