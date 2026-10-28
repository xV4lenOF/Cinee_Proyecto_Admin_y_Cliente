package pe.cinee.admin;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HexFormat;

/** Password storage based on PBKDF2-HMAC-SHA256, with a unique random salt per account. */
public final class PasswordSecurity {
    private static final int ITERATIONS = 210_000;
    private static final int KEY_BITS = 256;
    private static final int SALT_BYTES = 16;
    private static final SecureRandom RANDOM = new SecureRandom();
    private PasswordSecurity() {}

    public record Encoded(String saltHex, String hashHex) {}

    public static Encoded encode(char[] password) {
        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        return new Encoded(HexFormat.of().formatHex(salt), HexFormat.of().formatHex(derive(password, salt)));
    }

    public static boolean verify(char[] password, String saltHex, String hashHex) {
        try {
            byte[] salt = HexFormat.of().parseHex(saltHex);
            byte[] expected = HexFormat.of().parseHex(hashHex);
            byte[] actual = derive(password, salt);
            return MessageDigest.isEqual(expected, actual);
        } catch (IllegalArgumentException ex) { return false; }
    }

    private static byte[] derive(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_BITS);
        try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
        catch (GeneralSecurityException e) { throw new IllegalStateException("No se pudo proteger la contraseña.", e); }
        finally { spec.clearPassword(); }
    }
}
