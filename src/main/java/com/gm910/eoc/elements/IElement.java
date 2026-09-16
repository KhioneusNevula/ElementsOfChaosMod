package com.gm910.eoc.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/***
 * Interface defining the basic elements
 */
public interface IElement {

	public static Codec<IElement> createCodec() {
		return RecordCodecBuilder.create(instance -> // Given an emanation
		instance.group(Codec.STRING.fieldOf("name").forGetter(IElement::getName)).apply(instance,
				(n) -> new Element(n)));
	}

	/***
	 * Return the element's name (translation key)
	 * 
	 * @return
	 */
	public String getName();
}
