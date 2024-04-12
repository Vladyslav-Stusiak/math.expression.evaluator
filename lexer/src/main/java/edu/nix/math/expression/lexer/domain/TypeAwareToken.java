package edu.nix.math.expression.lexer.domain;

public record TypeAwareToken(TokenType type) implements Token {
	@Override
	public TokenType getType() {
		return type();
	}
}
