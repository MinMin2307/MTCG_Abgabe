package com.if23b212.mtcg.db;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.if23b212.mtcg.util.DatabaseUtils;

public class DatabaseRunner {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseRunner.class);
    private static final String path = "db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.USERNAME, DatabaseUtils.PASSWORD);
    }
    public void initializeDatabase(){
        try(Connection con = getConnection()) {

            URI uri = getClass().getClassLoader().getResource(path).toURI();

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(uri), "*.sql")) {
                for (Path filePath : stream) {
                    String scriptName = filePath.getFileName().toString();
                    executeSqlScript(con, filePath, scriptName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in initializeDatabase", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private static void executeSqlScript(Connection con, Path filePath, String scriptName) {
        try {
            String content = new String(Files.readAllBytes(filePath));
            try(Statement statement = con.createStatement()){
                statement.execute(content);
                logger.info("Executed script: {}", scriptName);
            }
        } catch (SQLException | IOException e) {
           e.printStackTrace();
        }
    }

}
