package edu.nix.math.expression.lexer.domain;

public sealed interface Token permits TypeAwareToken {
	TokenType getType();
}