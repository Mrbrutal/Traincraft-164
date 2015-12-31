/**
 * A track that detects all instance of Tank carts
 * 
 * @author Spitfire4466
 */
package traincraft.common.blocks.tracks;

import mods.railcraft.api.tracks.ITrackEmitter;
import net.minecraft.entity.item.EntityMinecart;
import traincraft.api.LiquidTank;
import traincraft.common.library.TrackIDs;

public class BlockDetectorTankCartsTrack extends BlockDetectorTrack implements ITrackEmitter {

	@Override
	public TrackIDs getTrackType() {
		return TrackIDs.DETECTOR_TANK_CARTS;
	}

	@Override
	public void onMinecartPass(EntityMinecart cart) {
		if (cart instanceof LiquidTank) {
			setTrackPowering();
		}
	}
}