package ligamanager.spion.analysis.test;

import ligamanager.spion.common.LmGame;
import ligamanager.spion.common.hibernate.LmGameHibernateBean;
import ligamanager.spion.common.hibernate.SessionFactoryFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindGamesForFormationAndTacticFirstHalf {

    private static final Logger LOGGER = Logger.getLogger(FindGamesForFormationAndTacticFirstHalf.class);

    public static List<LmGame> getGamesWithSameFormationAndTactic() throws HibernateException {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE G.formationFirstHalfHome = G.formationFirstHalfAway " +
                "AND G.tacticFirstHalfHome = G.tacticFirstHalfAway " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getAllGames() throws HibernateException {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getGamesWithDifferentFormationAndTactic() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.formationFirstHalfHome != G.formationFirstHalfAway " +
                "OR G.tacticFirstHalfHome != G.tacticFirstHalfAway) " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getGames442_3vsAny() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.formationFirstHalfHome = '4-4-2 (3)' " +
                "AND G.strengthAverageOfFirstHalfHome > G.strengthAverageOfFirstHalfAway) " +
                "OR (G.formationFirstHalfAway = '4-4-2 (3)' " +
                "AND G.strengthAverageOfFirstHalfAway > G.strengthAverageOfFirstHalfHome) " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getGamesWith442_3AsWinningFormationWithout442_3AsOpposingFormation() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.formationFirstHalfHome = '4-4-2 (3)' " +
                "AND G.strengthAverageOfFirstHalfHome > G.strengthAverageOfFirstHalfAway " +
                "AND G.formationFirstHalfAway != '4-4-2 (3)') " +
                "OR (G.formationFirstHalfAway = '4-4-2 (3)' " +
                "AND G.strengthAverageOfFirstHalfAway > G.strengthAverageOfFirstHalfHome " +
                "AND G.formationFirstHalfHome != '4-4-2 (3)') " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getGamesWith442_3OffensiveAsWinningFormation() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.formationFirstHalfHome = '4-4-2 (3)' " +
                    "AND G.tacticFirstHalfHome = 1" +
                    "AND G.strengthAverageOfFirstHalfHome > G.strengthAverageOfFirstHalfAway) " +
                "OR (G.formationFirstHalfAway = '4-4-2 (3)' " +
                    "AND G.tacticFirstHalfAway = 1" +
                    "AND G.strengthAverageOfFirstHalfAway > G.strengthAverageOfFirstHalfHome) " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getGamesWith442_3OffensiveAsWinningFormationWithout442_3AsOpposingFormation() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.formationFirstHalfHome = '4-4-2 (3)' " +
                "AND G.tacticFirstHalfHome = 1" +
                "AND G.strengthAverageOfFirstHalfHome > G.strengthAverageOfFirstHalfAway " +
                "AND G.formationFirstHalfAway != '4-4-2 (3)') " +
                "OR (G.formationFirstHalfAway = '4-4-2 (3)' " +
                "AND G.tacticFirstHalfAway = 1" +
                "AND G.strengthAverageOfFirstHalfAway > G.strengthAverageOfFirstHalfHome " +
                "AND G.formationFirstHalfHome != '4-4-2 (3)') " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getGames442_3vs442_3() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.formationFirstHalfHome = '4-4-2 (3)' " +
                "AND G.strengthAverageOfFirstHalfHome > G.strengthAverageOfFirstHalfAway " +
                "AND G.formationFirstHalfAway = '4-4-2 (3)') " +
                "OR (G.formationFirstHalfAway = '4-4-2 (3)' " +
                "AND G.strengthAverageOfFirstHalfAway > G.strengthAverageOfFirstHalfHome " +
                "AND G.formationFirstHalfHome = '4-4-2 (3)') " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getGames352_6vs442_1() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.strengthAverageOfFirstHalfHome > G.strengthAverageOfFirstHalfAway " +
                    "AND G.formationFirstHalfHome = '3-5-2 (6)' " +
                    "AND G.formationFirstHalfAway = '4-4-2 (1)') " +
                "OR (G.strengthAverageOfFirstHalfAway > G.strengthAverageOfFirstHalfHome " +
                    "AND G.formationFirstHalfAway = '3-5-2 (6)' " +
                    "AND G.formationFirstHalfHome = '4-4-2 (1)') " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }


    public static List<LmGame> getGamesWith343_1AsWinningFormation() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.strengthAverageOfFirstHalfHome > G.strengthAverageOfFirstHalfAway " +
                "AND G.formationFirstHalfHome = '3-4-3 (1)') " +
                "OR (G.strengthAverageOfFirstHalfAway > G.strengthAverageOfFirstHalfHome " +
                "AND G.formationFirstHalfAway = '3-4-3 (1)') " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    private static List<LmGame> getGamesForSQL(String queryCreateString) throws HibernateException {
        List<LmGame> ret = new ArrayList<>();

        Session session = SessionFactoryFactory.getFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Query query = session.createQuery(queryCreateString);
            List games = query.list();

            System.out.println("Total number of found games: " + games.size());

            for (Iterator iterator = games.iterator(); iterator.hasNext(); ) {

                LmGameHibernateBean gameBean = (LmGameHibernateBean) iterator.next();
                LOGGER.info("Season:  " + gameBean.seasonNumber + " | Game Id: " + gameBean.gameId);
                ret.add(gameBean.toLmGame());
            }


            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("Unable to read games from database: " + ex.getMessage());
            throw ex;

        } finally {
            session.close();
        }

        return ret;
    }
}
