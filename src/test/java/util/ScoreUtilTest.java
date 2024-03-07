package util;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.if23b212.mtcg.db.DatabaseRunner;
import org.junit.Test;

import com.if23b212.mtcg.config.ScoreboardConfig;
import com.if23b212.mtcg.model.game.BattleHistory;
import com.if23b212.mtcg.model.game.Statistics;
import com.if23b212.mtcg.util.ScoreUtil;

import static org.junit.Assert.assertEquals;

public class ScoreUtilTest {
 DatabaseRunner db = new DatabaseRunner();
    @Test
    public void testCalculateScore2Wins() {
        db.initializeDatabase();
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        BattleHistory win1 = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId1);
        BattleHistory win2 = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId1);

        List<BattleHistory> battles = Arrays.asList(win1, win2);

        int score = ScoreUtil.calculateScore(battles, userId1);
        assertEquals(ScoreboardConfig.BASE_SCORE + 3 + 3, score);
    }
    @Test
    public void testCalculateScore2Losses() {
        db.initializeDatabase();
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        BattleHistory loss1 = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId2);
        BattleHistory loss2 = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId2);

        List<BattleHistory> battles = Arrays.asList(loss1, loss2);

        int score = ScoreUtil.calculateScore(battles, userId1);
        assertEquals(ScoreboardConfig.BASE_SCORE -5 - 5, score);
    }
    @Test
    public void testCalculateScoreALL() {
        db.initializeDatabase();
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        BattleHistory win = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId1);
        BattleHistory loss = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId2);
        BattleHistory draw = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, null);

        List<BattleHistory> battles = Arrays.asList(win, loss, draw);

        int score = ScoreUtil.calculateScore(battles, userId1);
        assertEquals(ScoreboardConfig.BASE_SCORE + 3 - 5, score);
    }

    @Test
    public void testCalculateStatistics2Wins() {
        db.initializeDatabase();
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        BattleHistory win = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId1);
        BattleHistory win2 = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId1);

        List<BattleHistory> battles = List.of(win, win2);
        Statistics statistics =  ScoreUtil.calculateStatistics(battles, userId1);
        assertEquals(2, statistics.getWins());
        assertEquals(2, statistics.getNumberOfBattles());
    }

    @Test
    public void testCalculateStatistics2Loss()  {
        db.initializeDatabase();
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        BattleHistory loss1 = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId2);
        BattleHistory loss2 = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId2);


        List<BattleHistory> battles = List.of(loss1, loss2);
        Statistics statistics = ScoreUtil.calculateStatistics(battles, userId1);
        assertEquals(2, statistics.getLosses());
        assertEquals(2, statistics.getNumberOfBattles());
    }

    @Test
    public void testCalculateStatisticALL() {
        db.initializeDatabase();
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        BattleHistory win = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId1);
        BattleHistory loss = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, userId2);
        BattleHistory draw = new BattleHistory(UUID.randomUUID(), null, userId1, userId2, null);


        List<BattleHistory> battles = List.of(win, loss, draw);
        Statistics statistics =  ScoreUtil.calculateStatistics(battles, userId1);
        assertEquals(1, statistics.getWins());
        assertEquals(1, statistics.getLosses());
        assertEquals(3, statistics.getNumberOfBattles());
    }
}