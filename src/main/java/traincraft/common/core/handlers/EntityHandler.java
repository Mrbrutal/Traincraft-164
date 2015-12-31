/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 * 
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package traincraft.common.core.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import traincraft.common.Traincraft;
import traincraft.api.EntityBogie;
import traincraft.api.EntityBogieUtility;
import traincraft.common.core.EntityIds;
import traincraft.common.entity.zeppelin.EntityZeppelinOneBalloon;
import traincraft.common.entity.zeppelin.EntityZeppelinTwoBalloons;
import traincraft.common.library.EnumTrains;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityHandler {
	public static void init() {	
		EntityRegistry.registerModEntity(EntityZeppelinTwoBalloons.class, "zeppelin", EntityIds.ZEPPELIN, Traincraft.instance, 512, 1, true);//zepplin
		EntityRegistry.registerModEntity(EntityBogie.class, "Entity Front Bogie", EntityIds.LOCOMOTIVE_BOGIE, Traincraft.instance, 512, 1, true);//front bogie
		EntityRegistry.registerModEntity(EntityBogieUtility.class, "Entity Utility Bogie", EntityIds.UTILITY_BOGIE, Traincraft.instance, 512, 1, true);//front bogie
		EntityRegistry.registerModEntity(EntityZeppelinOneBalloon.class, "zeppelin big", EntityIds.ZEPPELIN_BIG, Traincraft.instance, 512, 1, true);//zepplin big
		
		for(EnumTrains trains : EnumTrains.values()){
			EntityRegistry.registerModEntity(trains.getEntityClass(), trains.getInternalName(), trains.getEntityID(), Traincraft.instance, 512, 1, true);
		}
	}

	public static Entity getEntityServer(World world, int entityId) {
		if ((world != null) && (world instanceof WorldServer)) {
			return ((WorldServer) world).getEntityByID(entityId);
		}
		return null;
	}
}