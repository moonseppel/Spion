package ligamanager.spion.analysis.test.filter;

public class EmptyFilter implements LmGameFilter {

    @Override
    public String generateQueryPart() {
        return "";
    }
}
