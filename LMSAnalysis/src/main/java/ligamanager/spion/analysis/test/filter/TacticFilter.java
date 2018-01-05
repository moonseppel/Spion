package ligamanager.spion.analysis.test.filter;

import ligamanager.spion.common.Tactic;

public class TacticFilter implements LmGameFilter {

    private final FilterHalfs appliesTo;
    private final Tactic tactic;

    public TacticFilter(Tactic tactic, FilterHalfs appliesTo) {
        this.tactic = tactic;
        this.appliesTo = appliesTo;
    }

    @Override
    public String generateQueryPart() {
        return null;
    }
}
