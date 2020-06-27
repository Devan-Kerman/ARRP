package net.devtech.arrp.json.recipe;

public class JStackedResult extends JResult {
    protected int count;

    JStackedResult(final String id) {
        super(id);

        this.count = 1;
    }
}
