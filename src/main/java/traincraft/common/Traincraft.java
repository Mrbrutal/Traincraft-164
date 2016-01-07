package traincraft.common;

import java.io.File;
import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import traincraft.api.LiquidManager;
import traincraft.common.blocks.TCBlocks;
import traincraft.common.core.CommonProxy;
import traincraft.common.core.CreativeTabTraincraft;
import traincraft.common.core.TraincraftCore;
import traincraft.common.core.handlers.AchievementHandler;
import traincraft.common.core.handlers.ChunkHandler;
import traincraft.common.core.handlers.ConfigHandler;
import traincraft.common.core.handlers.CraftingHandler;
import traincraft.common.core.handlers.EntityHandler;
import traincraft.common.core.handlers.FuelHandler;
import traincraft.common.core.handlers.KeyServerHandler;
import traincraft.common.core.handlers.OreHandler;
import traincraft.common.core.handlers.PacketHandler;
import traincraft.common.core.handlers.PlayerTracker;
import traincraft.common.core.handlers.RecipeHandler;
import traincraft.common.core.handlers.VillagerTraincraftHandler;
import traincraft.common.generation.ComponentVillageTrainstation;
import traincraft.common.generation.WorldGenWorld;
import traincraft.common.items.TCItems;
import traincraft.common.library.Info;
import traincraft.common.mysql.mysqlLogInterface;
import traincraft.common.mysql.mysqlLogger;
import traincraft.common.recipes.AssemblyTableRecipes;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

@Mod(modid = Info.modID, name = Info.modName, version = Info.modVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = true, versionBounds = "[" + Info.modVersion + "]", channels = { Info.channel }, packetHandler = PacketHandler.class, connectionHandler = KeyServerHandler.class)
public class Traincraft {

	/* TrainCraft instance */
	@Instance(Info.modID)
	public static Traincraft instance;

	/* TrainCraft proxy files */
	@SidedProxy(clientSide = "traincraft.client.core.ClientProxy", serverSide = "traincraft.common.core.CommonProxy")
	public static CommonProxy proxy;

	/* TrainCraft Logger */
	public static Logger tcLog = Logger.getLogger(Info.modID);

	/* Creative tab for Traincraft */
	public static CreativeTabs tcTab;

	public EnumArmorMaterial armor = EnumHelper.addArmorMaterial("Armor", 5, new int[] { 1, 3, 2, 1 }, 25);
	public EnumArmorMaterial armorCloth = EnumHelper.addArmorMaterial("TCcloth", 5, new int[] {1, 3, 2, 1}, 25);
	public EnumArmorMaterial armorCompositeSuit = EnumHelper.addArmorMaterial("TCsuit", 70, new int[] {5, 12, 8, 5}, 50);
	public static int trainArmor;
	public static int trainCloth;
	public static int trainCompositeSuit;

	private mysqlLogInterface logMysql = new mysqlLogger();
	public static boolean mysqlLoggerEnabled;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {	
		proxy.registerSounds();
		//ForgeChunkManager.setForcedChunkLoadingCallback(instance, ChunkHandler.getInstance());
		MinecraftForge.EVENT_BUS.register(ChunkHandler.getInstance());

		/* Log */
		tcLog.setParent(FMLLog.getLogger());
		tcLog.info("Starting Traincraft " + Info.modVersion + "!");

		/* Config handler */
		ConfigHandler.init(new File(event.getModConfigurationDirectory(), Info.modName + ".cfg"));
		proxy.getKeysFromProperties();

		/* Register the KeyBinding Handler */
		proxy.registerKeyBindingHandler();

		/* Tile Entities */
		proxy.registerTileEntities();

		/* Rendering registration */
		proxy.registerRenderInformation();
		trainArmor = proxy.addArmor("armor");
		trainCloth = proxy.addArmor("Paintable");
		trainCompositeSuit = proxy.addArmor("CompositeSuit");
		
		/* Tab for creative items/blocks */
		tcTab = new CreativeTabTraincraft(CreativeTabs.getNextID(), "Traincraft");

		/* Ore generation */
		GameRegistry.registerWorldGenerator(new WorldGenWorld());
		MapGenStructureIO.func_143031_a(ComponentVillageTrainstation.class, "Trainstation");

		/* Player tracker */
		GameRegistry.registerPlayerTracker(new PlayerTracker());

		/* Track registration */
		TraincraftCore.RegisterNewTracks();
		
		/*Fuel registration*/
		GameRegistry.registerFuelHandler(new FuelHandler());
		
		TCBlocks.init();
		TCItems.init();
		
		/* Register entities */
		EntityHandler.init();

		AchievementHandler.load();
		AchievementPage.registerAchievementPage(AchievementHandler.tmPage);
		
		/* Check holidays */
		proxy.isHoliday();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

		//proxy.getCape();
		
		/* GUI handler initiation */
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		GameRegistry.registerCraftingHandler(new CraftingHandler());

		/* Ore dictionary */
		OreHandler.registerOres();

		/* Recipes */
		RecipeHandler.initBlockRecipes();
		RecipeHandler.initItemRecipes();
		AssemblyTableRecipes.recipes();

		/* Register the liquids */
		LiquidManager.getInstance().registerLiquids();
		
		/* Liquid FX */
		proxy.registerTextureFX();

		/* Try to load mysql */
		if (ConfigHandler.MYSQL_ENABLE)
			mysqlLoggerEnabled = logMysql.enableLogger();
		
		/*Trainman Villager*/
		VillagerRegistry.instance().registerVillagerId(ConfigHandler.TRAINCRAFT_VILLAGER_ID);
		VillagerTraincraftHandler villageHandler = new VillagerTraincraftHandler();
		VillagerRegistry.instance().registerVillageCreationHandler(villageHandler);
	    proxy.registerVillagerSkin(ConfigHandler.TRAINCRAFT_VILLAGER_ID, "station_chief.png");
	    VillagerRegistry.instance().registerVillageTradeHandler(ConfigHandler.TRAINCRAFT_VILLAGER_ID, villageHandler);
	    
	    proxy.registerBookHandler();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		proxy.registerChunkHandler(instance);
	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {
		TraincraftCore.ModsLoaded();
		LiquidManager.getLiquidsFromDictionnary();
	}
	
	@EventHandler
	public void serverStop(FMLServerStoppedEvent event) {
		 proxy.killAllStreams();
	}
}