package com.developer.personaapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhoneType {

	HOME("Home"),
	MOBILE("Mobile"),
	COMERCIAL("COMERCIAL");

	private final String description;
}
