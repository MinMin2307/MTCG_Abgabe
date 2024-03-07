package util;

import com.if23b212.mtcg.util.PasswordUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordUtilTest {

    @Test
    public void comparePassword() {
        String plainPassword = "password";
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);

        assertTrue(PasswordUtils.checkPassword(plainPassword, hashedPassword));
    }

    @Test
    public void comparePasswordNULL() {
        String plainPassword = null;
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);

        assertNull(hashedPassword);
    }

}
