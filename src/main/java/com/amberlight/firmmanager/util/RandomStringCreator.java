package com.amberlight.firmmanager.util;

import java.util.Random;

/**
 * An accessory class for creating random strings with different size.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
public class RandomStringCreator {

    /**
     * Pool of chars for creating random strings.
     */
    private static final char[] pool = {
            'a','b','c','d','e','f','g',
            'h','i','j','k','l','m','n',
            'o','p','q','r','s','t','u',
            'v','w','x','y','z','0','1',
            '2', '3','4','5','6','7','8','9'};

    /**
     * Create a random string with the certain size passed as a parameter.
     * @param size the size of random string
     * @return the random string
     */
    public String getRandomString(int size) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++)
            sb.append(pool[random.nextInt(pool.length)]);
        return new String(sb);
    }

    @Override
    public String toString() {
        return "RandomStringCreator{" +
                "pool[]:" + pool +
                "}";
    }
}
