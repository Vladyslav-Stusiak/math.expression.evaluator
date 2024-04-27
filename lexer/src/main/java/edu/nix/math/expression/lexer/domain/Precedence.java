package edu.nix.math.expression.lexer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Precedence(int value) {
	public static @JsonCreator Precedence of(final @JsonProperty("precedence") int value) {
		return new Precedence(value);
	}
}
