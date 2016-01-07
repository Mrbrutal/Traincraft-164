package traincraft.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import traincraft.client.render.models.blocks.ModelBridgePillar;
import traincraft.common.tile.TileBridgePillar;

public class RenderBridgePillar extends TileEntitySpecialRenderer {
	private ModelBridgePillar modelBridgePillar = new ModelBridgePillar();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		modelBridgePillar.render((TileBridgePillar) tileEntity, x, y, z);
	}
}
