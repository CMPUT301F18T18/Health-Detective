package cmput301f18t18.health_detective.domain.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class Id {

    private static final char[] HEX_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    private Id() {}

    public static String genUniqueId() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

            return genUniqueId(secureRandom);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String genUniqueId(SecureRandom secureRandom) {
        // Verify that secure random has been init'd
        if (secureRandom == null) {
            return null;
        }

        // Get random integer
        Integer randomInt = Integer.valueOf(secureRandom.nextInt());

        // Create SHA 256 from randomInt
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(randomInt.toString().getBytes());
            String id = byteArrayToHexString(hashBytes);

            return id;

        } catch (NoSuchAlgorithmException e) {
            System.console().printf("genUniqueId: Invalid Hash Algorithm");

            return null;
        }
    }

    private static String byteArrayToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }

        String hexString;
        char[] characters = new char[bytes.length * 2];
        int character;

        for (int i = 0; i < bytes.length; i++) {
            character = bytes[i] & 0xFF;
            characters[i * 2] = HEX_CHARACTERS[(character & 0xF0) >>> 4];
            characters[i * 2 + 1] = HEX_CHARACTERS[character & 0x0F];
        }

        hexString = new String(characters);

        return hexString;
    }
}
