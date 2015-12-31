/**
 * A track that detects all instance of Freight
 * 
 * @author Spitfire4466
 */
package traincraft.common.blocks.tracks;

import mods.railcraft.api.tracks.ITrackEmitter;
import net.minecraft.entity.item.EntityMinecart;
import traincraft.api.Freight;
import traincraft.common.library.TrackIDs;

public class BlockDetectorFreightTrack extends BlockDetectorTrack implements ITrackEmitter {

	@Override
	public TrackIDs getTrackType() {
		return TrackIDs.DETECTOR_FREIGHT;
	}

	@Override
	public void onMinecartPass(EntityMinecart cart) {
		if (cart instanceof Freight) {
			setTrackPowering();
		}
	}

}
