/*******************************************************************************
 * Copyright (c) 2013 Mrbrutal. All rights reserved.
 * 
 * @name Traincraft
 * @author Mrbrutal
 ******************************************************************************/

package traincraft.common.core.handlers;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import traincraft.api.DieselTrain;
import traincraft.api.ElectricTrain;
import traincraft.api.Freight;
import traincraft.api.SteamTrain;
import traincraft.api.Tender;
import traincraft.common.entity.rollingStock.EntityBoxCartUS;
import traincraft.common.entity.rollingStock.EntityFlatCarLogs_DB;
import traincraft.common.entity.rollingStock.EntityFlatCarRails_DB;
import traincraft.common.entity.rollingStock.EntityFlatCartWoodUS;
import traincraft.common.entity.rollingStock.EntityFreightCart;
import traincraft.common.entity.rollingStock.EntityFreightCart2;
import traincraft.common.entity.rollingStock.EntityFreightCartSmall;
import traincraft.common.entity.rollingStock.EntityFreightCartUS;
import traincraft.common.entity.rollingStock.EntityFreightCenterbeam_Empty;
import traincraft.common.entity.rollingStock.EntityFreightCenterbeam_Wood_1;
import traincraft.common.entity.rollingStock.EntityFreightCenterbeam_Wood_2;
import traincraft.common.entity.rollingStock.EntityFreightClosed;
import traincraft.common.entity.rollingStock.EntityFreightGondola_DB;
import traincraft.common.entity.rollingStock.EntityFreightGrain;
import traincraft.common.entity.rollingStock.EntityFreightHopperUS;
import traincraft.common.entity.rollingStock.EntityFreightMinetrain;
import traincraft.common.entity.rollingStock.EntityFreightOpen2;
import traincraft.common.entity.rollingStock.EntityFreightOpenWagon;
import traincraft.common.entity.rollingStock.EntityFreightTrailer;
import traincraft.common.entity.rollingStock.EntityFreightWagenDB;
import traincraft.common.entity.rollingStock.EntityFreightWellcar;
import traincraft.common.entity.rollingStock.EntityFreightWood;
import traincraft.common.entity.rollingStock.EntityFreightWood2;
import traincraft.common.items.ItemTCRail;

public class ItemHandler {

	public static boolean handleItems(Entity entity, ItemStack itemstack) {
		if(itemstack!= null) {
    		if(entity instanceof Freight) {
    			return handleFreight(entity, itemstack);
    		}
    		else if(entity instanceof DieselTrain) {
    			return handleDiesel(entity, itemstack);
    		}
    		else if(entity instanceof ElectricTrain) {
    			return handleElectric(entity, itemstack);
    		}
            else if(entity instanceof SteamTrain) {
            	return handleSteam(entity, itemstack);
            }
            else if(entity instanceof Tender) {
            	return handleTender(entity, itemstack);
            }
            else {
            	return handleOther(entity, itemstack);
            }
		}
		return false;
	}

	public static boolean handleOther(Entity entity, ItemStack itemstack) {
		return false;
	}

	public static boolean handleTender(Entity entity, ItemStack itemstack) {
		return false;
	}

	public static boolean handleSteam(Entity entity, ItemStack itemstack) {
		return false;
	}

	public static boolean handleElectric(Entity entity, ItemStack itemstack) {
		return false;
	}

	public static boolean handleDiesel(Entity entity, ItemStack itemstack) {
		return false;
	}

