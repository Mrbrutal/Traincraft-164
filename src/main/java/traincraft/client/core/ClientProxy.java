package traincraft.client.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import traincraft.client.core.handlers.ClientTickHandler;
import traincraft.client.core.handlers.KeyBindingHandler;
import traincraft.client.core.handlers.RecipeBookHandler;
import traincraft.client.core.helpers.HolidayHelper;
import traincraft.client.core.helpers.KeyBindingHelper;
import traincraft.client.gui.GuiBuilder;
import traincraft.client.gui.GuiCrafterTier;
import traincraft.client.gui.GuiCraftingCart;
import traincraft.client.gui.GuiDistil;
import traincraft.client.gui.GuiForney;
import traincraft.client.gui.GuiFreight;
import traincraft.client.gui.GuiFurnaceCart;
import traincraft.client.gui.GuiGeneratorDiesel;
import traincraft.client.gui.GuiJukebox;
import traincraft.client.gui.GuiLantern;
import traincraft.client.gui.GuiLiquid;
import traincraft.client.gui.GuiLoco2;
import traincraft.client.gui.GuiOpenHearthFurnace;
import traincraft.client.gui.GuiRecipeBook;
import traincraft.client.gui.GuiTender;
import traincraft.client.gui.GuiTrainCraftingBlock;
import traincraft.client.gui.GuiZepp;
import traincraft.client.render.ItemRenderBridgePillar;
import traincraft.client.render.ItemRenderGeneratorDiesel;
import traincraft.client.render.ItemRenderLantern;
import traincraft.client.render.ItemRenderSignal;
import traincraft.client.render.ItemRenderStopper;
import traincraft.client.render.ItemRenderWaterWheel;
import traincraft.client.render.ItemRenderWindMill;
import traincraft.client.render.RenderBogie;
import traincraft.client.render.RenderBridgePillar;
import traincraft.client.render.RenderGeneratorDiesel;
import traincraft.client.render.RenderLantern;
import traincraft.client.render.RenderRollingStock;
import traincraft.client.render.RenderRotativeDigger;
import traincraft.client.render.RenderRotativeWheel;
import traincraft.client.render.RenderSignal;
import traincraft.client.render.RenderStopper;
import traincraft.client.render.RenderTCRail;
import traincraft.client.render.RenderWaterWheel;
import traincraft.client.render.RenderWindMill;
import traincraft.client.render.RenderZeppelins;
import traincraft.common.Traincraft;
import traincraft.api.EntityBogie;
import traincraft.api.EntityRollingStock;
import traincraft.common.core.CommonProxy;
import traincraft.common.core.Traincraft_EventSounds;
import traincraft.common.core.handlers.ConfigHandler;
import traincraft.common.entity.digger.EntityRotativeDigger;
import traincraft.common.entity.digger.EntityRotativeWheel;
import traincraft.common.entity.rollingStock.EntityJukeBoxCart;
import traincraft.common.entity.zeppelin.EntityZeppelinOneBalloon;
import traincraft.common.entity.zeppelin.EntityZeppelinTwoBalloons;
import traincraft.common.library.BlockIDs;
import traincraft.common.library.GuiIDs;
import traincraft.common.library.Info;
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
import traincraft.common.tile.TileTrainWbench;
import traincraft.common.tile.TileWaterWheel;
import traincraft.common.tile.TileWindMill;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

	@Override
	public void isHoliday() {
		HolidayHelper helper = new HolidayHelper();
		helper.setDaemon(true);
		helper.start();
	}
	
	@Override
	public void registerKeyBindingHandler() {
		KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler());
	}

	@Override
	public void setKeyBinding(String name, int value) {
		KeyBindingHelper.addKeyBinding(name, value);
		KeyBindingHelper.addIsRepeating(false);
	}

	@Override
	public void registerRenderInformation() {
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);

		RenderingRegistry.registerEntityRenderingHandler(EntityRollingStock.class, new RenderRollingStock());
		RenderingRegistry.registerEntityRenderingHandler(EntityZeppelinTwoBalloons.class, new RenderZeppelins());
		RenderingRegistry.registerEntityRenderingHandler(EntityZeppelinOneBalloon.class, new RenderZeppelins());
		RenderingRegistry.registerEntityRenderingHandler(EntityRotativeDigger.class, new RenderRotativeDigger());
		RenderingRegistry.registerEntityRenderingHandler(EntityRotativeWheel.class, new RenderRotativeWheel());
		//bogies
		RenderingRegistry.registerEntityRenderingHandler(EntityBogie.class, new RenderBogie());

		ClientRegistry.bindTileEntitySpecialRenderer(TileStopper.class, new RenderStopper());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.stopper.blockID, new ItemRenderStopper());
		
		//ClientRegistry.bindTileEntitySpecialRenderer(TileBook.class, new RenderTCBook());
		//MinecraftForgeClient.registerItemRenderer(BlockIDs.book.blockID, new ItemRenderBook());

		ClientRegistry.bindTileEntitySpecialRenderer(TileSignal.class, new RenderSignal());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.signal.blockID, new ItemRenderSignal());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileLantern.class, new RenderLantern());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.lantern.blockID, new ItemRenderLantern());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileWaterWheel.class, new RenderWaterWheel());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.waterWheel.blockID, new ItemRenderWaterWheel());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileWindMill.class, new RenderWindMill());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.windMill.blockID, new ItemRenderWindMill());

		ClientRegistry.bindTileEntitySpecialRenderer(TileGeneratorDiesel.class, new RenderGeneratorDiesel());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.generatorDiesel.blockID, new ItemRenderGeneratorDiesel());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileTCRail.class, new RenderTCRail());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileBridgePillar.class, new RenderBridgePillar());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.bridgePillar.blockID, new ItemRenderBridgePillar());
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		EntityPlayer riddenByEntity = null;
		Entity entity = player.ridingEntity;
		//entity = ;
		if (player.ridingEntity != null) {
			riddenByEntity = (EntityPlayer) entity.riddenByEntity;
		}

		Entity entity1 = null;
		if (y == -1) {
			for (Object ent : world.loadedEntityList) {
				if (((Entity) ent).entityId == x)
					entity1 = (Entity) ent;
			}
		}
		switch (ID) {
		case (GuiIDs.CRAFTER_TIER_I):
			return te != null && te instanceof TileCrafterTierI ? new GuiCrafterTier(player.inventory, (TileCrafterTierI) te) : null;
		case (GuiIDs.CRAFTER_TIER_II):
			return te != null && te instanceof TileCrafterTierII ? new GuiCrafterTier(player.inventory, (TileCrafterTierII) te) : null;
		case (GuiIDs.CRAFTER_TIER_III):
			return te != null && te instanceof TileCrafterTierIII ? new GuiCrafterTier(player.inventory, (TileCrafterTierIII) te) : null;
		case (GuiIDs.DISTIL):
			return te != null && te instanceof TileEntityDistil ? new GuiDistil(player.inventory, (TileEntityDistil) te) : null;
		case (GuiIDs.GENERATOR_DIESEL):
			return te != null && te instanceof TileGeneratorDiesel ? new GuiGeneratorDiesel(player.inventory, (TileGeneratorDiesel) te) : null;
		case (GuiIDs.OPEN_HEARTH_FURNACE):
			return te != null && te instanceof TileEntityOpenHearthFurnace ? new GuiOpenHearthFurnace(player.inventory, (TileEntityOpenHearthFurnace) te) : null;
		case GuiIDs.TRAIN_WORKBENCH:
			return te != null && te instanceof TileTrainWbench ? new GuiTrainCraftingBlock(player.inventory, player.worldObj, (TileTrainWbench) te) : null;
		case (GuiIDs.LOCO):
			return riddenByEntity != null && entity != null ? new GuiLoco2(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.FORNEY):
			return riddenByEntity != null && entity != null ? new GuiForney(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.CRAFTING_CART):
			return riddenByEntity != null && entity != null ? new GuiCraftingCart(riddenByEntity.inventory, world) : null;
		case (GuiIDs.FURNACE_CART):
			return riddenByEntity != null && entity != null ? new GuiFurnaceCart(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.ZEPPELIN):
			return riddenByEntity != null && entity != null ? new GuiZepp(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.DIGGER):
			return riddenByEntity != null && entity != null ? new GuiBuilder(player, riddenByEntity.inventory, entity) : null;

			//Stationary entities while player is not riding. 
		case (GuiIDs.FREIGHT):
			return entity1 != null ? new GuiFreight(player,player.inventory, entity1) : null;
		case (GuiIDs.TENDER):
			return entity1 != null ? new GuiTender(player,player.inventory, entity1) : null;
		case (GuiIDs.BUILDER):
			return entity1 != null ? new GuiBuilder(player,player.inventory, entity1) : null;
		case (GuiIDs.LIQUID):
			return entity1 != null ? new GuiLiquid(player,player.inventory, entity1) : null;
		case (GuiIDs.RECIPE_BOOK):
			return new GuiRecipeBook(player, player.getCurrentEquippedItem());
		/*case (GuiIDs.RECIPE_BOOK2):
			return te != null && te instanceof TileBook ? new GuiRecipeBook2(player, player.getCurrentEquippedItem()) : new GuiRecipeBook2(player, player.getCurrentEquippedItem());*/
		case (GuiIDs.LANTERN):
			return new GuiLantern(player, (TileLantern)te);
		case (GuiIDs.JUKEBOX):
			return entity1 != null ? new GuiJukebox(player,(EntityJukeBoxCart)entity1) : null;
		default:
			return null;
		}
	}
	
	@Override
	public void getKeysFromProperties() {
		File f = new File(Minecraft.getMinecraft().mcDataDir + "/options.txt");
		if (f.exists() && f.isFile()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(f));
				String line = "";

				while ((line = br.readLine()) != null) {
					try {
						String[] split = line.split(":");
						if (split[0].contains("forward")) {
							ConfigHandler.Key_Acc = Integer.parseInt(split[1]);
						}
						if (split[0].contains("back")) {
							ConfigHandler.Key_Dec = Integer.parseInt(split[1]);
						}
						if (split[0].contains("left")) {
							ConfigHandler.Key_Left = Integer.parseInt(split[1]);
						}
						if (split[0].contains("right")) {
							ConfigHandler.Key_Right = Integer.parseInt(split[1]);
						}
					} catch (Exception e) {
						Traincraft.tcLog.fine("Skiping option in options.txt file.");
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				Traincraft.tcLog.info("Options.txt file could not be read. Defaulting keys.");
			}
		}
		else {
			Traincraft.tcLog.info("Options.txt file could not be found. Defaulting keys.");
		}
	}

	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}

	@Override
	public GuiScreen getCurrentScreen() {
		return Minecraft.getMinecraft().currentScreen;
	}
	@Override
	public void registerVillagerSkin(int villagerId, String textureName) {
		VillagerRegistry.instance().registerVillagerSkin(villagerId, new ResourceLocation(Info.resourceLocation,Info.villagerPrefix + textureName));
	}

	@Override
	public void registerSounds() {
		MinecraftForge.EVENT_BUS.register(new Traincraft_EventSounds());
	}
	
	@Override
	public void registerBookHandler() {
		RecipeBookHandler recipeBookHandler = new RecipeBookHandler();
	}

	@Override
	public Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}
	
	@Override
	public void getCape() {
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
	}
	
	@Override
	public EntityPlayer getPlayer() {
		return getMinecraft().thePlayer;
	}
	
	@Override
	public void doNEICheck(int id) {
		if (Minecraft.getMinecraft().thePlayer != null ) {
            Iterator modsIT = Loader.instance().getModList().iterator();
            ModContainer modc;
            while (modsIT.hasNext()) {
                modc = (ModContainer) modsIT.next();
                if ("Not Enough Items".equals(modc.getName().trim())) {
                    codechicken.nei.api.API.hideItem(id);
                    return;
                }
            }
        }
	}
}