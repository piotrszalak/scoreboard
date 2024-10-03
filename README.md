Introduction

The Scoreboard application is a tool for managing football match scores. It enables users to track match scores in real-time, update scores, and view match history.



Features

    Start a Match. Starts a new match by entering team names.

    Update Score. Updates the match score.

    Finish Match. Finishes the match and save the score.

    Get Summary. Displays a summary of all ongoing matches.


Run

    Clone repository:    git clone https://github.com/piotrszalak/scoreboard.git
    Build:               mvn clean install
    Run the application: mvn exec:java -Dexec.mainClass="org.sportradar.Main"


API

The application provides an API with the following methods:

    startMatch(String homeTeam, String awayTeam)
      
      Parameters:
      homeTeam: Name of the first team.
      awayTeam: Name of the second team.

    
    updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore)
      
      Parameters:
      homeTeam: Name of the first team.
      awayTeam: Name of the second team.
      homeScore: Score of the first team.
      awayScore: Score of the second team.

   
    finishMatch(String homeTeam, String awayTeam)
      
      Parameters:
      homeTeam: Name of the first team.
      awayTeam: Name of the second team.

    
    getSummary()


Usage
  
    Start Match           scoreboard.startMatch("Team A", "Team B");
    Update Score          scoreboard.updateScore("Team A", "Team B", homeScore, awayScore);
    Finish Match          scoreboard.finishMatch("Team A", "Team B");
    Match Summary         List<Match> matches = scoreboard.getSummary();


Testing
The application includes a set of unit tests. To run the tests, use the command:  mvn test
