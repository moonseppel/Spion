package ligamanager.spion.analyzer.useCases;

import ligamanager.spion.analyzer.TestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jpralle on 01.03.2016.
 */
public class BasicActionsTest {

    @Test
    public void testLoginAndChooseFirstTeam() throws Exception {
        assertTrue(BasicActions.loginAndChooseFirstTeam(TestData.USERNAME, TestData.PASSWORD));
    }
}