package ligamanager.spion.common;

public class LmGame {

    public GameIds ids;

    public boolean hasExtraTime = false;
    public boolean hasPenaltyShooting = false;

    public HomeAwayPair<String> teamNames;

    public GameResult endResult;
    public GameValuesInclPenalties<GameResult> interimResults;

    public GameValues<HomeAwayPair<GameFormation>> formations;
    public GameValues<HomeAwayPair<Tactic>> tactics;
    public GameValues<GameResult> strengthBeginOfHalf;
    public GameValues<GameResult> strengthAverage;
    public GameValues<GameResult> strengthEndOfHalf;

}
