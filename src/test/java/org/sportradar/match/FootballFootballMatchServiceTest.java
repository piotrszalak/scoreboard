package org.sportradar.match;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sportradar.TestUtils;
import org.sportradar.match.exception.FootballMatchException;
import org.sportradar.team.exception.TeamValidationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FootballFootballMatchServiceTest extends TestUtils {
    private FootballMatchService footballMatchService;

    @BeforeEach
    public void setUp() {
        footballMatchService = new FootballMatchService();
    }

    @Test
    public void testStartMatch_Success() {
        footballMatchService.startMatch(TEAM_MEXICO, TEAM_CANADA);

        List<FootballMatch> summary = footballMatchService.getSummary();

        assertEquals(TEAM_MEXICO, summary.get(0).getHomeTeam());
        assertEquals(TEAM_CANADA, summary.get(0).getAwayTeam());
    }

    @Test()
    public void testStartMatch_SameTeams() {
        assertThrows(TeamValidationException.TeamsMustBeDifferentException.class, () -> footballMatchService.startMatch(TEAM_MEXICO, TEAM_MEXICO));
    }

    @Test()
    public void testStartMatch_TeamAlreadyPlaying() {
        footballMatchService.startMatch(TEAM_MEXICO, TEAM_CANADA);
        assertThrows(TeamValidationException.TeamIsPlayingException.class, () -> footballMatchService.startMatch(TEAM_MEXICO, TEAM_ITALY));
    }

    @Test
    public void testStartMatch_EmptyHomeTeam() {
        assertThrows(TeamValidationException.TeamNameNullOrEmptyException.class, () -> footballMatchService.startMatch("", TEAM_CANADA));
    }

    @Test
    public void testStartMatch_EmptyAwayTeam() {
        assertThrows(TeamValidationException.TeamNameNullOrEmptyException.class, () -> footballMatchService.startMatch(TEAM_MEXICO, ""));
    }

    @Test
    public void testStartMatch_NullHomeTeam() {
        assertThrows(TeamValidationException.TeamNameNullOrEmptyException.class, () -> footballMatchService.startMatch(null, TEAM_CANADA));
    }

    @Test
    public void testStartMatch_NullAwayTeam() {
        assertThrows(TeamValidationException.TeamNameNullOrEmptyException.class, () -> footballMatchService.startMatch(TEAM_MEXICO, null));
    }

    @Test
    public void testStartMatch_InvalidCharactersInTeamName() {
        assertThrows(TeamValidationException.TeamNameInvalidCharactersException.class, () -> footballMatchService.startMatch("Team@Mexico", TEAM_CANADA));
    }

    @Test
    public void testStartMatch_TooLongTeamName() {
        assertThrows(TeamValidationException.TeamNameTooLongException.class, () -> footballMatchService.startMatch("MexicoMexicoMexicoMexicoMexicoMexicoMexicoMexicoMex", TEAM_CANADA));
    }

    @Test
    public void testUpdateScore_ValidScores() {
        runUpdateScoreTest(2, 1);
    }

    @Test
    public void testUpdateScore_ZeroScore() {
        runUpdateScoreTest(0, 2);
    }

    @Test
    public void testUpdateScore_BothScoresZero() {
        runUpdateScoreTest(0, 0);
    }

    @Test
    public void testUpdateScore_IncreaseScore() {
        footballMatchService.startMatch(TEAM_MEXICO, TEAM_CANADA);
        footballMatchService.updateScore(TEAM_MEXICO, TEAM_CANADA, 1, 1);
        footballMatchService.updateScore(TEAM_MEXICO, TEAM_CANADA, 2, 2);
        assertSummary(2, 2);
    }


    @Test
    public void testUpdateScore_NegativeScore() {
        footballMatchService.startMatch(TEAM_MEXICO, TEAM_CANADA);
        Exception exception = assertThrows(FootballMatchException.NegativeScoreExceptionFootball.class, () -> footballMatchService.updateScore(TEAM_MEXICO, TEAM_CANADA, -1, 2));
        assertEquals("Team score cannot be negative", exception.getMessage());
    }

    @Test
    public void testUpdateScore_NoMatchStarted() {
        Exception exception = assertThrows(FootballMatchException.FootballMatchNotFoundException.class, () -> footballMatchService.updateScore(TEAM_MEXICO, TEAM_CANADA, 1, 0));
        assertEquals("Match not found for teams: Mexico vs Canada", exception.getMessage());
    }

    @Test
    public void testFinishMatch_Success() {
        footballMatchService.startMatch(TEAM_MEXICO, TEAM_CANADA);
        footballMatchService.finishMatch(TEAM_MEXICO, TEAM_CANADA);
        assertEquals(0, footballMatchService.getSummary().size());
    }

    @Test
    public void testFinishMatch_MatchNotFound() {
        assertThrows(FootballMatchException.FootballMatchNotFoundException.class, () -> footballMatchService.finishMatch(TEAM_MEXICO, TEAM_CANADA));
    }

    @Test
    public void testGetSummary_Success() throws InterruptedException {
        startMatchesWithDelay();
        updateScores();
        List<FootballMatch> summary = footballMatchService.getSummary();
        assertEquals(5, summary.size());
        assertTeamsOrder(summary);
    }

    @Test
    public void testGetSummary_EmptyList() {
        List<FootballMatch> summary = footballMatchService.getSummary();
        assertTrue(summary.isEmpty());
    }

    private void runUpdateScoreTest(int homeScore, int awayScore) {
        footballMatchService.startMatch(TEAM_MEXICO, TEAM_CANADA);
        footballMatchService.updateScore(TEAM_MEXICO, TEAM_CANADA, homeScore, awayScore);
        assertSummary(homeScore, awayScore);
    }

    private void assertSummary(int expectedHomeScore, int expectedAwayScore) {
        List<FootballMatch> summary = footballMatchService.getSummary();
        assertEquals(TEAM_MEXICO, summary.get(0).getHomeTeam());
        assertEquals(TEAM_CANADA, summary.get(0).getAwayTeam());
        assertEquals(expectedHomeScore, summary.get(0).getHomeScore());
        assertEquals(expectedAwayScore, summary.get(0).getAwayScore());
    }

    private void startMatchesWithDelay() throws InterruptedException {
        footballMatchService.startMatch(TEAM_MEXICO, TEAM_CANADA);
        Thread.sleep(10);
        footballMatchService.startMatch(TEAM_SPAIN, TEAM_BRAZIL);
        Thread.sleep(10);
        footballMatchService.startMatch(TEAM_GERMANY, TEAM_FRANCE);
        Thread.sleep(10);
        footballMatchService.startMatch(TEAM_URUGUAY, TEAM_ITALY);
        Thread.sleep(10);
        footballMatchService.startMatch(TEAM_ARGENTINA, TEAM_AUSTRALIA);
    }

    private void updateScores() {
        footballMatchService.updateScore(TEAM_MEXICO, TEAM_CANADA, 0, 5);
        footballMatchService.updateScore(TEAM_SPAIN, TEAM_BRAZIL, 10, 2);
        footballMatchService.updateScore(TEAM_GERMANY, TEAM_FRANCE, 2, 2);
        footballMatchService.updateScore(TEAM_URUGUAY, TEAM_ITALY, 6, 6);
        footballMatchService.updateScore(TEAM_ARGENTINA, TEAM_AUSTRALIA, 3, 1);
    }

    private void assertTeamsOrder(List<FootballMatch> summary) {
        assertEquals(TEAM_URUGUAY, summary.get(0).getHomeTeam());
        assertEquals(TEAM_SPAIN, summary.get(1).getHomeTeam());
        assertEquals(TEAM_MEXICO, summary.get(2).getHomeTeam());
        assertEquals(TEAM_ARGENTINA, summary.get(3).getHomeTeam());
        assertEquals(TEAM_GERMANY, summary.get(4).getHomeTeam());
    }
}