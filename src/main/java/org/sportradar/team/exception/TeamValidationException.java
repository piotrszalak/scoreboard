package org.sportradar.team.exception;

public class TeamValidationException extends RuntimeException {
    public TeamValidationException(String message) {
        super(message);
    }

    public static class TeamIsPlayingException extends TeamValidationException {
        public TeamIsPlayingException() {
            super("One of the teams is already playing");
        }
    }

    public static class TeamNameNullOrEmptyException extends TeamValidationException {
        public TeamNameNullOrEmptyException(String teamType) {
            super(teamType + " team name cannot be null or empty");
        }
    }

    public static class TeamNameInvalidCharactersException extends TeamValidationException {
        public TeamNameInvalidCharactersException(String teamType) {
            super(teamType + " team name contains invalid characters. Only alphabetic characters are allowed");
        }
    }

    public static class TeamNameTooLongException extends TeamValidationException {
        public TeamNameTooLongException(String teamType) {
            super(teamType + " team name exceeds max length of " + 50);
        }
    }

    public static class TeamsMustBeDifferentException extends TeamValidationException {
        public TeamsMustBeDifferentException() {
            super("Home and away team names must be different");
        }
    }
}
