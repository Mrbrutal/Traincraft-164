package traincraft.common.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import traincraft.common.Traincraft;
import traincraft.api.AbstractTrains;
import traincraft.api.EntityRollingStock;
import traincraft.api.Freight;
import traincraft.api.LiquidTank;
import traincraft.api.Tender;
import traincraft.common.containers.ContainerDistil;
import traincraft.common.containers.ContainerGeneratorDiesel;
import traincraft.common.containers.ContainerOpenHearthFurnace;
import traincraft.common.containers.ContainerTier;
import traincraft.common.containers.ContainerTrainWorkbench;
import traincraft.common.containers.ContainerWorkbenchCart;
import traincraft.common.core.handlers.ServerTickHandler;
import traincraft.common.core.util.MP3Player;
import traincraft.common.entity.digger.EntityRotativeDigger;
import traincraft.common.entity.rollingStock.EntityJukeBoxCart;
import traincraft.common.entity.rollingStock.EntityTracksBuilder;
import traincraft.common.entity.zeppelin.AbstractZeppelin;
import traincraft.common.inventory.InventoryBuilder;
import traincraft.common.inventory.InventoryForney;
import traincraft.common.inventory.InventoryFreight;
import traincraft.common.inventory.InventoryJukeBoxCart;
import traincraft.common.inventory.InventoryLiquid;
import traincraft.common.inventory.InventoryLoco;
import traincraft.common.inventory.InventoryRotativeDigger;
import traincraft.common.inventory.InventoryTender;
import traincraft.common.inventory.InventoryWorkCart;
import traincraft.common.inventory.InventoryZepp;
import traincraft.common.library.GuiIDs;
import traincraft.common.tile.TileBook;
import traincraft.common.tile.TileBridgePillar;
import traincraft.common.tile.TileCrafterTierI;
import traincraft.common.tile.TileCrafterTierII;
import traincraft.common.tile.TileCrafterTierIII;
import traincraft.common.tile.TileEntityDistil;
import traincraft.common.tile.TileEntityOpenHearthFurnace;
import traincraft.common.tile.TileGeneratorDiesel;
import traincraft.common.tile.TileLantern;
import traincraft.common.tile.TileSignal;
import traincraft.common.tile.TileStopper;
import traincraft.common.tile.TileTCRail;
import traincraft.common.tile.TileTCRailGag;
import traincraft.common.tile.TileTrainWbench;
import traincraft.common.tile.TileWaterWheel;
import traincraft.common.tile.TileWindMill;

