package src.train.common.blocks;

import java.util.Random;

import buildcraft.api.tools.IToolWrench;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import src.train.common.Traincraft;
import src.train.common.library.GuiIDs;
import src.train.common.library.Info;
import src.train.common.tile.TileLantern;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockLantern extends Block {
	private Icon texture;

	public BlockLantern(int id) {
		super(id, Material.rock);
		setCreativeTab(Traincraft.tcTab);
		this.setTickRandomly(true);
		float f = 0.3F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileLantern();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		int l = par1World.getBlockMetadata(par2, par3, par4);
		double d0 = (double) ((float) par2 + 0.5F);
		double d1 = (double) ((float) par3 + 0.7F);
		double d2 = (double) ((float) par4 + 0.5F);
		double d3 = 0.2199999988079071D;
		double d4 = 0.27000001072883606D;

		par1World.spawnParticle("smoke", d0, par3 + d3, d2, 0.0D, 0.0D, 0.0D);
		par1World.spawnParticle("flame", d0, par3 + d3, d2, 0.0D, 0.0D, 0.0D);

	}
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (player.isSneaking()) {
			return false;
		}
		if(player!=null && player.getCurrentEquippedItem()!=null && player.getCurrentEquippedItem().getItem() instanceof IToolWrench)
		if (te != null && te instanceof TileLantern) {
			player.openGui(Traincraft.instance, GuiIDs.LANTERN, world, i, j, k);
		}
		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		texture = iconRegister.registerIcon(Info.modID.toLowerCase() + ":lantern");
	}

	@Override
	public Icon getIcon(int i, int j) {
		return texture;
	}
}
