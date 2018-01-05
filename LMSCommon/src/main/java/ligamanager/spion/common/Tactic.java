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
@Table(name = "TACTICS")
public class Tactic implements Emptyable {

    public static final Tactic EMPTY = new Tactic(Integer.MIN_VALUE, "[no tactic]");

    public static final Tactic VERY_OFFENSIVE = new Tactic(2, "sehr offensiv");
    public static final Tactic OFFENSIVE = new Tactic(1, "offensiv");
    public static final Tactic NORMAL = new Tactic(0, "normal");
    public static final Tactic DEFENSIVE = new Tactic(-1, "defensiv");
    public static final Tactic VERY_DEFENSIVE = new Tactic(-2, "sehr defensiv");

    public static final List<Tactic> ALL = getAll();

    @Id
    @Column(name = "TACTIC_ID")
    private final int id;

    @Column(name = "TACTIC_NAME")
    private final String name;

    private static List<Tactic> getAll() {
        List<Tactic> ret = new ArrayList<Tactic>();
        ret.add(VERY_OFFENSIVE);
        ret.add(OFFENSIVE);
        ret.add(NORMAL);
        ret.add(DEFENSIVE);
        ret.add(VERY_DEFENSIVE);
        return ret;
    }


    public static Tactic getTacticFrom(String name) {
        Tactic ret = Tactic.EMPTY;

        for (Tactic formation :
                ALL) {
            if (formation.getName().equalsIgnoreCase(name)) {
                ret = formation;
                break;
            }
        }

        return ret;
    }

    private Tactic() {
        this.id = EMPTY.id;
        this.name = EMPTY.getName();
    }

    private Tactic(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        return equals(EMPTY);
    }

    @Override
    public String toString() {
        return "Tactic{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tactic tactic = (Tactic) o;

        return name != null ? name.equals(tactic.name) : tactic.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
