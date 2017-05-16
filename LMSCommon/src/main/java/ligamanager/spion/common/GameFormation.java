package ligamanager.spion.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpralle on 17.05.2016.
 */
@Entity
@Table(name = "GAME_FORMATIONS")
public class GameFormation implements Emptyable {

    public static final GameFormation EMPTY = new GameFormation("[no formation]", "[no formation description]");

    public static final GameFormation FORMATION_343_1 = new GameFormation("3-4-3 (1)", "(LV-Loff-RV) (LM-OM-DM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_343_2 = new GameFormation("3-4-3 (2)", "(LV-Loff-RV) (LM-LOM-ROM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_343_3 = new GameFormation("3-4-3 (3)", "(LV-Loff-RV) (LM-LDM-RDM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_343_4 = new GameFormation("3-4-3 (4)", "(LV-Ldef-RV) (LM-OM-DM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_343_5 = new GameFormation("3-4-3 (5)", "(LV-Ldef-RV) (LM-LOM-ROM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_343_6 = new GameFormation("3-4-3 (6)", "(LV-Ldef-RV) (LM-LDM-RDM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_343_7 = new GameFormation("3-4-3 (7)", "(LV-IV-RV) (LM-OM-DM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_343_8 = new GameFormation("3-4-3 (8)", "(LV-IV-RV) (LM-LOM-ROM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_343_9 = new GameFormation("3-4-3 (9)", "(LV-IV-RV) (LM-LDM-RDM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_352_1 = new GameFormation("3-5-2 (1)", "(LV-Loff-RV) (LM-LOM-DM-ROM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_352_2 = new GameFormation("3-5-2 (2)", "(LV-Loff-RV) (LM-LDM-OM-RDM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_352_3 = new GameFormation("3-5-2 (3)", "(LV-Ldef-RV) (LM-LOM-DM-ROM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_352_4 = new GameFormation("3-5-2 (4)", "(LV-Ldef-RV) (LM-LDM-OM-RDM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_352_5 = new GameFormation("3-5-2 (5)", "(LV-IV-RV) (LM-LOM-DM-ROM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_352_6 = new GameFormation("3-5-2 (6)", "(LV-IV-RV) (LM-LDM-OM-RDM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_433_1 = new GameFormation("4-3-3 (1)", "(LV-LIV-RIV-RV) (LM-OM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_433_2 = new GameFormation("4-3-3 (2)", "(LV-LIV-RIV-RV) (LM-DM-RM) (LS-MS-RS)");
    public static final GameFormation FORMATION_442_1 = new GameFormation("4-4-2 (1)", "(LV-LIV-RIV-RV) (LM-OM-DM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_442_2 = new GameFormation("4-4-2 (2)", "(LV-LIV-RIV-RV) (LM-LOM-ROM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_442_3 = new GameFormation("4-4-2 (3)", "(LV-LIV-RIV-RV) (LM-LDM-RDM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_451_1 = new GameFormation("4-5-1 (1)", "(LV-LIV-RIV-RV) (LM-LOM-DM-ROM-RM) (MS)");
    public static final GameFormation FORMATION_451_2 = new GameFormation("4-5-1 (2)", "(LV-LIV-RIV-RV) (LM-LDM-OM-RDM-RM) (MS)");
    public static final GameFormation FORMATION_532_1 = new GameFormation("5-3-2 (1)", "(LV-LIV-Loff-RIV-RV) (LM-OM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_532_2 = new GameFormation("5-3-2 (2)", "(LV-LIV-Loff-RIV-RV) (LM-DM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_532_3 = new GameFormation("5-3-2 (3)", "(LV-LIV-Ldef-RIV-RV) (LM-OM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_532_4 = new GameFormation("5-3-2 (4)", "(LV-LIV-Ldef-RIV-RV) (LM-DM-RM) (LMS-RMS)");
    public static final GameFormation FORMATION_541_1 = new GameFormation("5-4-1 (1)", "(LV-LIV-Loff-RIV-RV) (LM-LOM-ROM-RM) (MS)");
    public static final GameFormation FORMATION_541_2 = new GameFormation("5-4-1 (2)", "(LV-LIV-Loff-RIV-RV) (LM-LDM-RDM-RM) (MS)");
    public static final GameFormation FORMATION_541_3 = new GameFormation("5-4-1 (3)", "(LV-LIV-Loff-RIV-RV) (LM-OM-DM-RM) (MS)");
    public static final GameFormation FORMATION_541_4 = new GameFormation("5-4-1 (4)", "(LV-LIV-Ldef-RIV-RV) (LM-LOM-ROM-RM) (MS)");
    public static final GameFormation FORMATION_541_5 = new GameFormation("5-4-1 (5)", "(LV-LIV-Ldef-RIV-RV) (LM-LDM-RDM-RM) (MS)");
    public static final GameFormation FORMATION_541_6 = new GameFormation("5-4-1 (6)", "(LV-LIV-Ldef-RIV-RV) (LM-OM-DM-RM) (MS)");

    public static final List<GameFormation> ALL = getAll();

    @Id
    @Column(name = "GAME_FORMATION_ID")
    private final String id;
    @Column(name = "GAME_FORMATION_DETAIL")
    private final String detail;

    private static List<GameFormation> getAll() {
        List<GameFormation> ret = new ArrayList<GameFormation>();
        ret.add(FORMATION_343_1);
        ret.add(FORMATION_343_2);
        ret.add(FORMATION_343_3);
        ret.add(FORMATION_343_4);
        ret.add(FORMATION_343_5);
        ret.add(FORMATION_343_6);
        ret.add(FORMATION_343_7);
        ret.add(FORMATION_343_8);
        ret.add(FORMATION_343_9);
        ret.add(FORMATION_352_1);
        ret.add(FORMATION_352_2);
        ret.add(FORMATION_352_3);
        ret.add(FORMATION_352_4);
        ret.add(FORMATION_352_5);
        ret.add(FORMATION_352_6);
        ret.add(FORMATION_433_1);
        ret.add(FORMATION_433_2);
        ret.add(FORMATION_442_1);
        ret.add(FORMATION_442_2);
        ret.add(FORMATION_442_3);
        ret.add(FORMATION_451_1);
        ret.add(FORMATION_451_2);
        ret.add(FORMATION_532_1);
        ret.add(FORMATION_532_2);
        ret.add(FORMATION_532_3);
        ret.add(FORMATION_532_4);
        ret.add(FORMATION_541_1);
        ret.add(FORMATION_541_2);
        ret.add(FORMATION_541_3);
        ret.add(FORMATION_541_4);
        ret.add(FORMATION_541_5);
        ret.add(FORMATION_541_6);
        return ret;
    }


    public static GameFormation getFormationFrom(String formationId) {
        GameFormation ret = GameFormation.EMPTY;

        for (GameFormation formation :
                ALL) {
            if (formation.getId().equals(formationId)) {
                ret = formation;
                break;
            }
        }

        return ret;
    }

    private GameFormation() {
        this.id = EMPTY.getId();
        this.detail = EMPTY.getDetail();
    }

    private GameFormation(String id, String detail) {
        this.id = id;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isEmpty() {
        return equals(EMPTY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameFormation that = (GameFormation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return detail != null ? detail.equals(that.detail) : that.detail == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GameFormation{" +
                "id='" + id + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
