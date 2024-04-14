package edu.nix.math.expression.lexer.domain;

public record TypeAwareToken(TokenType type, String rawValue) implements Token {
	@Override
	public TokenType getType() {
		return type();
	}

	@Override
	public String getRawValue() {
		return rawValue();
	}
}
