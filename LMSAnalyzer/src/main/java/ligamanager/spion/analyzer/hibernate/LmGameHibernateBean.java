package ligamanager.spion.analyzer.hibernate;

import ligamanager.spion.analyzer.util.*;

/**
 * Created by jpralle on 02.06.2016.
 */
public class LmGameHibernateBean {

    private int gameId = -1;
    private int seasonNumber = -1;

    private boolean hasExtraTime = false;
    private boolean hasPenaltyShooting = false;

    private String homeTeamName = null;
    private String awayTeamName = null;
    private int endResultHome = -1;
    private int endResultAway = -1;
    private int resultFirstHalfHome = -1;
    private int resultFirstHalfAway = -1;
    private int resultSecondHalfHome = -1;
    private int resultSecondHalfAway = -1;
    private int resultExtraTimeHome = -1;
    private int resultExtraTimeAway = -1;
    private int resultPenaltyShootingHome = -1;
    private int resultPenaltyShootingAway = -1;
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
    private int strengthBeginOfFirstHalfHome = -1;
    private int strengthBeginOfFirstHalfAway = -1;
    private int strengthBeginOfSecondHalfHome = -1;
    private int strengthBeginOfSecondHalfAway = -1;
    private int strengthBeginOfExtraTimeHome = -1;
    private int strengthBeginOfExtraTimeAway = -1;
    private int strengthAverageOfFirstHalfHome = -1;
    private int strengthAverageOfFirstHalfAway = -1;
    private int strengthAverageOfSecondHalfHome = -1;
    private int strengthAverageOfSecondHalfAway = -1;
    private int strengthAverageOfExtraTimeHome = -1;
    private int strengthAverageOfExtraTimeAway = -1;
    private int strengthEndOfFirstHalfHome = -1;
    private int strengthEndOfFirstHalfAway = -1;
    private int strengthEndOfSecondHalfHome = -1;
    private int strengthEndOfSecondHalfAway = -1;
    private int strengthEndOfExtraTimeHome = -1;
    private int strengthEndOfExtraTimeAway = -1;

}
