package com.dlc.testmod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(MainMod.MODID)
public class MainMod {
    public static final String MODID = "testmod"; // Mod ID
    private static final Logger LOGGER = LogUtils.getLogger();


    private  static  final DeferredRegister<Block> BLOCK=DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    // 注册物品
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public  static  final  RegistryObject<Block> myblock=BLOCK.register("new_item",
            ()->  new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE) ));
    // 定义新物品
    public static final RegistryObject<Item> NEW_ITEM = ITEMS.register("new_item",
            () -> new BlockItem(myblock.get(), new Item.Properties()));
    // ItemInit.java
//    public static final RegistryObject<Item> CRYING_DIAMOND = ITEMS.register("crying_diamond",
//            () -> new Item(new Item.Properties())); // 直接使用 new Item.Properties()
    public MainMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        BLOCK.register(modEventBus);
        // 注册物品
        ITEMS.register(modEventBus);

        // 注册事件监听器
        modEventBus.addListener(this::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
    }

    // 将新物品添加到创造模式标签页
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(NEW_ITEM);
        }
    }

    // 服务器启动事件（可选）
 //   @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {
//        LOGGER.info("HELLO from server starting");
//    }
}