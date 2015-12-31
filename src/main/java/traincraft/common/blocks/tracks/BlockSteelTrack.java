/**
 * @author Spitfire4466
 */
package traincraft.common.blocks.tracks;

import net.minecraft.util.Icon;
import traincraft.common.library.TrackIDs;

public class BlockSteelTrack extends TrackBaseTraincraft {
	public BlockSteelTrack() {
		this.speedController = SpeedControllerSteel.getInstance();
	}

	@Override
	public TrackIDs getTrackType() {
		return TrackIDs.STEEL_TRACK;
	}
	@Override
	public Icon getIcon() {
		int meta = this.tileEntity.getBlockMetadata();
		if (meta >= 6) {
			return getIcon(1);
		}
		return getIcon(0);
	}
	@Override
	public boolean isFlexibleRail() {
		return true;
	}
}