import com.google.common.collect.Lists;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements IGuiHandler {
	public static List<MP3Player> playerList = new ArrayList();
	
	public void registerKeyBindingHandler() {}

	public void setKeyBinding(String name, int value) {}

	public void registerRenderInformation() {}

	public void registerTileEntities() {
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
		GameRegistry.registerTileEntity(TileCrafterTierI.class, "TileCrafterTierI");
		GameRegistry.registerTileEntity(TileCrafterTierII.class, "TileCrafterTierII");
		GameRegistry.registerTileEntity(TileCrafterTierIII.class, "TileCrafterTierIII");
		GameRegistry.registerTileEntity(TileTrainWbench.class, "TileTrainWbench");
		GameRegistry.registerTileEntity(TileEntityDistil.class, "Tile Distil");
		GameRegistry.registerTileEntity(TileEntityOpenHearthFurnace.class, "Tile OpenHearthFurnace");
		GameRegistry.registerTileEntity(TileStopper.class, "TileStopper");
		GameRegistry.registerTileEntity(TileSignal.class, "TileSignal");
		GameRegistry.registerTileEntity(TileLantern.class, "tileLantern");
		GameRegistry.registerTileEntity(TileWaterWheel.class, "tileWaterWheel");
		GameRegistry.registerTileEntity(TileWindMill.class, "tileWindMill");
		GameRegistry.registerTileEntity(TileGeneratorDiesel.class, "tileGeneratorDiesel");
		GameRegistry.registerTileEntity(TileBook.class, "tileBook");
		GameRegistry.registerTileEntity(TileTCRailGag.class, "tileTCRailGag");
		GameRegistry.registerTileEntity(TileTCRail.class, "tileTCRail");
		GameRegistry.registerTileEntity(TileBridgePillar.class, "tileTCBridgePillar");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		EntityPlayer riddenByEntity = null;
		Entity entity = null;
		entity = player.ridingEntity;

		if (player.ridingEntity != null) {
			riddenByEntity = (EntityPlayer) entity.riddenByEntity;
		}

		Entity entity1 = null;
		if (y == -1) {
			entity1 = getEntity(world, x);
		}

		switch (ID) {
		case (GuiIDs.CRAFTER_TIER_I):
			return te != null && te instanceof TileCrafterTierI ? new ContainerTier(player.inventory, (TileCrafterTierI) te) : null;
		case (GuiIDs.CRAFTER_TIER_II):
			return te != null && te instanceof TileCrafterTierII ? new ContainerTier(player.inventory, (TileCrafterTierII) te) : null;
		case (GuiIDs.CRAFTER_TIER_III):
			return te != null && te instanceof TileCrafterTierIII ? new ContainerTier(player.inventory, (TileCrafterTierIII) te) : null;
		case (GuiIDs.DISTIL):
			return te != null && te instanceof TileEntityDistil ? new ContainerDistil(player.inventory, (TileEntityDistil) te) : null;
		case (GuiIDs.GENERATOR_DIESEL):
			return te != null && te instanceof TileGeneratorDiesel ? new ContainerGeneratorDiesel(player.inventory, (TileGeneratorDiesel) te) : null;
		case (GuiIDs.OPEN_HEARTH_FURNACE):
			return te != null && te instanceof TileEntityOpenHearthFurnace ? new ContainerOpenHearthFurnace(player.inventory, (TileEntityOpenHearthFurnace) te) : null;
		case (GuiIDs.TRAIN_WORKBENCH):
			return te != null && te instanceof TileTrainWbench ? new ContainerTrainWorkbench(player.inventory, player.worldObj, (TileTrainWbench) te) : null;
		case (GuiIDs.LOCO):
			return riddenByEntity != null && entity != null ? new InventoryLoco(riddenByEntity.inventory, (EntityRollingStock) entity) : null;
		case (GuiIDs.FORNEY):
			return riddenByEntity != null && entity != null ? new InventoryForney(player.inventory, (EntityRollingStock) entity) : null;
		case (GuiIDs.CRAFTING_CART):
			return new ContainerWorkbenchCart(player.inventory, player.worldObj);
		case (GuiIDs.FURNACE_CART):
			return riddenByEntity != null && entity != null ? new InventoryWorkCart(player.inventory, entity) : null;
		case (GuiIDs.ZEPPELIN):
			return riddenByEntity != null && entity != null ? new InventoryZepp(player.inventory, (AbstractZeppelin) entity) : null;
		case (GuiIDs.DIGGER):
			return riddenByEntity != null && entity != null ? new InventoryRotativeDigger(player.inventory, (EntityRotativeDigger) entity) : null;

			/* Stationary entities while player is not riding. */
		case (GuiIDs.FREIGHT):
			//System.out.println("Freight: " + ID + " | " + entity1.getEntityName() + " | " + x + ":" + y + ":" + z);
			return entity1 != null && entity1 instanceof Freight ? new InventoryFreight(player.inventory, (Freight) entity1) : null;
		case (GuiIDs.JUKEBOX):
			return entity1 != null && entity1 instanceof EntityJukeBoxCart ? new InventoryJukeBoxCart(player.inventory, (EntityJukeBoxCart) entity1) : null;
		case (GuiIDs.TENDER):
			return entity1 != null && entity1 instanceof Tender ? new InventoryTender(player.inventory, (Tender) entity1) : null;
		case (GuiIDs.BUILDER):
			return entity1 != null && entity1 instanceof EntityTracksBuilder ? new InventoryBuilder(player.inventory, (EntityTracksBuilder) entity1) : null;
		case (GuiIDs.LIQUID):
			return entity1 != null && entity1 instanceof LiquidTank ? new InventoryLiquid(player.inventory, (LiquidTank) entity1) : null;
		default:
			return null;
		}
	}
	public void registerChunkHandler(Traincraft instance){
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new locoChunkloadCallback());
	}
	
	public class locoChunkloadCallback implements ForgeChunkManager.OrderedLoadingCallback {

		@Override
		public void ticketsLoaded(List<Ticket> tickets, World world) {
			for (Ticket ticket : tickets) {
				int locoID = ticket.getModData().getInteger("locoID");
				Entity entity = world.getEntityByID(locoID);
				if(entity!=null && entity instanceof AbstractTrains){
					((AbstractTrains)entity).forceChunkLoading(ticket);
				}
			}
		}

		@Override
		public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount) {
			List<Ticket> validTickets = Lists.newArrayList();
			for (Ticket ticket : tickets) {
				int locoID = ticket.getModData().getInteger("locoID");
				Entity entity = world.getEntityByID(locoID);
				if(entity!=null && entity instanceof AbstractTrains){
					validTickets.add(ticket);
				}
			}
			return validTickets;
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public static Entity getEntity(World world, int entityId) {
		if ((world != null) && (world instanceof WorldServer)) {
			return ((WorldServer) world).getEntityByID(entityId);
		}
		return null;
	}

	public int addArmor(String armor) {
		return 0;
	}

	public Minecraft getClientInstance() {
		return FMLClientHandler.instance().getClient();
	}

	public void getKeysFromProperties() {}

	public GuiScreen getCurrentScreen() {
		return null;
	}

	public void registerTextureFX() {}

	public void registerSounds() {}
	
	public void registerBookHandler() {}
    
	public void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().replace("tile.", ""));
	}
	
	public void registerBlock(Block block, Class<? extends ItemBlock> item) {
		GameRegistry.registerBlock(block, item, block.getUnlocalizedName().replace("tile.", ""));
    }

	public Minecraft getMinecraft() {
		return null;
	}

	public void registerVillagerSkin(int villagerId, String textureName) {}
	
	public void getCape() {}
	
	public static void killAllStreams() {
		for (MP3Player p : playerList) {
			p.stop();
		}
	}
	
	public static boolean checkJukeboxEntity(World world, int id) {
		if (world.getEntityByID(id)!=null) {
			return true;
		}
		return false;
	}
	
	public void isHoliday() {}

	public void doNEICheck(int id) {}
	
	public EntityPlayer getPlayer() {
		return null;
	}
}