package ligamanager.spion.analyzer.util;

/**
 * Created by jpralle on 02.03.2016.
 */
public class GameResult {

    protected int homeValue = -1;
    protected int awayValue = -1;

    public GameResult() { }

    public GameResult(int homeValue, int awayValue) {
        this.homeValue = homeValue;
        this.awayValue = awayValue;
    }

    public int getHome() {
        return homeValue;
    }

    public int getAway() {
        return awayValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameResult that = (GameResult) o;

        if (homeValue != that.homeValue) return false;
        return awayValue == that.awayValue;

    }

    @Override
    public int hashCode() {
        int result = homeValue;
        result = 31 * result + awayValue;
        return result;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "homeValue=" + homeValue +
                ", awayValue=" + awayValue +
                '}';
    }
}
