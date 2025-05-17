package com.chivasss.pocket_dimestions.sound;

import com.chivasss.pocket_dimestions.PocketDim;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PocketDim.MODID);

    public static final RegistryObject<SoundEvent> SOME_SOUND = registerSoundEvent("some_sound");
    public static final RegistryObject<SoundEvent> SYMBIOTE_SCREAM = registerSoundEvent("symbiote_scream");
    public static final RegistryObject<SoundEvent> LASERBORE_LOOP = registerSoundEvent("laserbore_loop");
    public static final RegistryObject<SoundEvent> LASERBORE_OVERHEAT = registerSoundEvent("laserbore_overheat_start");
    public static final RegistryObject<SoundEvent> RESEARCH_BOOR_OPEN = registerSoundEvent("research_book_open");




    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(PocketDim.MODID, name)));
    }


    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
