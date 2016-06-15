# Tool for reading games from liga-manager.de 

# How to build the JAR?
    mvn clean compile assembly:single

# How to run the tool?
    java -jar lmsanalyzer-[version]-jar-with-dependencies.jar [seasons] [numberOfGames] [LM username] [LM password]
    OR: java -jar lmsanalyzer.jar initdb
    [seasons] maybe a range ("10-12") or a single season ("1").
    [numberOfGames] is the number of games to retrieve for each season, always starting with game 1.
    "initdb" initilizes a fresh db for runnign this application.
