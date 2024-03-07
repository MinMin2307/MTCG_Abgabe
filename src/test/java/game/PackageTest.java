package game;

import com.if23b212.mtcg.db.DatabaseRunner;
import com.if23b212.mtcg.model.card.*;
import com.if23b212.mtcg.model.card.Package;
import com.if23b212.mtcg.model.user.UserCredentials;
import com.if23b212.mtcg.service.card.PackageService;
import com.if23b212.mtcg.service.user.UserService;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class PackageTest {

    DatabaseRunner db = new DatabaseRunner();

    @Test
    public void createPackageTest() {
        db.initializeDatabase();
        PackageService packageService = new PackageService();

        CardData card1 = new CardData(UUID.randomUUID(), "WaterGoblin", 35);
        CardData card2 = new CardData(UUID.randomUUID(), "EarthGoblin", 35);
        CardData card3 = new CardData(UUID.randomUUID(), "NormalGoblin", 35);
        CardData card4 = new CardData(UUID.randomUUID(), "WaterDragon", 45);
        CardData card5 = new CardData(UUID.randomUUID(), "EarthDragon", 45);

        List<CardData> cards = List.of(card1,card2,card3,card4,card5);
        try {
            packageService.savePackage(cards);
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(false);
        }
    }

    @Test
    public void failedPackageCreationTest() {
        db.initializeDatabase();
        PackageService packageService = new PackageService();
        UUID packageId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Card card1 = new Card(UUID.randomUUID(), "WaterGoblin", 35, CardType.MONSTER, ElementType.WATER, userId, packageId);
        Card card2 = new Card(UUID.randomUUID(), "EarthGoblin", 35, CardType.MONSTER, ElementType.WATER, userId, packageId);
        Card card3 = new Card(UUID.randomUUID(), "NormalGoblin", 35, CardType.MONSTER, ElementType.WATER, userId, packageId);
        Card card4 = new Card(UUID.randomUUID(), "WaterDragon", 45, CardType.MONSTER, ElementType.WATER, userId, packageId);

        List<Card> cards = List.of(card1,card2,card3,card4);

        assertThrows(IllegalArgumentException.class, ()-> new Package(packageId, cards));
    }

    @Test
    public void buyPackageTest() {
        db.initializeDatabase();
        PackageService packageService = new PackageService();
        UserService userService = new UserService();

        UserCredentials user1 = new UserCredentials("mine", "hallo123");
        userService.saveUser(user1);

        try {
            packageService.buyPackage(user1.getUsername());
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(false);
        }
    }


}
