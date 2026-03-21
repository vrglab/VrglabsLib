package org.vrglab.vrglabsLib.Fabric.Utils;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.vrglab.azure.azurelib.common.animation.cache.AzIdentityRegistry;
import org.vrglab.azure.azurelib.common.network.packet.AzBlockEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.AzEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.AzItemStackDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.SendConfigDataPacket;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRenderer;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRendererRegistry;
import org.vrglab.azure.azurelib.common.render.item.AzItemRenderer;
import org.vrglab.azure.azurelib.common.render.item.AzItemRendererRegistry;
import org.vrglab.azure.azurelib.world.Armor.AzureArmor;
import org.vrglab.azure.azurelib.world.Item.AzureItem;
import org.vrglab.reflections.Reflections;
import org.vrglab.reflections.scanners.Scanners;
import org.vrglab.reflections.util.ConfigurationBuilder;
import org.vrglab.reflections.util.FilterBuilder;
import org.vrglab.vrglabsLib.Utils.ReflectionUtil;
import org.vrglab.vrglabsLib.api.autoRegistry.AutoRegistryLoader;
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;
import org.vrglab.vrglabsLib.api.callbacks.IClampedCallBack;
import org.vrglab.vrglabsLib.api.callbacks.IClampedSingleCallback;
import org.vrglab.vrglabsLib.api.functionProviders.IBlockEntityLoaderFunction;
import org.vrglab.vrglabsLib.api.helpers.TypeTransformer;
import org.vrglab.vrglabsLib.api.registries.Bootstrapper;
import org.vrglab.vrglabsLib.api.registries.interfaces.BootstrapType;
import org.vrglab.vrglabsLib.api.registries.interfaces.RegistryTypes;
import org.vrglab.vrglabsLib.core.VrglabsInitializer;
import org.vrglab.vrglabsLib.Utils.Utils;

import java.lang.annotation.Annotation;
import java.util.function.Supplier;

public class VrglabsFabricInitializer {

    public static void Initialize(String modid, String modPackage) {
        Create(modid);
        VrglabsInitializer.Initialize(modid, modPackage);
    }

    public static void InitializeClient(String modid) {
        ClientPlayNetworking.registerGlobalReceiver(
                AzEntityDispatchCommandPacket.TYPE,
                (packet, context) -> packet.handle()
        );
        ClientPlayNetworking.registerGlobalReceiver(
                AzItemStackDispatchCommandPacket.TYPE,
                (packet, context) -> packet.handle()
        );
        ClientPlayNetworking.registerGlobalReceiver(
                AzBlockEntityDispatchCommandPacket.TYPE,
                (packet, context) -> packet.handle()
        );
        ClientPlayNetworking.registerGlobalReceiver(
                SendConfigDataPacket.TYPE,
                (packet, context) -> packet.handle()
        );

        CreateClient(modid);
    }


