package ligamanager.spion.common.util;

import ligamanager.spion.common.Tactic;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by jpralle on 17.05.2016.
 */
public class TacticTest {

    @Test
    public void testGetFormationFromLegalIdWithUpperCase() {
        String tacticName = "sehr Offensiv";
        Tactic actual = Tactic.getTacticFrom(tacticName);

        assertEquals(Tactic.VERY_OFFENSIVE, actual);
    }

    @Test
    public void testGetFormationFromLegalId() {
        String tacticName = "normal";
        Tactic actual = Tactic.getTacticFrom(tacticName);

        assertEquals(Tactic.NORMAL, actual);
    }

    @Test
    public void testGetFormationFromLegalId2() {
        String tacticName = "defensiv";
        Tactic actual = Tactic.getTacticFrom(tacticName);

        assertEquals(Tactic.DEFENSIVE, actual);
    }

    @Test
    public void testGetFormationFromIllegalId() {
        String tacticName = "neutral";
        Tactic actual = Tactic.getTacticFrom(tacticName);

        assertEquals(Tactic.EMPTY, actual);
    }

}
