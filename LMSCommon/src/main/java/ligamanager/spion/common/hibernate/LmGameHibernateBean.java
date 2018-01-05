package ligamanager.spion.common.hibernate;

import ligamanager.spion.common.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;

@Entity
@IdClass(GameIds.class)
@Table(name = "LM_GAMES")
public class LmGameHibernateBean {

    @Id
    @Column(name = "GAME_ID")
    public int gameId = -1;
    @Id
    @Column(name = "SEASON_NUMBER")
    public int seasonNumber = -1;
    @Column(name = "HAS_EXTRA_TIME")
    public boolean hasExtraTime = false;
    @Column(name = "HAS_PENALTY_SHOOTING")
    public boolean hasPenaltyShooting = false;
    @Column(name = "HOME_TEAM_NAME")
    public String homeTeamName = null;
    @Column(name = "AWAY_TEAM_NAME")
    public String awayTeamName = null;
    @Column(name = "END_RESULT_HOME")
    public int endResultHome = -1;
    @Column(name = "END_RESULT_AWAY")
    public int endResultAway = -1;
    @Column(name = "RESULT_FIRST_HALF_HOME")
    public int resultFirstHalfHome = -1;
    @Column(name = "RESULT_FIRST_HALF_AWAY")
    public int resultFirstHalfAway = -1;
    @Column(name = "RESULT_SECOND_HALF_HOME")
    public int resultSecondHalfHome = -1;
    @Column(name = "RESULT_SECOND_HALF_AWAY")
    public int resultSecondHalfAway = -1;
    @Column(name = "RESULT_EXTRA_TIME_HOME")
    public int resultExtraTimeHome = -1;
    @Column(name = "RESUKT_EXTRA_TIME_AWAY")
    public int resultExtraTimeAway = -1;
    @Column(name = "RESULT_PENALTY_SHOOTING_HOME")
    public int resultPenaltyShootingHome = -1;
    @Column(name = "RESUKT_PENALTY_SHOOTING_AWAY")
    public int resultPenaltyShootingAway = -1;
    @OneToOne()
    @JoinColumn(name = "FORMATION_FIRST_HALF_HOME")
    public GameFormation formationFirstHalfHome = null;
    @OneToOne()
    @JoinColumn(name = "FORMATION_SECOND_HALF_HOME")
    public GameFormation formationSecondHalfHome = null;
    @OneToOne()
    @JoinColumn(name = "FORMATION_EXTRA_TIME_HOME")
    public GameFormation formationExtraTimeHome = null;
    @OneToOne()
    @JoinColumn(name = "FORMATION_FIRST_HALF_AWAY")
    public GameFormation formationFirstHalfAway = null;
    @OneToOne()
    @JoinColumn(name = "FORMATION_SECOND_HALF_AWAY")
    public GameFormation formationSecondHalfAway = null;
    @OneToOne()
    @JoinColumn(name = "FORMATION_EXTRA_TIME_AWAY")
    public GameFormation formationExtraTimeAway = null;
    @OneToOne()
    @JoinColumn(name = "TACTIC_FIRST_HALF_HOME")
    public Tactic tacticFirstHalfHome = null;
    @OneToOne()
    @JoinColumn(name = "TACTIC_SECOND_HALF_HOME")
    public Tactic tacticSecondHalfHome = null;
    @OneToOne()
    @JoinColumn(name = "TACTIC_EXTRA_TIME_HOME")
    public Tactic tacticExtraTimeHome = null;
    @OneToOne()
    @JoinColumn(name = "TACTIC_FIRST_HALF_AWAY")
    public Tactic tacticFirstHalfAway = null;
    @OneToOne()
    @JoinColumn(name = "TACTIC_SECOND_HALF_AWAY")
    public Tactic tacticSecondHalfAway = null;
    @OneToOne()
    @JoinColumn(name = "TACTIC_EXTRA_TIME_AWAY")
    public Tactic tacticExtraTimeAway = null;
    @Column(name = "STRENGTH_BEGIN_FIRST_HALF_HOME")
    public int strengthBeginOfFirstHalfHome = -1;
    @Column(name = "STRENGTH_BEGIN_FIRST_HALF_AWAY")
    public int strengthBeginOfFirstHalfAway = -1;
    @Column(name = "STRENGTH_BEGIN_SECOND_HALF_HOME")
    public int strengthBeginOfSecondHalfHome = -1;
    @Column(name = "STRENGTH_BEGIN_SECOND_HALF_AWAY")
    public int strengthBeginOfSecondHalfAway = -1;
    @Column(name = "STRENGTH_BEGIN_EXTRA_TIME_HOME")
    public int strengthBeginOfExtraTimeHome = -1;
    @Column(name = "STRENGTH_BEGIN_EXTRA_TIME_AWAY")
    public int strengthBeginOfExtraTimeAway = -1;
    @Column(name = "STRENGTH_AVERAGE_FIRST_HALF_HOME")
    public int strengthAverageOfFirstHalfHome = -1;
    @Column(name = "STRENGTH_AVERAGE_FIRST_HALF_AWAY")
    public int strengthAverageOfFirstHalfAway = -1;
    @Column(name = "STRENGTH_AVERAGE_SECOND_HALF_HOME")
    public int strengthAverageOfSecondHalfHome = -1;
    @Column(name = "STRENGTH_AVERAGE_SECOND_HALF_AWAY")
    public int strengthAverageOfSecondHalfAway = -1;
    @Column(name = "STRENGTH_AVERAGE_EXTRA_TIME_HOME")
    public int strengthAverageOfExtraTimeHome = -1;
    @Column(name = "STRENGTH_AVERAGE_EXTRA_TIME_AWAY")
    public int strengthAverageOfExtraTimeAway = -1;
    @Column(name = "STRENGTH_END_FIRST_HALF_HOME")
    public int strengthEndOfFirstHalfHome = -1;
    @Column(name = "STRENGTH_END_FIRST_HALF_AWAY")
    public int strengthEndOfFirstHalfAway = -1;
    @Column(name = "STRENGTH_END_SECOND_HALF_HOME")
    public int strengthEndOfSecondHalfHome = -1;
    @Column(name = "STRENGTH_END_SECOND_HALF_AWAY")
    public int strengthEndOfSecondHalfAway = -1;
    @Column(name = "STRENGTH_END_EXTRA_TIME_HOME")
    public int strengthEndOfExtraTimeHome = -1;
    @Column(name = "STRENGTH_END_EXTRA_TIME_AWAY")
    public int strengthEndOfExtraTimeAway = -1;