    /**
     * Initializes Fabric related Callbacks and functions
     * @param modid The modid of the calling mod
     */
    @SuppressWarnings("unchecked")
    public static void Create(String modid) {
        createAutoRegistry();
       setEnergyStorageStatics(modid);
        setOreGenHelperStatics();
        setNetworkStatics();

        TypeTransformer.ObjectToType = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return args[0];
            }
        };

        ICallBack ItemRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                ResourceLocation id = CreateNewId(modid, args[0].toString());

                Item.Properties properties = Utils.MakeSafeSettings(((Supplier<Item.Properties>) args[2]).get(), RegistryTypes.ITEM, id);
                Item itemToRegister = ((IClampedCallBack<Item>) args[1]).accept(properties);

                return Registry.register(BuiltInRegistries.ITEM, id, itemToRegister);
            }
        };
        ICallBack BlockRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                ResourceLocation id = CreateNewId(modid, args[0].toString());
                Block b = Registry.register(BuiltInRegistries.BLOCK, id, (((IClampedSingleCallback<Block, BlockBehaviour.Properties>) args[1]).
                        accept(Utils.MakeSafeSettings(((Supplier<BlockBehaviour.Properties>) args[3]).get(), RegistryTypes.BLOCK, id))));
                Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(b, Utils.MakeSafeSettings( ((Supplier<Item.Properties>) args[2]).get(), RegistryTypes.BLOCK, id)));
                return b;
            }
        };
        ICallBack ItemlessBlockRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                ResourceLocation id = CreateNewId(modid, args[0].toString());
                return Registry.register(BuiltInRegistries.BLOCK, id, (((IClampedSingleCallback<Block, BlockBehaviour.Properties>) args[1]).
                        accept(Utils.MakeSafeSettings(((Supplier<BlockBehaviour.Properties>) args[2]).get(), RegistryTypes.BLOCK, id))));
            }
        };
        ICallBack POIRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return PointOfInterestHelper.register(CreateNewId(modid, args[0].toString()), (int) args[1], (int) args[2], (Block) args[3]);
            }
        };

        ICallBack ProfesionRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
               /* return Registry.register(Registries.VILLAGER_PROFESSION, CreateNewId(modid, args[0].toString()),
                        VillagerProfessionBuilder.create().id(CreateNewId(modid, args[0].toString()))
                                .workstation(RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), CreateNewId(modid, args[1].toString())))
                                .harvestableItems(args[2] == null ? null: (Item[])args[2]).secondaryJobSites(args[3] == null ? null: (Block[])args[3])
                                .workSound((args.length >= 5 && args[4] == null) ? null : (SoundEvent)args[4]).build());*/
                return null;
            }
        };

        ICallBack TradeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
               /* TradeOfferHelper.registerVillagerOffers((VillagerProfession) args[1] ,(int)args[2],
                        factories -> {
                            for (TradeOffer data: (TradeOffer[])args[3]) {
                                factories.add(((entity, random) -> data));
                            }
                        });*/
                return null;
            }
        };

        ICallBack OreGenRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
               /* RegistryKey r = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, CreateNewId(modid, args[0].toString()));
                Bootstrapper.SimpleRegister(BootstrapType.CONFIGUERED_FEAT_ORES, modid, r, new ConfiguredFeature((Feature) args[1], new OreFeatureConfig(((Supplier<List<OreFeatureConfig.Target>>) args[2]).get(),  (int)args[3])));
                return r;*/
                return null;
            }
        };

        ICallBack PlacedFeatCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
               /* RegistryKey r = RegistryKey.of(RegistryKeys.PLACED_FEATURE, CreateNewId(modid, args[0].toString()));
                Bootstrapper.SimpleRegister(BootstrapType.PLACED_FEAT, modid, r, args[1], args[2]);
                return r;*/
                return  null;
            }
        };

        ICallBack BiomeModCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                /*VinillaBiomeTypes types = (VinillaBiomeTypes) args[1];
                switch (types){
                    case END -> BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), (GenerationStep.Feature) args[2], (RegistryKey<PlacedFeature>) args[3]);
                    case NETHER -> BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), (GenerationStep.Feature) args[2], (RegistryKey<PlacedFeature>) args[3]);
                    case OVERWORLD -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), (GenerationStep.Feature) args[2], (RegistryKey<PlacedFeature>) args[3]);
                }*/
                return null;
            }
        };

        ICallBack BlockEntityTypeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                FabricBlockEntityTypeBuilder.Factory factory = new FabricBlockEntityTypeBuilder.Factory() {
                    @Override
                    public BlockEntity create(BlockPos blockPos, BlockState blockState) {
                        return ((IBlockEntityLoaderFunction<BlockEntity>) args[1]).create(blockPos, blockState);
                    }
                };
                return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateNewId(modid, args[0].toString()), FabricBlockEntityTypeBuilder.create(factory, (Block) args[2]).build());
            }
        };


        ICallBack RecipeSerializerRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, CreateNewId(modid, args[0].toString()), (RecipeSerializer) args[1]);
            }
        };

        ICallBack RecipeTypeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return Registry.register(BuiltInRegistries.RECIPE_TYPE, CreateNewId(modid, args[0].toString()), (RecipeType) args[1]);
            }
        };

        ICallBack ItemGroupRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CreateNewId(modid, args[0].toString()), ((Supplier<CreativeModeTab>) args[1]).get());
            }
        };

        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(ItemGroupRegistryCallBack, RegistryTypes.CREATIVE_MODE_TAB, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(ItemRegistryCallBack, RegistryTypes.ITEM, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(ItemlessBlockRegistryCallBack, RegistryTypes.ITEMLESS_BLOCK, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(BlockEntityTypeRegistryCallBack, RegistryTypes.BLOCK_ENTITY_TYPE, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(BlockRegistryCallBack, RegistryTypes.BLOCK, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(POIRegistryCallBack, RegistryTypes.POI, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(ProfesionRegistryCallBack, RegistryTypes.PROFESSION, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(TradeRegistryCallBack, RegistryTypes.TRADE, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(OreGenRegistryCallBack, RegistryTypes.CONFIGURED_FEAT_ORE, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(PlacedFeatCallBack, RegistryTypes.PLACED_FEAT, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(BiomeModCallBack, RegistryTypes.BIOME_MODIFICATIONS, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(RecipeSerializerRegistryCallBack, RegistryTypes.RECIPE_SERIALIZER, modid);
        org.vrglab.vrglabsLib.api.registries.Registry.initRegistry(RecipeTypeRegistryCallBack, RegistryTypes.RECIPE_TYPE, modid);
    }

    public static void CreateClient(String modid) {
        Bootstrapper.initBootstrapper(new ICallBack() {
            @Override
            public Object accept(Object... args) {
                AzureArmor tranformed_obj = (AzureArmor) TypeTransformer.ObjectToType.accept(args[0]);

                AzArmorRendererRegistry.register((Supplier<AzArmorRenderer>) tranformed_obj.GetAzureRenderer(), tranformed_obj.asItem());

                return null;
            }
        }, BootstrapType.AZURE_ARMOR.getTypeId(),  modid);


        Bootstrapper.initBootstrapper(new ICallBack() {
            @Override
            public Object accept(Object... args) {
                AzureItem tranformed_obj = (AzureItem) TypeTransformer.ObjectToType.accept(args[0]);
                AzItemRendererRegistry.register((Supplier<AzItemRenderer>) tranformed_obj.GetAzureRenderer(), tranformed_obj);
                return null;
            }
        }, BootstrapType.AZURE_ITEM.getTypeId(),  modid);


        Bootstrapper.initBootstrapper(new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Class clazz = (Class) args[1];

                if (ReflectionUtil.isSubclassOrSame(clazz, AzureArmor.class)) {
                    AzureArmor tranformed_obj = (AzureArmor) TypeTransformer.ObjectToType.accept(args[0]);
                    AzIdentityRegistry.register(tranformed_obj);
                }

                if (ReflectionUtil.isSubclassOrSame(clazz, AzureItem.class)) {
                    AzureItem tranformed_obj = (AzureItem) TypeTransformer.ObjectToType.accept(args[0]);
                    AzIdentityRegistry.register(tranformed_obj);
                }

                return null;
            }
        }, BootstrapType.AZURE_ID.getTypeId(),  modid);
    }

    public static ResourceLocation CreateNewId(String modid, String pathId ){
        return ResourceLocation.fromNamespaceAndPath(modid, pathId);
    }

   /* public static void configureBootstrapped(RegistryWrapper.WrapperLookup Wrapper, FabricDynamicRegistryProvider.Entries entries, RegistryKey... keys) {
        for (RegistryKey key : keys) {
            entries.addAll(Wrapper.getOrThrow(key));
        }
    }

    public static void callBlockDataGen(BlockStateModelGenerator blockStateModelGenerator, String modid) {
        ICallBack BlockDatagen = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                blockStateModelGenerator.registerSimpleCubeAll(Utils.convertToMcSafeType(args[0]));
                return null;
            }
        };

        DataGenRegistry.initRegistery(BlockDatagen, DataGenType.Block, modid);
    }

    public static void callItemDataGen(ItemModelGenerator itemModelGenerator, String modid) {
        ICallBack Itemdatagen = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                itemModelGenerator.register(Utils.convertToMcSafeType(args[0]), Models.GENERATED);
                return null;
            }
        };

        DataGenRegistry.initRegistery(Itemdatagen, DataGenType.Item, modid);
    }

    public static void boostrap(RegistryBuilder builder, String modid) {
        builder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, (r)->Bootstrapper.initBootstrapper((args)->{
           return r.register((RegistryKey<ConfiguredFeature<?,?>>) args[0], (ConfiguredFeature<?, ?>)args[1]);
        }, BootstrapType.CONFIGUERED_FEAT_ORES, modid));


        builder.addRegistry(RegistryKeys.PLACED_FEATURE, (r)->Bootstrapper.initBootstrapper((args)->{
            var config_feat_lookup = r.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
            return r.register((RegistryKey<PlacedFeature>)args[0], new PlacedFeature(config_feat_lookup.getOrThrow((RegistryKey<ConfiguredFeature<?, ?>>) args[1]), (List<PlacementModifier>)args[2]));
        }, BootstrapType.PLACED_FEAT, modid));
    }*/

    private static void setEnergyStorageStatics(String modid) {
       /* EnergyStorageUtils.createStorageInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return new SimpleEnergyStorage((Long) args[0],(Long)  args[1],(Long)  args[2]){
                    @Override
                    protected void onFinalCommit() {
                        super.onFinalCommit();
                        try {
                            ((EnergyStorage)args[4]).makeDirty.accept();
                        } catch (Throwable t) {

                        }
                    }
                };
            }
        };

        EnergyStorageUtils.receiveEnergyInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                try (Transaction openTrans = Transaction.openOuter()) {
                    long res = ((SimpleEnergyStorage)args[0]).insert((long)args[1], openTrans);
                    openTrans.commit();
                    return res;
                }
            }
        };

        EnergyStorageUtils.extractEnergyInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                try (Transaction openTrans = Transaction.openOuter()) {
                    long res = ((SimpleEnergyStorage)args[0]).extract((long)args[1], openTrans);
                    openTrans.commit();
                    return res;
                }
            }
        };

        EnergyStorageUtils.hasExternalStorage = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                BlockEntity entity = (BlockEntity) args[0];

                if(entity == null){
                    return false;
                }

                return (team.reborn.energy.api.EnergyStorage.SIDED.find(entity.getWorld(), entity.getPos(), Direction.NORTH) != null ||
                        team.reborn.energy.api.EnergyStorage.SIDED.find(entity.getWorld(), entity.getPos(), Direction.EAST) != null ||
                        team.reborn.energy.api.EnergyStorage.SIDED.find(entity.getWorld(), entity.getPos(), Direction.WEST) != null ||
                        team.reborn.energy.api.EnergyStorage.SIDED.find(entity.getWorld(), entity.getPos(), Direction.SOUTH) != null);
            }
        };

        EnergyStorageUtils.wrapExternalStorage = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                team.reborn.energy.api.EnergyStorage storage = team.reborn.energy.api.EnergyStorage.SIDED.find((World) args[0], (BlockPos) args[1], (Direction) args[2]);
                return new EnergyStorage(storage, storage.getCapacity(), storage.getCapacity(), storage.getCapacity(), storage.getAmount());
            }
        };*/
    }

    private static void setOreGenHelperStatics() {
       /* OreGenFeatCreationHelper.ObjectBlockToStateConverted = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ((Block)args[0]).getDefaultState();
            }
        };

        PlacementModifierCreationHelper.getHeightModifications = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom((Integer) args[0]), YOffset.aboveBottom((Integer) args[1]));
            }
        };*/
    }

    private static void setNetworkStatics() {
      /*  Network.registerGlobalReceiver = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {
                //TODO: Fix networking
                //ServerPlayNetworking.registerGlobalReceiver((ResourceLocation) args[0], (a, b, c, d, e)->((ICallBack)args[1]).accept(a,b,c,d,e));
            }
        };

        Network.clientSendPacket = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {
                //TODO: Fix networking
                //ClientPlayNetworking.send((ResourceLocation) args[0], (PacketByteBuf) args[1]);
            }
        };*/
    }

    private static void createAutoRegistry() {
        AutoRegistryLoader.collectAnnotatedFieldsForMod = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Reflections reflections = new Reflections(
                        new ConfigurationBuilder().
                                forPackage(args[0].toString()).
                                filterInputsBy(new FilterBuilder().includePackage(args[0].toString())).
                                setScanners(Scanners.FieldsAnnotated));
                return reflections.getFieldsAnnotatedWith((Class<? extends Annotation>) args[1]);
            }
        };

        AutoRegistryLoader.collectAnnotatedTypesForMod = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Reflections reflections = new Reflections(
                        new ConfigurationBuilder().
                                forPackage(args[0].toString()).
                                filterInputsBy(new FilterBuilder().includePackage(args[0].toString())).
                                setScanners(Scanners.TypesAnnotated));
                return reflections.getTypesAnnotatedWith((Class<? extends Annotation>) args[1]);
            }
        };

        AutoRegistryLoader.entityTypeBlockSelector = new ICallBack() {

            @Override
            public Object accept(Object... args) {
                return ((org.vrglab.vrglabsLib.api.autoRegistry.World.Block) ((org.vrglab.vrglabsLib.api.autoRegistry.World.BlockEntity) args[0]).getArgs().get("block")).getRegisteredObject();
            }
        };
    }
}
