package org.sportradar.match.factory;

import org.sportradar.match.FootballMatch;
import org.sportradar.team.TeamValidator;

public class FootballMatchFactory {
    public static FootballMatch createMatch(String homeTeam, String awayTeam) {
        TeamValidator.validateTeams(homeTeam, awayTeam);
        return new FootballMatch(homeTeam, awayTeam);
    }
}
