package ligamanager.spion.analysis.test;

public class ResultStateChangeCounter {

    private int minStrength = -1;
    private int maxStrength = -1;

    private int totalGames = 0;
    private int persistentWins = 0;
    private int winsToDraws = 0;
    private int winsToLosses = 0;
    private int persistentDraws = 0;
    private int drawsToWins = 0;
    private int drawsToLosses = 0;
    private int persistentLosses = 0;
    private int lossesToWins = 0;
    private int lossesToDraws = 0;

    public ResultStateChangeCounter(int minStrength, int maxStrength) {
        this.minStrength = minStrength;
        this.maxStrength = maxStrength;
    }

    public boolean matchesStrength(int strengthDiff) {
        boolean ret = (this.maxStrength >= strengthDiff) && (this.minStrength <= strengthDiff);
        return ret;
    }

    public void countPersistentWin() {
        totalGames++;
        persistentWins++;
    }

    public void countWinToDraw() {
        totalGames++;
        winsToDraws++;
    }

    public void countWinToLoss() {
        totalGames++;
        winsToLosses++;
    }

    public void countPersistentDraw() {
        totalGames++;
        persistentDraws++;
    }

    public void countDrawToWin() {
        totalGames++;
        drawsToWins++;
    }

    public void countDrawToLoss() {
        totalGames++;
        drawsToLosses++;
    }

    public void countPersistentLoss() {
        totalGames++;
        persistentLosses++;
    }

    public void countLossToWin() {
        totalGames++;
        lossesToWins++;
    }

    public void countLossToDraw() {
        totalGames++;
        lossesToDraws++;
    }

    public int getMinStrength() {
        return minStrength;
    }

    public int getMaxStrength() {
        return maxStrength;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public double getPersistentWinPercentage() {
        return calculatePercentageFor(persistentWins);
    }

    public double getWinToDrawPercentage() {
        return calculatePercentageFor(winsToDraws);
    }

    public double getWinToLossPercentage() {
        return calculatePercentageFor(winsToLosses);
    }

    public double getPersistentDrawPercentage() {
        return calculatePercentageFor(persistentDraws);
    }

    public double getDrawToWinPercentage() {
        return calculatePercentageFor(drawsToWins);
    }

    public double getDrawToLossPercentage() {
        return calculatePercentageFor(drawsToLosses);
    }

    public double getPersistentLossPercentage() {
        return calculatePercentageFor(persistentLosses);
    }

    public double getLossToWinPercentage() {
        return calculatePercentageFor(lossesToWins);
    }

    public double getLossToDrawPercentage() {
        return calculatePercentageFor(lossesToDraws);
    }

    private double calculatePercentageFor(int gameCount) {
        if (totalGames <= 0) {
            return 0.0;
        }

        double winsDouble = (double) gameCount;
        double totalGamesDouble = (double) totalGames;
        double ret = winsDouble / totalGamesDouble;
        ret = ret * 100;
        return ret;
    }

}