    public LmGameHibernateBean() {
    }

    public GameIds save() {

        Session session = SessionFactoryFactory.getFactory().openSession();
        Transaction tx = null;
        GameIds ids = null;

        try {
            tx = session.beginTransaction();

            ids = (GameIds) session.save(this);
            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            throw ex;

        } finally {
            session.close();
        }

        return ids;
    }

    public static LmGameHibernateBean read(GameIds ids) {
        return read(ids.getGameId(), ids.getSeasonNumber());
    }

    public static LmGameHibernateBean read(int gameId, int seasonNumber) {

        LmGameHibernateBean ret = null;
        Session session = SessionFactoryFactory.getFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Query query = session.createQuery("FROM LmGameHibernateBean G WHERE G.gameId = :game_id AND G.seasonNumber = :season_number");
            query.setParameter("game_id", gameId);
            query.setParameter("season_number",seasonNumber);

            if(query.list().size() > 0) {
                ret = (LmGameHibernateBean) query.list().get(0);
            }

            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            throw ex;

        } finally {
            session.close();
        }

        return ret;
    }

    public void delete() {

        Session session = SessionFactoryFactory.getFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.delete(this);

            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            throw ex;

        } finally {
            session.close();
        }
    }

    public LmGame toLmGame() {
        LmGame ret = new LmGame();

        ret.ids = new GameIds(gameId, seasonNumber);

        ret.hasExtraTime = hasExtraTime;
        ret.hasPenaltyShooting = hasPenaltyShooting;

        ret.teamNames = new HomeAwayPair<String>(homeTeamName, awayTeamName);

        ret.endResult = new GameResult(endResultHome, endResultAway);
        GameResult firstHalfResult = new GameResult(resultFirstHalfHome, resultFirstHalfAway);
        GameResult secondHalfResult = new GameResult(resultSecondHalfHome, resultSecondHalfAway);
        GameResult extraTimeResult = new GameResult(resultExtraTimeHome, resultExtraTimeAway);
        GameResult penaltyResult = new GameResult(resultPenaltyShootingHome, resultPenaltyShootingAway);
        ret.interimResults = new GameValuesInclPenalties<>(firstHalfResult, secondHalfResult, extraTimeResult, penaltyResult);

        HomeAwayPair<GameFormation> firstHalfFormations = new HomeAwayPair<>(formationFirstHalfHome, formationFirstHalfAway);
        HomeAwayPair<GameFormation> secondHalfFormations = new HomeAwayPair<>(formationSecondHalfHome, formationSecondHalfAway);
        HomeAwayPair<GameFormation> extraTimeFormations = new HomeAwayPair<>(formationExtraTimeHome, formationExtraTimeAway);
        ret.formations = new GameValues<>(firstHalfFormations, secondHalfFormations, extraTimeFormations);

        HomeAwayPair<Tactic> firstHalfTactics = new HomeAwayPair<>(tacticFirstHalfHome, tacticFirstHalfAway);
        HomeAwayPair<Tactic> secondHalfTactics = new HomeAwayPair<>(tacticSecondHalfHome, tacticSecondHalfAway);
        HomeAwayPair<Tactic> extraTimeTactics = new HomeAwayPair<>(tacticExtraTimeHome, tacticExtraTimeAway);
        ret.tactics = new GameValues<>(firstHalfTactics, secondHalfTactics, extraTimeTactics);

        GameResult firstHalfBeginStrength = new GameResult(strengthBeginOfFirstHalfHome, strengthBeginOfFirstHalfAway);
        GameResult secondHalfBeginStrength = new GameResult(strengthBeginOfSecondHalfHome, strengthBeginOfSecondHalfAway);
        GameResult extraTimeBeginStrength = new GameResult(strengthBeginOfExtraTimeHome, strengthBeginOfExtraTimeAway);
        ret.strengthBeginOfHalf = new GameValues<>(firstHalfBeginStrength, secondHalfBeginStrength, extraTimeBeginStrength);

        GameResult firstHalfAverageStrength = new GameResult(strengthAverageOfFirstHalfHome, strengthAverageOfFirstHalfAway);
        GameResult secondHalfAverageStrength = new GameResult(strengthAverageOfSecondHalfHome, strengthAverageOfSecondHalfAway);
        GameResult extraTimeAverageStrength = new GameResult(strengthAverageOfExtraTimeHome, strengthAverageOfExtraTimeAway);
        ret.strengthAverage = new GameValues<>(firstHalfAverageStrength, secondHalfAverageStrength, extraTimeAverageStrength);

        GameResult firstHalfEndStrength = new GameResult(strengthEndOfFirstHalfHome, strengthEndOfFirstHalfAway);
        GameResult secondHalfEndStrength = new GameResult(strengthEndOfSecondHalfHome, strengthEndOfSecondHalfAway);
        GameResult extraTimeEndStrength = new GameResult(strengthEndOfExtraTimeHome, strengthEndOfExtraTimeAway);
        ret.strengthEndOfHalf = new GameValues<>(firstHalfEndStrength, secondHalfEndStrength, extraTimeEndStrength);

        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LmGameHibernateBean that = (LmGameHibernateBean) o;

        if (gameId != that.gameId) return false;
        if (seasonNumber != that.seasonNumber) return false;
        if (hasExtraTime != that.hasExtraTime) return false;
        if (hasPenaltyShooting != that.hasPenaltyShooting) return false;
        if (endResultHome != that.endResultHome) return false;
        if (endResultAway != that.endResultAway) return false;
        if (resultFirstHalfHome != that.resultFirstHalfHome) return false;
        if (resultFirstHalfAway != that.resultFirstHalfAway) return false;
        if (resultSecondHalfHome != that.resultSecondHalfHome) return false;
        if (resultSecondHalfAway != that.resultSecondHalfAway) return false;
        if (resultExtraTimeHome != that.resultExtraTimeHome) return false;
        if (resultExtraTimeAway != that.resultExtraTimeAway) return false;
        if (resultPenaltyShootingHome != that.resultPenaltyShootingHome) return false;
        if (resultPenaltyShootingAway != that.resultPenaltyShootingAway) return false;
        if (strengthBeginOfFirstHalfHome != that.strengthBeginOfFirstHalfHome) return false;
        if (strengthBeginOfFirstHalfAway != that.strengthBeginOfFirstHalfAway) return false;
        if (strengthBeginOfSecondHalfHome != that.strengthBeginOfSecondHalfHome) return false;
        if (strengthBeginOfSecondHalfAway != that.strengthBeginOfSecondHalfAway) return false;
        if (strengthBeginOfExtraTimeHome != that.strengthBeginOfExtraTimeHome) return false;
        if (strengthBeginOfExtraTimeAway != that.strengthBeginOfExtraTimeAway) return false;
        if (strengthAverageOfFirstHalfHome != that.strengthAverageOfFirstHalfHome) return false;
        if (strengthAverageOfFirstHalfAway != that.strengthAverageOfFirstHalfAway) return false;
        if (strengthAverageOfSecondHalfHome != that.strengthAverageOfSecondHalfHome) return false;
        if (strengthAverageOfSecondHalfAway != that.strengthAverageOfSecondHalfAway) return false;
        if (strengthAverageOfExtraTimeHome != that.strengthAverageOfExtraTimeHome) return false;
        if (strengthAverageOfExtraTimeAway != that.strengthAverageOfExtraTimeAway) return false;
        if (strengthEndOfFirstHalfHome != that.strengthEndOfFirstHalfHome) return false;
        if (strengthEndOfFirstHalfAway != that.strengthEndOfFirstHalfAway) return false;
        if (strengthEndOfSecondHalfHome != that.strengthEndOfSecondHalfHome) return false;
        if (strengthEndOfSecondHalfAway != that.strengthEndOfSecondHalfAway) return false;
        if (strengthEndOfExtraTimeHome != that.strengthEndOfExtraTimeHome) return false;
        if (strengthEndOfExtraTimeAway != that.strengthEndOfExtraTimeAway) return false;
        if (homeTeamName != null ? !homeTeamName.equals(that.homeTeamName) : that.homeTeamName != null) return false;
        if (awayTeamName != null ? !awayTeamName.equals(that.awayTeamName) : that.awayTeamName != null) return false;
        if (formationFirstHalfHome != null ? !formationFirstHalfHome.equals(that.formationFirstHalfHome) : that.formationFirstHalfHome != null)
            return false;
        if (formationSecondHalfHome != null ? !formationSecondHalfHome.equals(that.formationSecondHalfHome) : that.formationSecondHalfHome != null)
            return false;
        if (formationExtraTimeHome != null ? !formationExtraTimeHome.equals(that.formationExtraTimeHome) : that.formationExtraTimeHome != null)
            return false;
        if (formationFirstHalfAway != null ? !formationFirstHalfAway.equals(that.formationFirstHalfAway) : that.formationFirstHalfAway != null)
            return false;
        if (formationSecondHalfAway != null ? !formationSecondHalfAway.equals(that.formationSecondHalfAway) : that.formationSecondHalfAway != null)
            return false;
        if (formationExtraTimeAway != null ? !formationExtraTimeAway.equals(that.formationExtraTimeAway) : that.formationExtraTimeAway != null)
            return false;
        if (tacticFirstHalfHome != null ? !tacticFirstHalfHome.equals(that.tacticFirstHalfHome) : that.tacticFirstHalfHome != null) return false;
        if (tacticSecondHalfHome != null ? !tacticSecondHalfHome.equals(that.tacticSecondHalfHome) : that.tacticSecondHalfHome != null) return false;
        if (tacticExtraTimeHome != null ? !tacticExtraTimeHome.equals(that.tacticExtraTimeHome) : that.tacticExtraTimeHome != null) return false;
        if (tacticFirstHalfAway != null ? !tacticFirstHalfAway.equals(that.tacticFirstHalfAway) : that.tacticFirstHalfAway != null) return false;
        if (tacticSecondHalfAway != null ? !tacticSecondHalfAway.equals(that.tacticSecondHalfAway) : that.tacticSecondHalfAway != null) return false;
        return tacticExtraTimeAway != null ? tacticExtraTimeAway.equals(that.tacticExtraTimeAway) : that.tacticExtraTimeAway == null;

    }

    @Override
    public int hashCode() {
        int result = gameId;
        result = 31 * result + seasonNumber;
        result = 31 * result + (hasExtraTime ? 1 : 0);
        result = 31 * result + (hasPenaltyShooting ? 1 : 0);
        result = 31 * result + (homeTeamName != null ? homeTeamName.hashCode() : 0);
        result = 31 * result + (awayTeamName != null ? awayTeamName.hashCode() : 0);
        result = 31 * result + endResultHome;
        result = 31 * result + endResultAway;
        result = 31 * result + resultFirstHalfHome;
        result = 31 * result + resultFirstHalfAway;
        result = 31 * result + resultSecondHalfHome;
        result = 31 * result + resultSecondHalfAway;
        result = 31 * result + resultExtraTimeHome;
        result = 31 * result + resultExtraTimeAway;
        result = 31 * result + resultPenaltyShootingHome;
        result = 31 * result + resultPenaltyShootingAway;
        result = 31 * result + (formationFirstHalfHome != null ? formationFirstHalfHome.hashCode() : 0);
        result = 31 * result + (formationSecondHalfHome != null ? formationSecondHalfHome.hashCode() : 0);
        result = 31 * result + (formationExtraTimeHome != null ? formationExtraTimeHome.hashCode() : 0);
        result = 31 * result + (formationFirstHalfAway != null ? formationFirstHalfAway.hashCode() : 0);
        result = 31 * result + (formationSecondHalfAway != null ? formationSecondHalfAway.hashCode() : 0);
        result = 31 * result + (formationExtraTimeAway != null ? formationExtraTimeAway.hashCode() : 0);
        result = 31 * result + (tacticFirstHalfHome != null ? tacticFirstHalfHome.hashCode() : 0);
        result = 31 * result + (tacticSecondHalfHome != null ? tacticSecondHalfHome.hashCode() : 0);
        result = 31 * result + (tacticExtraTimeHome != null ? tacticExtraTimeHome.hashCode() : 0);
        result = 31 * result + (tacticFirstHalfAway != null ? tacticFirstHalfAway.hashCode() : 0);
        result = 31 * result + (tacticSecondHalfAway != null ? tacticSecondHalfAway.hashCode() : 0);
        result = 31 * result + (tacticExtraTimeAway != null ? tacticExtraTimeAway.hashCode() : 0);
        result = 31 * result + strengthBeginOfFirstHalfHome;
        result = 31 * result + strengthBeginOfFirstHalfAway;
        result = 31 * result + strengthBeginOfSecondHalfHome;
        result = 31 * result + strengthBeginOfSecondHalfAway;
        result = 31 * result + strengthBeginOfExtraTimeHome;
        result = 31 * result + strengthBeginOfExtraTimeAway;
        result = 31 * result + strengthAverageOfFirstHalfHome;
        result = 31 * result + strengthAverageOfFirstHalfAway;
        result = 31 * result + strengthAverageOfSecondHalfHome;
        result = 31 * result + strengthAverageOfSecondHalfAway;
        result = 31 * result + strengthAverageOfExtraTimeHome;
        result = 31 * result + strengthAverageOfExtraTimeAway;
        result = 31 * result + strengthEndOfFirstHalfHome;
        result = 31 * result + strengthEndOfFirstHalfAway;
        result = 31 * result + strengthEndOfSecondHalfHome;
        result = 31 * result + strengthEndOfSecondHalfAway;
        result = 31 * result + strengthEndOfExtraTimeHome;
        result = 31 * result + strengthEndOfExtraTimeAway;
        return result;
    }

}
