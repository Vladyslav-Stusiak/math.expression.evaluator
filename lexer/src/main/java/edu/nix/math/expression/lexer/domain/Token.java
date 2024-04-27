package edu.nix.math.expression.lexer.domain;

public sealed interface Token permits TypeAwareToken {
	TokenType getType();
	String getRawValue();
	Associativity getAssociativity();
	Precedence getPrecedence();
}
