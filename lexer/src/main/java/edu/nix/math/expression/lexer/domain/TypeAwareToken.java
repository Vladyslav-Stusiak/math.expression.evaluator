package edu.nix.math.expression.lexer.domain;

public record TypeAwareToken(TokenType type, String rawValue, Associativity associativity,
                             Precedence precedence) implements Token {
	@Override
	public TokenType getType() {
		return type();
	}

	@Override
	public String getRawValue() {
		return rawValue();
	}

	@Override
	public Associativity getAssociativity() {
		return associativity();
	}

	@Override
	public Precedence getPrecedence() {
		return precedence();
	}
}
