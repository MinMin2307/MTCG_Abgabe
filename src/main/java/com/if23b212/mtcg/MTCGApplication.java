package com.if23b212.mtcg;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.if23b212.mtcg.config.ServerConfig;
import com.if23b212.mtcg.db.DatabaseRunner;
import com.if23b212.mtcg.rest.card.CardController;
import com.if23b212.mtcg.rest.card.DeckController;
import com.if23b212.mtcg.rest.card.PackageController;
import com.if23b212.mtcg.rest.game.BattleController;
import com.if23b212.mtcg.rest.game.ScoreboardController;
import com.if23b212.mtcg.rest.game.StatisticController;
import com.if23b212.mtcg.rest.user.UserController;
import com.sun.net.httpserver.HttpServer;

/**
 *
 * Anmerkung: https://stackoverflow.com/questions/11655720/the-correct-way-to-pass-data-to-from-a-java-httphandler-class-for-java-httpserve
 */
public class MTCGApplication {

    private final static String SERVER_STARTING_MESSAGE = "Server is starting....";
    private final static String SERVER_RUNNING_MESSAGE = "Server is running....";
    private final static int MAX_PLAYER_COUNT = 20;
    public static void main(String[] args) {

        try {
            prepareDatabase();
            startServer();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startServer() throws IOException {
        System.out.println(SERVER_STARTING_MESSAGE);

        HttpServer server = HttpServer.create(new InetSocketAddress(ServerConfig.INET_SOCKET_PORT), 0);
        configureEndpoints(server);
//      HttpServer inherently supports handling HTTP requests from different threads, but to gain more control
//      for example limiting the amount of possible threads or scalability I use the ThreadPoolExecutor
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_PLAYER_COUNT); 
        server.setExecutor(executor);

        server.start();

        System.out.println(SERVER_RUNNING_MESSAGE);
    }

    private static void configureEndpoints(HttpServer server) {

        if(server != null) {
            server.createContext("/users", new UserController.UserHandler());
            server.createContext("/sessions", new UserController.UserLogin());
            server.createContext("/packages", new PackageController.PackageCreation());
            server.createContext("/transactions/packages", new PackageController.BuyPackage());
            server.createContext("/cards", new CardController.GetCardList());
            server.createContext("/deck", new DeckController.HandleDeck());
            server.createContext("/stats", new StatisticController.GetStats());
            server.createContext("/scoreboard", new ScoreboardController.GetScoreboard());
            server.createContext("/battle", new BattleController.EnterBatteLobby());


        }
    }

    private static void prepareDatabase() {
        new DatabaseRunner().initializeDatabase();
    }
}
