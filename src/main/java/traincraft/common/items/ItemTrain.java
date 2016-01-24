package traincraft.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import traincraft.common.Traincraft;
import traincraft.common.library.Info;
import traincraft.common.library.ItemIDs;

public class ItemTrain extends Item {

	public ItemTrain(int i) {
		super(i);
		maxStackSize = 64;
		setCreativeTab(Traincraft.tcTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Info.modID.toLowerCase() + ":parts/" + ItemIDs.getIcon(this.itemID));
	}
}