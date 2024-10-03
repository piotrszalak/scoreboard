package org.sportradar.match;

import java.util.List;

import static org.sportradar.match.exception.FootballMatchException.*;
import static org.sportradar.team.exception.TeamValidationException.*;

public class FootballMatchValidator {

    public static void validateMatchStart(List<FootballMatch> footballMatches, String homeTeam, String awayTeam) {
        if (isTeamPlaying(footballMatches, homeTeam, awayTeam)) {
            throw new TeamIsPlayingException();
        }
    }

    public static void validateScoreUpdate(FootballMatch footballMatch, int homeScore, int awayScore) {
        validateScore(footballMatch, homeScore, awayScore);
        if (footballMatch.isFinished()) {
            throw new FootballMatchFinishedException();
        }
    }

    public static void validateFinishMatch(FootballMatch footballMatch, String homeTeam, String awayTeam) {
        if (footballMatch.isFinished()) {
            throw new FootballMatchFinishedException(homeTeam, awayTeam);
        }
    }

    private static boolean isTeamPlaying(List<FootballMatch> footballMatches, String homeTeam, String awayTeam) {
        return footballMatches.stream()
                .anyMatch(match -> match.getHomeTeam().equals(homeTeam) || match.getAwayTeam().equals(awayTeam));
    }

    private static void validateScore(FootballMatch footballMatch, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new NegativeScoreExceptionFootball();
        }

        if (footballMatch.getHomeScore() > homeScore || footballMatch.getAwayScore() > awayScore) {
            throw new IncorrectScoreExceptionFootball();
        }
    }
}