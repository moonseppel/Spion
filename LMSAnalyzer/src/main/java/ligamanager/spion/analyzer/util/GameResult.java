package ligamanager.spion.analyzer.util;

import java.util.StringTokenizer;

/**
 * Created by jpralle on 02.03.2016.
 */
public class GameResult implements Emptyable {

    protected int homeValue = -1;
    protected int awayValue = -1;

    public static final GameResult EMPTY = new GameResult(-1, -1);

    public GameResult(int homeValue, int awayValue) {
        this.homeValue = homeValue;
        this.awayValue = awayValue;
    }

    public GameResult(String resultWithColon) {
        parseResultWithColon(resultWithColon);
    }

    public int getHome() {
        return homeValue;
    }

    public int getAway() {
        return awayValue;
    }

    public boolean isEmpty() {
        return this.equals(GameResult.EMPTY);
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

    private void parseResultWithColon(String resultWithColon) {
        StringTokenizer st = new StringTokenizer(resultWithColon, ":", false);

        if(st.hasMoreTokens()) {
            String homeScoreAsString = st.nextToken().trim();
            homeValue = Integer.parseInt(homeScoreAsString);
        }

        if(st.hasMoreTokens()) {
            String awayScoreAsString = st.nextToken().trim();
            awayValue = Integer.parseInt(awayScoreAsString);
        }
    }

}
