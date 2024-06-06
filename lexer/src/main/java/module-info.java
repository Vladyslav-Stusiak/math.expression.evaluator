module math.expression.evaluator.lexer {
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;

	exports edu.nix.math.expression.lexer;
	exports edu.nix.math.expression.lexer.domain;
	exports edu.nix.math.expression.lexer.configuration;
}