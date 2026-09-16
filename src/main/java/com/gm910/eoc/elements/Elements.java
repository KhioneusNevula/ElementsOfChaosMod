package com.gm910.eoc.elements;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;

import com.gm910.eoc.main.ModRegistries;
import com.gm910.eoc.util.ModUtils;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Iterables;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;

public class Elements extends SimpleJsonResourceReloadListener<IElement> {
	private static final Logger LOGGER = LogUtils.getLogger();
	private BiMap<Identifier, IElement> elements = ImmutableBiMap.of();
	private static Optional<Elements> INSTANCE = Optional.empty();
	private static Codec<IElement> CODEC;

	/**
	 * Codec to retrieve symbols by resource location
	 */
	public static final Codec<IElement> BY_NAME_CODEC = Identifier.CODEC.flatXmap((s) -> {
		var symbol = Elements.instance().elements.get(s);
		if (symbol == null) {
			return DataResult.error(() -> "No element for " + s);
		}
		return DataResult.success(symbol);
	}, (s) -> {
		var rl = Elements.instance().elements.inverse().get(s);
		if (rl == null) {
			return DataResult.error(() -> "Unregistered element: " + s.toString());
		}
		return DataResult.success(rl);
	});

	public static final Codec<IElement> elementCodec() {
		if (CODEC == null)
			CODEC = IElement.createCodec();
		return CODEC;
	}

	private Elements(Provider prov) {
		super(prov, elementCodec(), ModRegistries.ELEMENTS);
	}

	@Override
	protected Map<Identifier, IElement> prepare(ResourceManager mana, ProfilerFiller p_10772_) {

		return super.prepare(mana, p_10772_);
	}

	@Override
	protected void apply(Map<Identifier, IElement> map, ResourceManager rm, ProfilerFiller filler) {
		this.elements = HashBiMap.create(map);
		LOGGER.info("Loaded elements: {}", elements.values());
	}

	public static Elements instance() {
		return INSTANCE.get();
	}

	/*** Return an iterable of all elements */
	public Iterable<IElement> getAllElements() {
		return Iterables.unmodifiableIterable(elements.values());
	}

	/*** Return a given element */
	public IElement getElement(Identifier id) {
		return elements.get(id);
	}

	/**
	 * So we can add this reloadable registry to the listener
	 * 
	 * @param event
	 */
	public static void eventAddListener(AddReloadListenerEvent event) {
		INSTANCE = Optional.of(new Elements(event.getRegistries()));
		event.addListener(INSTANCE.get());
	}

}
