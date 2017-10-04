package com.mcmoddev.nethermetals.integration.plugins;

import com.mcmoddev.nethermetals.NetherMetals;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.material.MMDMaterial;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@MMDPlugin(addonId = NetherMetals.MODID,
		   pluginId = TinkersConstruct.PLUGIN_MODID, 
           preInitCallback="preInit", 
           initCallback="initCallback",
           postInitCallback="postInit")
public class TinkersConstruct extends com.mcmoddev.lib.integration.plugins.TinkersConstructBase implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.lib.util.ConfigBase.Options.isModEnabled("enableTinkersConstruct")) {
			return;
		}
		
		initDone = true;
	}
	
	public void preInit() {
		super.preInitSetup();
		registerMelting();
		super.setMaterialsVisible();
	}
	
	public void initCallback() {
		super.initSetup();
	}

	public void postInit() {
		super.postInitSetup();
	}

	private void registerMelting() {
		registerExtraMeltingWrapper(Materials.getMaterialByName("coal"), 576);
		registerExtraMeltingWrapper(Materials.getMaterialByName("diamond"), 576);
		registerExtraMeltingWrapper(Materials.getMaterialByName("emerald"), 576);
		registerExtraMeltingWrapper(Materials.getMaterialByName("gold"), 576);
		registerExtraMeltingWrapper(Materials.getMaterialByName("iron"), 576);
		// No such thing as a Lapis fluid in the code
		//registerExtraMeltingWrapper(Materials.getMaterialByName("lapis"), 576);
		registerExtraMeltingWrapper(Materials.getMaterialByName("redstone"), 576);
		
		if( Loader.isModLoaded("basemetals") ) {
			registerExtraMeltingWrapper(Materials.getMaterialByName("antimony"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("bismuth"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("copper"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("lead"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("mercury"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("nickel"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("platinum"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("silver"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("tin"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("zinc"), 576);
		}
		
		if( Loader.isModLoaded("modernmetals") ) {
			registerExtraMeltingWrapper(Materials.getMaterialByName("aluminum"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("cadmium"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("chromium"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("iridium"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("magnesium"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("manganese"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("osmium"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("plutonium"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("rutile"),  576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("tantalum"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("titanium"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("tungsten"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("uranium"), 576);
			registerExtraMeltingWrapper(Materials.getMaterialByName("zirconium"), 576);
		}
	}

	private static void registerExtraMeltingWrapper(MMDMaterial material, int quantity) {
		FluidStack result = FluidRegistry.getFluidStack(material.getName(), quantity);
		registry.registerMelting(Item.getItemFromBlock(material.getBlock(Names.NETHERORE)), result);
	}	
}
