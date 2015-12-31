/**
 * A track that detects all instance of Locomotive
 * 
 * @author Spitfire4466
 */
package traincraft.common.blocks.tracks;

import mods.railcraft.api.tracks.ITrackEmitter;
import net.minecraft.entity.item.EntityMinecart;
import traincraft.api.Locomotive;
import traincraft.common.library.TrackIDs;

public class BlockDetectorHornAllLocomotiveTrack extends BlockDetectorTrack implements ITrackEmitter {

	@Override
	public TrackIDs getTrackType() {
		return TrackIDs.HORN_ALL_LOCOMOTIVES;
	}
	@Override
	public void onMinecartPass(EntityMinecart cart) {
		if (cart instanceof Locomotive) {
			((Locomotive)cart).soundHorn();
		}
	}

}
