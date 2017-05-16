package ligamanager.spion.reader.util;

import ligamanager.spion.common.GameFormation;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by jpralle on 17.05.2016.
 */
public class GameFormationTest {

    @Test
    public void testGetFormationFromLegalId() {
        String formationId = "4-4-2 (1)";
        GameFormation actual = GameFormation.getFormationFrom(formationId);

        assertEquals(GameFormation.FORMATION_442_1, actual);
    }

    @Test
    public void testGetFormationFromLegalId2() {
        String formationId = "5-4-1 (2)";
        GameFormation actual = GameFormation.getFormationFrom(formationId);

        assertEquals(GameFormation.FORMATION_541_2, actual);
    }

    @Test
    public void testGetFormationFromLegalId3() {
        String formationId = "3-4-3 (7)";
        GameFormation actual = GameFormation.getFormationFrom(formationId);

        assertEquals(GameFormation.FORMATION_343_7, actual);
    }

    @Test
    public void testGetFormationFromIllegalId() {
        String formationId = "4-4-2(1)";
        GameFormation actual = GameFormation.getFormationFrom(formationId);

        assertEquals(GameFormation.EMPTY, actual);
    }
}
