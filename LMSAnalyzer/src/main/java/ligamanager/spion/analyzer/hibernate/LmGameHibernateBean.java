package ligamanager.spion.analyzer.hibernate;

import ligamanager.spion.analyzer.util.*;

/**
 * Created by jpralle on 02.06.2016.
 */
public class LmGameHibernateBean {

    private int gameId = -1;
    private int seasonNumber = -1;

    private boolean showsHomeBonus = false;

    private boolean hasExtraTime = false;
    private boolean hasPenaltyShooting = false;

    private String homeTeamName = null;
    private String awayTeamName = null;
    private GameResult endResult = null;
    private GameResult resultFirstHalf = null;
    private GameResult resultSecondHalf = null;
    private GameResult resultExtraTime = null;
    private GameResult resultPenaltyShooting = null;
    private GameFormation homeFormationFirstHalf = null;
    private GameFormation homeFormationSecondHalf = null;
    private GameFormation homeFormationExtraTime = null;
    private GameFormation awayFormationFirstHalf = null;
    private GameFormation awayFormationSecondHalf = null;
    private GameFormation awayFormationExtraTime = null;
    private Tactic homeTacticFirstHalf = null;
    private Tactic homeTacticSecondHalf = null;
    private Tactic homeTacticExtraTime = null;
    private Tactic awayTacticFirstHalf = null;
    private Tactic awayTacticSecondHalf = null;
    private Tactic awayTacticExtraTime = null;
    private GameResult strengthBeginOfFirstHalf = null;
    private GameResult strengthBeginOfSecondHalf = null;
    private GameResult strengthBeginOfExtraTime = null;
    private GameResult strengthAverageOfFirstHalf = null;
    private GameResult strengthAverageOfSecondHalf = null;
    private GameResult strengthAverageOfExtraTime = null;
    private GameResult strengthEndOfFirstHalf = null;
    private GameResult strengthEndOfSecondHalf = null;
    private GameResult strengthEndOfExtraTime = null;

}
