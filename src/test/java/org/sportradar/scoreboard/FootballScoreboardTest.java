package org.sportradar.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sportradar.TestUtils;
import org.sportradar.match.exception.FootballMatchException;
import org.sportradar.match.FootballMatch;
import org.sportradar.match.FootballMatchService;
import org.sportradar.team.exception.TeamValidationException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class FootballScoreboardTest extends TestUtils {
    private FootballScoreboard scoreboard;
    private FootballMatchService footballMatchServiceMock;

    @BeforeEach
    public void setUp() {
        footballMatchServiceMock = mock(FootballMatchService.class);
        scoreboard = new FootballScoreboard(footballMatchServiceMock);
    }

    @Test
    void testStartMatch_Success() {
        scoreboard.startMatch(TEAM_MEXICO, TEAM_CANADA);
        verify(footballMatchServiceMock, times(1)).startMatch(TEAM_MEXICO, TEAM_CANADA);
    }

    @Test
    void testStartMatch_ThrowsTeamValidationException() {
        doThrow(new TeamValidationException("Home and away team names must be different"))
                .when(footballMatchServiceMock).startMatch(TEAM_MEXICO, TEAM_MEXICO);

        assertThrows(TeamValidationException.class, () -> scoreboard.startMatch(TEAM_MEXICO, TEAM_MEXICO));

        verify(footballMatchServiceMock, times(1)).startMatch(TEAM_MEXICO, TEAM_MEXICO);
    }

    @Test
    void testUpdateScore_Success() {
        scoreboard.updateScore(TEAM_MEXICO, TEAM_CANADA, 3, 2);
        verify(footballMatchServiceMock, times(1)).updateScore(TEAM_MEXICO, TEAM_CANADA, 3, 2);
    }

    @Test
    void testUpdateScore_ThrowsException() {
        doThrow(new FootballMatchException("Match not found for teams: " + TEAM_MEXICO + " vs " + TEAM_CANADA))
                .when(footballMatchServiceMock).updateScore(TEAM_MEXICO, TEAM_CANADA, 3, 2);

        assertThrows(FootballMatchException.class, () -> scoreboard.updateScore(TEAM_MEXICO, TEAM_CANADA, 3, 2));
        verify(footballMatchServiceMock, times(1)).updateScore(TEAM_MEXICO, TEAM_CANADA, 3, 2);
    }

    @Test
    void testFinishMatch_Success() {
        scoreboard.finishMatch(TEAM_MEXICO, TEAM_CANADA);
        verify(footballMatchServiceMock, times(1)).finishMatch(TEAM_MEXICO, TEAM_CANADA);
    }

    @Test
    void testFinishMatch_ThrowsException() {
        doThrow(new FootballMatchException("Match not found for teams: " + TEAM_MEXICO + " vs " + TEAM_CANADA))
                .when(footballMatchServiceMock).finishMatch(TEAM_MEXICO, TEAM_CANADA);

        assertThrows(FootballMatchException.class, () -> scoreboard.finishMatch(TEAM_MEXICO, TEAM_CANADA));
        verify(footballMatchServiceMock, times(1)).finishMatch(TEAM_MEXICO, TEAM_CANADA);
    }

    @Test
    void testGetSummary_Success() {
        List<FootballMatch> mockFootballMatches = Arrays.asList(
                new FootballMatch(TEAM_MEXICO, TEAM_CANADA),
                new FootballMatch(TEAM_SPAIN, TEAM_BRAZIL)
        );

        when(footballMatchServiceMock.getSummary()).thenReturn(mockFootballMatches);

        List<FootballMatch> summary = scoreboard.getSummary();

        assertEquals(2, summary.size());
        assertEquals(TEAM_MEXICO, summary.get(0).getHomeTeam());
        assertEquals(TEAM_SPAIN, summary.get(1).getHomeTeam());

        verify(footballMatchServiceMock, times(1)).getSummary();
    }

    @Test
    void testGetSummary_NoMatches() {
        List<FootballMatch> summary = scoreboard.getSummary();

        assertTrue(summary.isEmpty());
        verify(footballMatchServiceMock, times(1)).getSummary();
    }
}


