package ligamanager.spion.analyzer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpralle on 17.05.2016.
 */
public class Tactic implements Emptyable {

    public static final Tactic EMPTY = new Tactic("[no tactic]");
    public static final Tactic VERY_OFFENSIVE = new Tactic("sehr offensiv");
    public static final Tactic OFFENSIVE = new Tactic("offensiv");
    public static final Tactic NORMAL = new Tactic("normal");
    public static final Tactic DEFENSIVE = new Tactic("defensiv");
    public static final Tactic VERY_DEFENSIVE = new Tactic("sehr defensiv");

    public static final List<Tactic> ALL_FORMATIONS = getAllFormations();

    private final String name;

    private static List<Tactic> getAllFormations() {
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
                ALL_FORMATIONS) {
            if (formation.getName().equalsIgnoreCase(name)) {
                ret = formation;
                break;
            }
        }

        return ret;
    }

    private Tactic(String name) {
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
