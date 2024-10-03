package org.sportradar.match;

import java.util.List;


public interface MatchInterface {

    void startMatch(String homeTeam, String awayTeam);

    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);

    void finishMatch(String homeTeam, String awayTeam);

    List<FootballMatch> getSummary();
}