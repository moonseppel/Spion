package ligamanager.spion.analysis.test;

public class WinsAndLossesCounter {

    private int minStrength = -1;
    private int maxStrength = -1;

    private int wins = 0;
    private int draws = 0;
    private int losses = 0;
    private int totalGames = 0;

    public WinsAndLossesCounter(int minStrength, int maxStrength) {
        this.minStrength = minStrength;
        this.maxStrength = maxStrength;
    }

    public boolean matchesStrength(int strengthDiff) {
        boolean ret = (this.maxStrength >= strengthDiff) && (this.minStrength <= strengthDiff);
        return ret;
    }

    public void countWin() {
        this.totalGames++;
        this.wins++;
    }

    public void countDraw() {
        this.totalGames++;
        this.draws++;
    }

    public void countLoss() {
        this.totalGames++;
        this.losses++;
    }

    public int getMinStrength() {
        return minStrength;
    }

    public int getMaxStrength() {
        return maxStrength;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public double getWinPercentage() {
        return calculatePercentageFor(this.wins);
    }

    public double getDrawPercentage() {
        return calculatePercentageFor(this.draws);
    }

    public double getLossPercentage() {
        return calculatePercentageFor(this.losses);
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
