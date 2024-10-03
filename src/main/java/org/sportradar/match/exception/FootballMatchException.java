package org.sportradar.match.exception;

public class FootballMatchException extends RuntimeException {
    public FootballMatchException(String message) {
        super(message);
    }

    public static class FootballMatchNotFoundException extends FootballMatchException {
        public FootballMatchNotFoundException(String homeTeam, String awayTeam) {
            super("Match not found for teams: " + homeTeam + " vs " + awayTeam);
        }
    }

    public static class FootballMatchFinishedException extends FootballMatchException {
        public FootballMatchFinishedException() {
            super("Cannot update score, match is already finished");
        }

        public FootballMatchFinishedException(String homeTeam, String awayTeam) {
            super("Match between " + homeTeam + " and " + awayTeam + " is already finished");
        }
    }

    public static class NegativeScoreExceptionFootball extends FootballMatchException {
        public NegativeScoreExceptionFootball() {
            super("Team score cannot be negative");
        }
    }

    public static class IncorrectScoreExceptionFootball extends FootballMatchException {
        public IncorrectScoreExceptionFootball() {
            super("New scores cannot be lower than the current scores");
        }
    }
}

