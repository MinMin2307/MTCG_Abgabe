package game;

import com.if23b212.mtcg.db.DatabaseRunner;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.game.BattleStatus;
import com.if23b212.mtcg.model.user.UserCredentials;
import com.if23b212.mtcg.service.card.CardService;
import com.if23b212.mtcg.service.card.DeckService;
import com.if23b212.mtcg.service.demo.DemoService;
import com.if23b212.mtcg.service.game.GameService;
import com.if23b212.mtcg.service.user.UserService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BattleTest {

    DatabaseRunner db = new DatabaseRunner();

    UserService userService = new UserService();
    DemoService demoService = new DemoService();

    CardService cardService = new CardService();

    DeckService deckService = new DeckService();

    GameService gameService = new GameService();
    @Test
    public void uniqueFeatureTest() {
        db.initializeDatabase();
        UserCredentials userCredentials1 = new UserCredentials("mine", "hallo123");
        userService.saveUser(userCredentials1);

        UserCredentials userCredentials2 = new UserCredentials("chris", "hallo123");
        userService.saveUser(userCredentials2);

        demoService.createPackage("mine");
        demoService.createPackage("mine");

        demoService.createPackage("chris");
        demoService.createPackage("chris");


    }

    @Test
    public void deckTest() {
        db.initializeDatabase();
        UserCredentials userCredentials1 = new UserCredentials("mine", "hallo123");
        userService.saveUser(userCredentials1);

        UserCredentials userCredentials2 = new UserCredentials("chris", "hallo123");
        userService.saveUser(userCredentials2);

        demoService.createPackage("mine");
        demoService.createPackage("mine");

        demoService.createPackage("chris");
        demoService.createPackage("chris");

        try {
            List<Card> mineCard = cardService.getUserCards("mine");
            List<Card> chrisCard = cardService.getUserCards("chris");

            deckService.configureDeck("mine", mineCard.stream().map(Card::getId).limit(4).toList()); //gehe die Karten durch, nehme ID raus & hole die ersten 4 raus
            deckService.configureDeck("chris", chrisCard.stream().map(Card::getId).limit(4).toList());

            BattleStatus status1 = gameService.enterLobby("mine");
            BattleStatus status2 = gameService.enterLobby("chris");

            System.out.println(status1.printStatus());
            System.out.println(status2.printStatus());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
