/**
 * A track that detects all instance of DieselTrain
 * 
 * @author Spitfire4466
 */
package traincraft.common.blocks.tracks;

import mods.railcraft.api.tracks.ITrackEmitter;
import net.minecraft.entity.item.EntityMinecart;
import traincraft.api.DieselTrain;
import traincraft.common.library.TrackIDs;

public class BlockDetectorDieselLocomotiveTrack extends BlockDetectorTrack implements ITrackEmitter {

	@Override
	public TrackIDs getTrackType() {
		return TrackIDs.DETECTOR_DIESEL_LOCOMOTIVES;
	}

	@Override
	public void onMinecartPass(EntityMinecart cart) {
		if (cart instanceof DieselTrain) {
			setTrackPowering();
		}
	}
}
