package org.sportradar.team;

import static org.sportradar.team.exception.TeamValidationException.*;

public class TeamValidator {

    private static final int MAX_TEAM_NAME_LENGTH = 50;

    public static void validateTeams(String homeTeam, String awayTeam) {
        validateTeam(homeTeam, TeamType.HOME);
        validateTeam(awayTeam, TeamType.AWAY);
        validateDifferentTeams(homeTeam, awayTeam);
    }

    private static void validateTeam(String teamName, TeamType teamType) {
        validateNotNullOrEmpty(teamName, teamType);
        validateMaxLength(teamName, teamType);
        validateTeamName(teamName, teamType);
    }


    private static void validateNotNullOrEmpty(String teamName, TeamType teamType) {
        if (isNullOrEmpty(teamName)) {
            throw new TeamNameNullOrEmptyException(teamType.name());
        }
    }

    private static void validateMaxLength(String teamName, TeamType teamType) {
        if (teamName.length() > MAX_TEAM_NAME_LENGTH) {
            throw new TeamNameTooLongException(teamType.name());
        }
    }

    private static void validateTeamName(String teamName, TeamType teamType) {
        if (isInvalidTeamName(teamName)) {
            throw new TeamNameInvalidCharactersException(teamType.name());
        }
    }

    private static void validateDifferentTeams(String homeTeam, String awayTeam) {
        if (homeTeam.equals(awayTeam)) {
            throw new TeamsMustBeDifferentException();
        }
    }

    private static boolean isNullOrEmpty(String teamName) {
        return teamName == null || teamName.isEmpty();
    }

    private static boolean isInvalidTeamName(String teamName) {
        return !teamName.matches("^[A-Za-z ]+$");
    }
}


