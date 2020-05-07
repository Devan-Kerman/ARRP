package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.devtech.arrp.json.blockstate.JMultipart;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.blockstate.JWhen;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.minecraft.util.math.Direction;

import static net.devtech.arrp.json.blockstate.JState.*;
import static net.devtech.arrp.json.models.JModel.model;
import static net.devtech.arrp.json.models.JModel.*;

public class RRPPreTest {
	public static void main(String[] args) {
		JState iron_block = state(variant(JState.model("block/iron_block")));
		JState oak_fence = state(multipart(JState.model("block/oak_fence_post")), multipart(JState.model("block/oak_fence_side").uvlock()).when(when().add("north", "true")), multipart(JState.model("block/oak_fence_side").y(90).uvlock()).when(when().add(
		"east", "true")), multipart(JState.model("block/oak_fence_side").y(180).uvlock()).when(when().add("south", "true")), multipart(JState.model("block/oak_fence_side").y(270).uvlock()).when(when().add("west", "true")));

		JModel model =
		model().textures(textures().var("all", "block/bamboo_stalk").particle("block/bamboo_stalk")).element(element().from(7, 0, 7).to(9, 16, 9).faces(faces().down(face("all").cullface(Direction.DOWN).uv(13, 4, 15, 6)).up(face("all").cullface(Direction.UP).uv(13, 0, 15, 2)).north(face("all").uv(9, 0, 11, 16)).south(face("all").uv(9, 0, 11, 16)).west(face("all").uv(9, 0, 11, 16)).east(face("all").uv(9, 0, 11, 16))));

		Gson gson =
		new GsonBuilder().registerTypeAdapter(JMultipart.class, new JMultipart.Serializer()).registerTypeAdapter(JWhen.class, new JWhen.Serializer()).registerTypeAdapter(JState.class, new JState.Serializer()).registerTypeAdapter(JVariant.class,
		                                                                                                                                                                                                                             new JVariant.Serializer()).registerTypeAdapter(JTextures.class, new JTextures.Serializer()).setPrettyPrinting().create();
		//System.out.println(gson.toJson(iron_block));
		//System.out.println(gson.toJson(oak_fence));
		System.out.println(gson.toJson(model));
	}
}
