package net.devtech.rrp;

import net.fabricmc.api.ModInitializer;
import sun.security.util.BitArray;
import java.util.BitSet;
import java.util.logging.Logger;

// todo block models
// todo sound
// todo add more examples in docs

// note for half
// remember: never use resource pack providers for assets, fabric API clears and rebuilds the resource pack list
// last time you tried this, you spent 5 hours debugging that, so don't do it again
public class RRP implements ModInitializer {
	public static final String MOD_ID = "runtime_resource_pack";
	@Override
	public void onInitialize() {
		Logger.getLogger("RRP").severe("Run from it... Dread it... but RRP still arrives.");
		// example code
		/*Registry.register(ITEM, TEST_ITEM, new Item(new Item.Settings().group(ItemGroup.MISC)));
		Block testBlock = new Block(FabricBlockSettings.of(Material.METAL).breakByHand(true).build());
		Registry.register(BLOCK, TEST_BLOCK, testBlock);
		Registry.register(ITEM, TEST_BLOCK, new BlockItem(testBlock, new Item.Settings().group(ItemGroup.MISC)));*/
	}
}