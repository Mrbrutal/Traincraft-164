package traincraft.client.core.handlers;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import traincraft.client.core.helpers.CapesHelper;
import traincraft.client.gui.HUDloco;
import traincraft.common.Traincraft;
import traincraft.api.Locomotive;
import traincraft.common.library.BlockIDs;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static boolean isHidden = false;
	private HUDloco locoHUD = new HUDloco(Traincraft.proxy.getClientInstance());

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if(type.equals(EnumSet.of(TickType.CLIENT))) {
    		if(mc.theWorld != null && mc != null && mc.theWorld.playerEntities != null) {
    			for (int i = 0; i < mc.theWorld.playerEntities.size(); i++) {
    				AbstractClientPlayer player = (AbstractClientPlayer)mc.theWorld.playerEntities.get(i);
    				CapesHelper user = CapesHelper.users.get(player.username);
    				if(user == null ) {
    					user = new CapesHelper(player.username);
    					CapesHelper.users.put(player.username, user);
    					user.setDaemon(true);
    					user.setName("Cape for " + player.username);
    					user.start();
    				}else if((user.isLoaded) && (user.MCCape)) {
    					player.downloadImageCape = user.getCurrentImage();
    					player.locationCape = user.getCurrentRL();
    				}
    			}
    		}
    		if(!isHidden) {
    			if(mc.theWorld != null && mc != null && mc.theWorld.playerEntities != null) {
    				Traincraft.proxy.doNEICheck(BlockIDs.tcRail.blockID);
        			Traincraft.proxy.doNEICheck(BlockIDs.tcRailGag.blockID);
    				isHidden = true;
    			}
    		}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(type.equals(EnumSet.of(TickType.RENDER))) {
			onRenderTick();
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "TC tick handler";
	}

	public void onRenderTick() {
		if (mc.thePlayer != null && mc.thePlayer.ridingEntity != null && mc.thePlayer.ridingEntity instanceof Locomotive && mc.isGuiEnabled() && mc.currentScreen == null) {
			locoHUD.renderSkillHUD((Locomotive) mc.thePlayer.ridingEntity);
		}
		//return;
	}
	
	public static ResourceLocation capeStaticRL(String username) {
		return new ResourceLocation("cloaks/" + StringUtils.stripControlCodes(username));
	}
 
	public static ResourceLocation capeAnimatedRL(String username, int frameNumber) {
		return new ResourceLocation("cloaks/" + StringUtils.stripControlCodes(username) + "/" + frameNumber);
	}
 
	public static ThreadDownloadImageData dlImage(ResourceLocation rl, String url) {
		return EntityPlayerSP.getDownloadImage(rl, url, (ResourceLocation)null, (IImageBuffer)null);
	}
}