	public static boolean handleFreight(Entity entity, ItemStack itemstack) {
		//System.out.println(entity.getEntityName());
		
		Block block = null;
		if (itemstack.itemID < Block.blocksList.length/* && itemstack.itemID < 256 && itemstack.itemID > 421*/) {
			block = Block.blocksList[itemstack.itemID];
		}
		
		if(entity instanceof EntityBoxCartUS) {
			return true;
		}
		else if(entity instanceof EntityFlatCarLogs_DB) {
			if(block != null) {
				return isDict("logWood", itemstack);
			}
			return false;
		}
		else if(entity instanceof EntityFlatCarRails_DB) {
			if(block != null) {
        		if(Block.blocksList[itemstack.itemID] instanceof BlockRailBase) {
        			return true;
        		}
        	}
			if(itemstack!=null && itemstack.getItem() instanceof ItemTCRail)return true;
			
        	return false;
		}
		else if(entity instanceof EntityFlatCartWoodUS) {
			if(block != null) {
				return isDict("plankWood", itemstack);
			}
			return false;	
		}
		else if(entity instanceof EntityFreightCart) {
			return true;
		}
        else if(entity instanceof EntityFreightCart2) {
        	return true;		
        }
        else if(entity instanceof EntityFreightCartSmall) {
        	return true;
        }
        else if(entity instanceof EntityFreightCartUS) {
        	if(block != null && !woodStuff(itemstack)) {
        		if(block instanceof Block) {
        			return true;
        		}
        	}
        	return false;
        }
        else if(entity instanceof EntityFreightCenterbeam_Empty) {
        	return true;
        }
        else if(entity instanceof EntityFreightCenterbeam_Wood_1) {
        	if(block != null) {
        		return woodStuff(itemstack);
			}
			return false;
        }
        else if(entity instanceof EntityFreightCenterbeam_Wood_2) {
        	if(block != null) {
				return woodStuff(itemstack);
			}
			return false;
        }
        else if(entity instanceof EntityFreightClosed) {
        	if(block != null && !woodStuff(itemstack)) {
        		if(block instanceof Block) {
        			return true;
        		}
        	}
        	return false;
        }
        else if(entity instanceof EntityFreightGondola_DB) {
        	if(block != null && !woodStuff(itemstack)) {
        		if(block instanceof Block) {
        			return true;
        		}
        	}
        	return false;
		}
        else if(entity instanceof EntityFreightGrain) {
        	if(itemstack.getItem().itemID == Item.wheat.itemID || itemstack.getItem().itemID == Item.seeds.itemID || itemstack.getItem().itemID == Item.melonSeeds.itemID || itemstack.getItem().itemID == Item.pumpkinSeeds.itemID) {
        		return true;
        	}
        	if(cropStuff(itemstack)) {
        		return true;
        	}
        	return false;		
        }
        else if(entity instanceof EntityFreightHopperUS) {
        	if(block != null) {
        		if(block instanceof Block && !woodStuff(itemstack)) {
        			return true;
        		}
        		else {
        			return false;
        		}
        	}
        	else {
        		return false;
        	}
        }
        else if(entity instanceof EntityFreightMinetrain) {
        	if(block != null) {
        		if(block.isOpaqueCube()) {
        			return true;
        		}
        		return false;
        	}
        	else {
        		if(itemstack.getItem().isDamaged(itemstack)) {
        			return false;
        		}
        	}
        }
        else if(entity instanceof EntityFreightOpen2) {
        	if(block != null && !woodStuff(itemstack)) {
        		if(block instanceof Block) {
        			return true;
        		}
        	}
        	return false;
        }
        else if(entity instanceof EntityFreightTrailer) {
        	return true;
        }
        else if(entity instanceof EntityFreightWagenDB) {
        	return true;
        }
        else if(entity instanceof EntityFreightWellcar) {
        	return true;
        }
        else if(entity instanceof EntityFreightWood) {
        	if(block != null) {
        		return isDict("logWood", itemstack);
			}
			return false;
		}
        else if(entity instanceof EntityFreightWood2) {
        	if(block != null) {
				return isDict("logWood", itemstack);
			}
			return false;		
        }
        else if(entity instanceof EntityFreightOpenWagon) {
        	if(block != null) {
        		if(block instanceof Block && !woodStuff(itemstack)) {
        			System.out.println(block.getLocalizedName());
        			return true;
        		}
        	}
        	return false;
        }
		return false;
	}
	
	private static boolean cropStuff(ItemStack itemstack) {
		String[] names = new String[] {"cropCorn", "cropHops", "cropRice", "seedCorn"};
		for (int i = 0; i < names.length; i++) {
			if(isDict(names[i], itemstack)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean woodStuff(ItemStack itemstack) {
		String[] names = new String[] {"logWood", "plankWood", "slabWood", "stickWood", "stairWood"};
		for (int i = 0; i < names.length; i++) {
			if(isDict(names[i], itemstack)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isDict(String name, ItemStack itemstack) {
		ArrayList<ItemStack> items = OreDictionary.getOres(name);
		for (int i = 0; i < items.size(); i++) {
			if(itemstack.getItem().itemID == items.get(i).itemID) {
				return true;
			}
		}
		return false;
	}
}
