package game;

import com.if23b212.mtcg.db.DatabaseRunner;
import com.if23b212.mtcg.exception.user.UserExceptionHelper;
import com.if23b212.mtcg.model.user.UserCredentials;
import com.if23b212.mtcg.service.user.UserService;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    DatabaseRunner db = new DatabaseRunner();

    @Test
    public void registerOneUserTest() {
        db.initializeDatabase();
        UserService userService = new UserService();
        UserCredentials user1 = new UserCredentials("mine", "hallo123");

        try {
            userService.saveUser(user1);
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(false);
        }
    }

    @Test
    public void registerTwoUserTest() {
        db.initializeDatabase();
        UserService userService = new UserService();
        UserCredentials user1 = new UserCredentials("mine", "hallo123");
        UserCredentials user2 = new UserCredentials("sana" , "blau123");

        try {
            userService.saveUser(user1);
            userService.saveUser(user2);
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(false);
        }
    }

    @Test
    public void alreadyRegisteredTest() {
        db.initializeDatabase();
        UserService userService = new UserService();
        UserCredentials user1 = new UserCredentials("mine", "hallo123");
        UserCredentials user2 = new UserCredentials("mine" , "hallo123");

        userService.saveUser(user1);

        assertThrows(UserExceptionHelper.AlreadyRegisteredException.class, ()-> userService.saveUser(user2));
    }

    @Test
    public void loginUserTest() {
        db.initializeDatabase();
        UserService userService = new UserService();
        UserCredentials user1 = new UserCredentials("mine", "hallo123");
        try {
            userService.saveUser(user1);
            String token = userService.loginUser(user1);
            assertEquals("mine-mtcgToken", token);
        } catch (Exception e) {
            assertFalse(false);
        }
    }

    @Test
    public void failedLoginUserTest() {
        db.initializeDatabase();
        UserService userService = new UserService();
        UserCredentials user1 = new UserCredentials("mine", "hallo123");

        assertThrows(UserExceptionHelper.InvalidCredentialsException.class, ()-> userService.loginUser(user1));

    }

}
