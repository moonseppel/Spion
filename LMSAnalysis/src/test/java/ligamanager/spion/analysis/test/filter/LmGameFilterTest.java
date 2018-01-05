package ligamanager.spion.analysis.test.filter;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LmGameFilterTest {

    @Test
    public void testFilterForAllGames() {
        String expected = "";
        LmGameFilter subject = new EmptyFilter();

        String actual = subject.generateQueryPart();

        assertThat(actual, is(expected));
    }

    @Test
    public void testFilterForAllGamesWithinStrengthLimits() {
        String expected = "ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";
        LmGameFilter subject = new EmptyFilter();

        String actual = subject.generateQueryPart();

        assertThat(actual, is(expected));
    }

    @Test
    public void testFilterForAllGamesWithSameTacticAndFormationWithinStrengthLimits() {
        String expected = "G.formationFirstHalfHome = G.formationFirstHalfAway " +
                "AND G.tacticFirstHalfHome = G.tacticFirstHalfAway " +
                "AND ABS(G.strengthAverageOfFirstHalfHome - G.strengthAverageOfFirstHalfAway) < 80";
        LmGameFilter subject = new EmptyFilter();

        String actual = subject.generateQueryPart();

        assertThat(actual, is(expected));
    }
}
