package org.sportradar.scoreboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sportradar.match.exception.FootballMatchException;
import org.sportradar.match.FootballMatch;
import org.sportradar.match.FootballMatchService;
import org.sportradar.team.exception.TeamValidationException;

import java.util.*;

public class FootballScoreboard implements Scoreboard {
    private final FootballMatchService footballMatchService;
    private static final Logger log = LoggerFactory.getLogger(FootballScoreboard.class);

    public FootballScoreboard(FootballMatchService footballMatchService) {
        this.footballMatchService = footballMatchService;
    }

    @Override
    public void startMatch(String homeTeam, String awayTeam) throws TeamValidationException, FootballMatchException {
        footballMatchService.startMatch(homeTeam, awayTeam);
        log.info("Scoreboard updated. Match: {} vs {} started}", homeTeam, awayTeam);
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) throws TeamValidationException, FootballMatchException {
        footballMatchService.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        log.info("Scoreboard updated for match: {} vs {}. Current score: {}:{}", homeTeam, awayTeam, homeScore, awayScore);
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) throws TeamValidationException, FootballMatchException {
        footballMatchService.finishMatch(homeTeam, awayTeam);
        log.info("Scoreboard updated.Match: {} vs {} is finished", homeTeam, awayTeam);
    }

    @Override
    public List<FootballMatch> getSummary() {
        return footballMatchService.getSummary();
    }
}