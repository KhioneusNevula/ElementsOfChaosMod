package com.gm910.eoc.elements;

/***
 * Implementation of IElement
 */
public class Element implements IElement {

	private String name;

	Element(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
