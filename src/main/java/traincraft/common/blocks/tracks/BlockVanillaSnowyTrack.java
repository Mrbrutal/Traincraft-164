/**
 * @author Spitfire4466
 */
package traincraft.common.blocks.tracks;

import net.minecraft.util.Icon;
import traincraft.common.library.TrackIDs;

public class BlockVanillaSnowyTrack extends TrackBaseTraincraft {

	@Override
	public TrackIDs getTrackType() {
		return TrackIDs.VANILLA_SNOWY_TRACK;
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