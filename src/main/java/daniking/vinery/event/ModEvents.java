package daniking.vinery.event;

import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.entity.WanderingWinemakerEntity;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VineryEntites;
import daniking.vinery.registry.VineryVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


public class ModEvents {

    @Mod.EventBusSubscriber(modid = Vinery.MODID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event){
            if(event.getType().equals(VineryVillagers.WINEMAKER.get())){
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

                List<VillagerTrades.ItemListing> level1 = trades.get(1);
                level1.add(new VineryVillagers.BuyForOneEmeraldFactory(ObjectRegistry.RED_GRAPE.get(), 5, 4, 5));
                level1.add(new VineryVillagers.BuyForOneEmeraldFactory(ObjectRegistry.WHITE_GRAPE.get(), 5, 4, 5));
                level1.add(new VineryVillagers.SellItemFactory(ObjectRegistry.RED_GRAPE_SEEDS.get(), 2, 1, 5));
                level1.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WHITE_GRAPE_SEEDS.get(), 2, 1, 5));

                List<VillagerTrades.ItemListing> level2 = trades.get(2);
                level2.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WINE_BOTTLE.get(), 1, 2, 7));

                List<VillagerTrades.ItemListing> level3 = trades.get(3);
                level3.add(new VineryVillagers.SellItemFactory(ObjectRegistry.COOKING_POT.get(), 3, 1, 10));
                level3.add(new VineryVillagers.SellItemFactory(ObjectRegistry.FLOWER_BOX.get(), 3, 1, 10));
                level3.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WHITE_GRAPE_CRATE.get(), 7, 1, 10));
                level3.add(new VineryVillagers.SellItemFactory(ObjectRegistry.RED_GRAPE_CRATE.get(), 7, 1, 10));

                List<VillagerTrades.ItemListing> level4 = trades.get(4);
                level4.add(new VineryVillagers.SellItemFactory(ObjectRegistry.BASKET.get(), 4, 1, 10));
                level4.add(new VineryVillagers.SellItemFactory(ObjectRegistry.FLOWER_POT.get(), 5, 1, 10));
                level4.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WINDOW.get(), 12, 1, 10));
                level4.add(new VineryVillagers.SellItemFactory(ObjectRegistry.CHERRY_BEAM.get(), 6, 1, 10));

                List<VillagerTrades.ItemListing> level5 = trades.get(5);
                level5.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WINE_BOX.get(), 10, 1, 10));
                level5.add(new VineryVillagers.SellItemFactory(ObjectRegistry.KING_DANIS_WINE.get(), 4, 1, 10));
                level5.add(new VineryVillagers.SellItemFactory(ObjectRegistry.GLOVES.get(), 12, 1, 15));
            }
        }

        @SubscribeEvent
        public static void lootTableLoadEvent(LootTableLoadEvent event) {
            final ResourceLocation resourceLocation = new VineryIdentifier("inject/seeds");
            ResourceLocation id = event.getTable().getLootTableId();
            if (Blocks.GRASS.getLootTable().equals(id) || Blocks.TALL_GRASS.getLootTable().equals(id) || Blocks.FERN.getLootTable().equals(id)) {
                event.getTable().addPool(LootPool.lootPool().add(LootTableReference.lootTableReference(resourceLocation).setWeight(1)).build());
            }
        }


    }

    @Mod.EventBusSubscriber(modid = Vinery.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(VineryEntites.MULE.get(), Llama.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.2f).build());
            event.put(VineryEntites.WANDERING_WINEMAKER.get(), WanderingWinemakerEntity.createMobAttributes().build());
        }

        @SubscribeEvent
        public static void colorHandlerBlockEvent(RegisterColorHandlersEvent.Block event) {
            event.register((state, world, pos, tintIndex) -> {
                if (world == null || pos == null) {
                    return -1;
                }
                return BiomeColors.getAverageGrassColor(world, pos);
            }, ObjectRegistry.GRASS_SLAB.get());
            event.register((state, world, pos, tintIndex) -> {
                if (world == null || pos == null) {
                    return -1;
                }
                return BiomeColors.getAverageWaterColor(world, pos);
            }, ObjectRegistry.KITCHEN_SINK.get());
        }

        @SubscribeEvent
        public static void colorHandlerItemEvent(RegisterColorHandlersEvent.Item event) {
            event.register((p_92672_, p_92673_) -> GrassColor.get(1.0, 0.5), ObjectRegistry.GRASS_SLAB.get().asItem());
        }
    }


}
