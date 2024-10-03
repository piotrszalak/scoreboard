package org.sportradar.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sportradar.match.factory.FootballMatchFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.sportradar.match.exception.FootballMatchException.*;

public class FootballMatchService implements MatchInterface {
    private final List<FootballMatch> footballMatches;
    private static final Logger log = LoggerFactory.getLogger(FootballMatchService.class);

    public FootballMatchService() {
        this.footballMatches = new ArrayList<>();
    }

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        FootballMatchValidator.validateMatchStart(footballMatches, homeTeam, awayTeam);
        addNewMatch(FootballMatchFactory.createMatch(homeTeam, awayTeam));
        log.info("Match started: {} vs {}", homeTeam, awayTeam);
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        FootballMatch footballMatch = findMatch(homeTeam, awayTeam);
        FootballMatchValidator.validateScoreUpdate(footballMatch, homeScore, awayScore);
        footballMatch.setHomeScore(homeScore);
        footballMatch.setAwayScore(awayScore);
        log.info("Score updated: {} vs {}. New score: {}:{}", homeTeam, awayTeam, homeScore, awayScore);
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        FootballMatch footballMatch = findMatch(homeTeam, awayTeam);
        FootballMatchValidator.validateFinishMatch(footballMatch, homeTeam, awayTeam);
        footballMatch.setFinished(true);
        removeFinishedMatch(footballMatch);
        log.info("Match between {} and {} has been finished", homeTeam, awayTeam);
    }

    @Override
    public List<FootballMatch> getSummary() {
        return footballMatches.stream()
                .sorted(Comparator.comparingInt(FootballMatch::getTotalScore).reversed()
                        .thenComparing(Comparator.comparing(FootballMatch::getStartTime).reversed()))
                .collect(Collectors.toList());
    }

    private FootballMatch findMatch(String homeTeam, String awayTeam) {
        return footballMatches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new FootballMatchNotFoundException(homeTeam, awayTeam));
    }

    private void addNewMatch(FootballMatch footballMatch) {
        footballMatches.add(footballMatch);
        log.info("New match added: {} vs {}", footballMatch.getHomeTeam(), footballMatch.getAwayTeam());
    }

    private void removeFinishedMatch(FootballMatch footballMatch) {
        footballMatches.remove(footballMatch);
        log.info("Finished match: {} vs {} removed", footballMatch.getHomeTeam(), footballMatch.getAwayTeam());
    }
}