package ligamanager.spion.analyzer.util;

/**
 * Created by jpralle on 09.06.2016.
 */
public class LmIllegalGameException extends LmIllegalPageException {

    private final int gameId;
    private final int seasonNumber;
    private final IllegalGameType gameType;

    public LmIllegalGameException(String msg, int gameId, int seasonNumber, IllegalGameType gameType) {
        super(msg);

        this.gameId = gameId;
        this.seasonNumber = seasonNumber;
        this.gameType = gameType;
    }

    public String getMessage() {
        String msg = super.getMessage() + " Id: " + gameId + ", season: " + seasonNumber + ", type: " + gameType;
        return msg;
    }

    public int getGameId() {
        return gameId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public IllegalGameType getGameType() {
        return gameType;
    }
}
