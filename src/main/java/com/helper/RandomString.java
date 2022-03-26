package com.helper;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {
    /**
     * Creates a new random string generator with length, randomizer, and symbols set.
     * @param length The length of the string to be generated.
     * @param random The randomizer to use.
     * @param symbols The set of symbols to choose from.
     * @throws IllegalArgumentException
     * @see #nextString()
     * @see #RandomString(int, Random)
     * @see #RandomString(int)
     * @see #RandomString()
     */
    public RandomString(final int length, final Random random, final String symbols) throws IllegalArgumentException {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    private final Random random;
    private final char[] symbols;
    private final char[] buf;

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVXYZ";
    public static final String lower = upper.toLowerCase(Locale.ROOT);
    public static final String digits = "0123456789";
    public static final String alphanum = upper + lower + digits;

    /**
     * Generates a random String.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

    /**
     * Creates an alphanumeric string generator.
     * @param length The length of the string to generate.
     * @param random The randomizer to use.
     * @see #RandomString(int, Random, String)
     * @see #RandomString(int)
     * @see #RandomString()
     */
    public RandomString(final int length, final Random random) {
        this(length, random, alphanum);
    }

    /**
     * Creates a sercure alphanumeric string generator.
     * @param length The length of the string to generate.
     * @see #RandomString(int, Random, String)
     * @see #RandomString(int, Random)
     * @see #RandomString()
     */
    public RandomString(final int length) {
        this(length, new SecureRandom());
    }

    /**
     * Creates a session identifier.
     * @hidden The length is 21.
     */
    public RandomString() {
        this(21);
    }
}
