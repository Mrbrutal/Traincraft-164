/**
 * A track that detects all instance of IPassenger
 * 
 * @author Spitfire4466
 */
package traincraft.common.blocks.tracks;

import mods.railcraft.api.tracks.ITrackEmitter;
import net.minecraft.entity.item.EntityMinecart;
import traincraft.api.IPassenger;
import traincraft.common.library.TrackIDs;

public class BlockDetectorPassengerTrack extends BlockDetectorTrack implements ITrackEmitter {

	@Override
	public TrackIDs getTrackType() {
		return TrackIDs.DETECTOR_PASSENGER;
	}
	@Override
	public void onMinecartPass(EntityMinecart cart) {
		if (cart instanceof IPassenger) {
			setTrackPowering();
		}
	}
}
