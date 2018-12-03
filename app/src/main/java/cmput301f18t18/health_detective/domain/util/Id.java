package cmput301f18t18.health_detective.domain.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class Id {

    private static final char[] HEX_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private Id() {}

    /**
     * Generates an id string from a secure random by creating a hash from a secure random. chance
     * of collision is negligible
     *
     * @param secureRandom
     * @return
     */
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

    /**
     * Takes a byte array and converts it into a HexString
     *  ex. 0 -> "00"
     *      15 -> "FF"
     *
     * @param bytes
     * @return String
     */
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
