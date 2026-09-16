package com.gm910.eoc.main;

import com.gm910.eoc.elements.IElement;
import com.gm910.eoc.util.ModUtils;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/***
 * All registries for this mod
 */
public class ModRegistries {

	public static final ResourceKey<Registry<IElement>> ELEMENTS = ResourceKey
			.createRegistryKey(ModUtils.path("element"));

	private ModRegistries() {
	}

}
