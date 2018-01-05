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

public class FindGamesForFormationAndTacticFullTime {

    private static final Logger LOGGER = Logger.getLogger(FindGamesForFormationAndTacticFullTime.class);

    public static List<LmGame> getGamesWithSameFormationAndTactic() throws HibernateException {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE G.formationFirstHalfHome = G.formationFirstHalfAway " +
                "AND G.formationSecondHalfHome = G.formationSecondHalfAway " +
                "AND G.formationFirstHalfHome = G.formationSecondHalfHome " +
                "AND G.tacticFirstHalfHome = G.tacticFirstHalfAway " +
                "AND G.tacticSecondHalfHome = G.tacticSecondHalfAway " +
                "AND G.tacticFirstHalfHome = G.tacticSecondHalfHome " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getGames442_3offvs451_2sd() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.formationFirstHalfHome = '4-5-1 (2)' " +
                "AND G.formationSecondHalfHome = '4-5-1 (2)' " +
//                "AND G.tacticFirstHalfHome = -2 " +
//                "AND G.tacticSecondHalfHome = -2 " +
                "AND G.formationFirstHalfAway = '4-4-2 (3)' " +
                "AND G.formationSecondHalfAway = '4-4-2 (3)' " +
//                "AND G.tacticFirstHalfAway = 1 " +
//                "AND G.tacticSecondHalfAway = 1 " +
                "AND G.strengthAverageOfFirstHalfHome <= G.strengthAverageOfFirstHalfAway) " +
                "OR (G.formationFirstHalfAway = '4-4-2 (3)' " +
                "AND G.formationSecondHalfHome = '4-4-2 (3)' " +
//                "AND G.tacticFirstHalfHome = 1 " +
//                "AND G.tacticSecondHalfHome = 1 " +
                "AND G.formationFirstHalfAway = '4-5-1 (2)' " +
                "AND G.formationSecondHalfAway = '4-5-1 (2)' " +
//                "AND G.tacticFirstHalfAway = -2 " +
//                "AND G.tacticSecondHalfAway = -2 " +
                "AND G.strengthAverageOfFirstHalfHome > G.strengthAverageOfFirstHalfAway) " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";

        ret = getGamesForSQL(queryCreateString);

        return ret;
    }

    public static List<LmGame> getGames451_2sdvs442_3off() {
        List<LmGame> ret;

        String queryCreateString = "FROM LmGameHibernateBean G " +
                "WHERE (G.formationFirstHalfHome = '4-5-1 (2)' " +
                "AND G.formationSecondHalfHome = '4-5-1 (2)' " +
                "AND G.tacticFirstHalfHome = -2 " +
                "AND G.tacticSecondHalfHome = -2 " +
                "AND G.formationFirstHalfAway = '4-4-2 (3)' " +
                "AND G.formationSecondHalfAway = '4-4-2 (3)' " +
                "AND G.tacticFirstHalfAway = 1 " +
                "AND G.tacticSecondHalfAway = 1 " +
                "AND G.strengthAverageOfFirstHalfHome >= G.strengthAverageOfFirstHalfAway) " +
                "OR (G.formationFirstHalfAway = '4-4-2 (3)' " +
                "AND G.formationSecondHalfHome = '4-4-2 (3)' " +
                "AND G.tacticFirstHalfHome = 1 " +
                "AND G.tacticSecondHalfHome = 1 " +
                "AND G.formationFirstHalfAway = '4-5-1 (2)' " +
                "AND G.formationSecondHalfAway = '4-5-1 (2)' " +
                "AND G.tacticFirstHalfAway = -2 " +
                "AND G.tacticSecondHalfAway = -2 " +
                "AND G.strengthAverageOfFirstHalfHome < G.strengthAverageOfFirstHalfAway) " +
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
