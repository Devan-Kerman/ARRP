package net.devtech.arrp.json.recipe;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class JStackedResult extends JResult {
    protected int count;

    JStackedResult(final String id) {
        super(id);

        this.count = 1;
    }

    public static JResult itemStack(final Item item, final int count) {
        return stackedResult(Registry.ITEM.getId(item).toString(), count);
    }

    public static JStackedResult stackedResult(final String id, final int count) {
        final JStackedResult stackedResult = new JStackedResult(id);

        stackedResult.count = count;

        return stackedResult;
    }
}
