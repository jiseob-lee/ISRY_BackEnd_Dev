package isry.itgcms.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomString {
	
	private static final String ALPHABET = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
	
	private static final String NUMERIC = "1234567890";
	
	public static String generate(int length) {
	    return IntStream.range(0, length)
	            .map(i -> ThreadLocalRandom.current().nextInt(ALPHABET.length()))
	            .mapToObj(i -> ALPHABET.substring(i, i + 1))
	            .collect(Collectors.joining());
	}
	
	public static String generateNumeric(int length) {
	    return IntStream.range(0, length)
	            .map(i -> ThreadLocalRandom.current().nextInt(NUMERIC.length()))
	            .mapToObj(i -> NUMERIC.substring(i, i + 1))
	            .collect(Collectors.joining());
	}
	
	/*
	public static void main(String[] args) {
		System.out.println(generate(6));
	}
	*/

}
