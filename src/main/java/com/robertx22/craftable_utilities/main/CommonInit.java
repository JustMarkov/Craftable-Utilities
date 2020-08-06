package com.robertx22.craftable_utilities.main;

import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {
        Items.INSTANCE = new Items();
        Components.INSTANCE = new Components();

        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);

        ExileEvents.MOB_DEATH.register(new EventConsumer<ExileEvents.OnMobDeath>() {
            @Override
            public void accept(ExileEvents.OnMobDeath event) {
                if (event.mob instanceof PlayerEntity) {
                    try {
                        Components.INSTANCE.ENTITY_DATA.get(event.mob).pos = event.mob.getBlockPos();
                        Components.INSTANCE.ENTITY_DATA.get(event.mob).dim = event.mob.world.getDimensionRegistryKey()
                            .getValue()
                            .toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.out.println("Craftable Utilities loaded.");
    }

}