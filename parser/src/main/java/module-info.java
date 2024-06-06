module math.expression.evaluator.parser {
	requires transitive math.expression.evaluator.lexer;

	exports edu.nix.math.expression.parser;
	exports edu.nix.math.expression.parser.domain;
}