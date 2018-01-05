package ligamanager.spion.common;

import java.io.Serializable;

/**
 * Created by jpralle on 06.06.2016.
 */
public class GameIds implements Serializable {

    private int gameId = -1;
    private int seasonNumber = -1;

    public GameIds() {
    }

    public GameIds(int gameId, int seasonNumber) {
        this.gameId = gameId;
        this.seasonNumber = seasonNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameIds gameIds = (GameIds) o;

        if (gameId != gameIds.gameId) return false;
        return seasonNumber == gameIds.seasonNumber;

    }

    @Override
    public int hashCode() {
        int result = gameId;
        result = 31 * result + seasonNumber;
        return result;
    }

}
