/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import java.util.Random;

/**
 *
 * @author JOHN
 */
public class RandomString {

    private final char[] alphanumeric = alphanumeric();
    private final Random rand;

    public RandomString() {
        this(null);
    }

    public RandomString(Random rand) {
        this.rand = (rand != null) ? rand : new Random();
    }

    public String get(int len) {
        StringBuilder out = new StringBuilder();
        while (out.length() < len) {
            int idx = Math.abs((rand.nextInt() % alphanumeric.length));
            out.append(alphanumeric[idx]);
        }
        return out.toString();
    }

    // create alphanumeric char array
    private char[] alphanumeric() {
        StringBuilder buf = new StringBuilder(128);
        for (int i = 48; i <= 57; i++) {
            buf.append((char) i); // 0-9
        }
        for (int i = 65; i <= 90; i++) {
            buf.append((char) i); // A-Z
        }
        for (int i = 97; i <= 122; i++) {
            buf.append((char) i); // a-z
        }
        return buf.toString().toCharArray();
    }
}
