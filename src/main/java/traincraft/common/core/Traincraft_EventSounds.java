package traincraft.common.core;

import java.io.File;

import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import traincraft.common.Traincraft;
import traincraft.common.library.EnumSoundsFiles;
import traincraft.common.library.Info;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class Traincraft_EventSounds {
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event) {
		try {
			for (EnumSoundsFiles sounds : EnumSoundsFiles.values()) {
				event.manager.soundPoolSounds.addSound(Info.resourceLocation+":"+sounds.getSoundName());
			}
		} catch (Exception e) {
			System.err.println("Failed to register one or more sounds.");
		}
	}
}
