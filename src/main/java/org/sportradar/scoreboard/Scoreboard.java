package org.sportradar.scoreboard;

import org.sportradar.match.FootballMatch;
import org.sportradar.team.exception.TeamValidationException;

import java.util.List;

public interface Scoreboard {

    void startMatch(String homeTeam, String awayTeam) throws TeamValidationException, MatchException;

    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) throws TeamValidationException, MatchException;

    void finishMatch(String homeTeam, String awayTeam) throws TeamValidationException, MatchException;

    List<FootballMatch> getSummary();
}
