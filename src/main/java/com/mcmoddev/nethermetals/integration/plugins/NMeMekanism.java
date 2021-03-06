package com.mcmoddev.nethermetals.integration.plugins;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.mcmoddev.basemetals.data.MaterialNames;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.Mekanism;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.Config.Options;
import com.mcmoddev.nethermetals.NetherMetals;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@MMDPlugin(
		addonId = NetherMetals.MODID,
		pluginId = NMeMekanism.PLUGIN_MODID)
public final class NMeMekanism extends Mekanism implements IIntegration {

	/**
	 *
	 */
	@Override
	public void init() {
		if (!Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}

		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	 *
	 * @param event
	 *            The Event.
	 */
	@SubscribeEvent
	public void regCallback(final RegistryEvent.Register<IRecipe> event) {
		final List<String> mekProvides = Arrays.asList(MaterialNames.IRON, MaterialNames.GOLD,
				com.mcmoddev.modernmetals.data.MaterialNames.OSMIUM, MaterialNames.COPPER,
				MaterialNames.TIN, MaterialNames.SILVER, MaterialNames.LEAD);

		mekProvides.stream().map(Materials::getMaterialByName).filter(mat -> !mat.isEmpty())
				.filter(mat -> gasExists(mat.getName())).forEach(mat -> {
					final ItemStack ore = mat.getBlockItemStack(Names.NETHERORE);
					final ItemStack dust = new ItemStack(
							ForgeRegistries.ITEMS
									.getValue(new ResourceLocation(PLUGIN_MODID, "dust")),
							4, mekProvides.indexOf(mat.getName()));
					final ItemStack clump = new ItemStack(
							ForgeRegistries.ITEMS.getValue(
									new ResourceLocation(PLUGIN_MODID, Names.CLUMP.toString())),
							6, mekProvides.indexOf(mat.getName()));
					final ItemStack shard = new ItemStack(
							ForgeRegistries.ITEMS.getValue(
									new ResourceLocation(PLUGIN_MODID, Names.SHARD.toString())),
							8, mekProvides.indexOf(mat.getName()));

					addEnrichmentChamberRecipe(ore, dust);
					addPurificationChamberRecipe(ore, clump);
					addChemicalInjectionChamberRecipe(ore, shard);
					addChemicalDissolutionChamberRecipe(ore, mat.getName(), 2000);
				});

		Materials.getAllMaterials().stream().filter(material -> material.hasBlock(Names.NETHERORE))
				.filter(material -> gasExists(material.getName()))
				.filter(material -> !mekProvides.contains(material.getName()))
				.forEach(this::addMultRecipe);

		if (Materials.hasMaterial(MaterialNames.LAPIS)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.LAPIS);
			addEnrichmentChamberRecipe(material.getBlockItemStack(Names.NETHERORE),
					new ItemStack(net.minecraft.init.Items.DYE, 24, 4));
		}

		if (Materials.hasMaterial(MaterialNames.REDSTONE)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.REDSTONE);
			addEnrichmentChamberRecipe(material.getBlockItemStack(Names.NETHERORE),
					new ItemStack(net.minecraft.init.Items.REDSTONE, 24));
		}

		if (Materials.hasMaterial(MaterialNames.COAL)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.COAL);
			addEnrichmentChamberRecipe(material.getBlockItemStack(Names.NETHERORE),
					new ItemStack(net.minecraft.init.Items.COAL, 4, 0));
		}

		if (Materials.hasMaterial(MaterialNames.DIAMOND)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.DIAMOND);
			addEnrichmentChamberRecipe(material.getBlockItemStack(Names.NETHERORE),
					new ItemStack(net.minecraft.init.Items.DIAMOND, 4));
		}

		if (Materials.hasMaterial(MaterialNames.EMERALD)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.EMERALD);
			addEnrichmentChamberRecipe(material.getBlockItemStack(Names.NETHERORE),
					new ItemStack(net.minecraft.init.Items.EMERALD, 4));
		}
	}

	private void addMultRecipe(@Nonnull final MMDMaterial material) {
		final ItemStack ore = material.getBlockItemStack(Names.NETHERORE);

		if (material.hasItem(Names.POWDER)) {
			addEnrichmentChamberRecipe(ore, material.getItemStack(Names.POWDER, 4));
		}
		if (material.hasItem(Names.CLUMP)) {
			addPurificationChamberRecipe(ore, material.getItemStack(Names.CLUMP, 6));
		}
		if (material.hasItem(Names.SHARD)) {
			addChemicalInjectionChamberRecipe(ore, material.getItemStack(Names.SHARD, 8));
		}
		addChemicalDissolutionChamberRecipe(ore, material.getName(), 2000);
	}
}
