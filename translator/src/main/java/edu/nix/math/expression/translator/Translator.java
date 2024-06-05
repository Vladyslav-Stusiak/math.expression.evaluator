package edu.nix.math.expression.translator;

import edu.nix.math.expression.parser.domain.SyntaxTree;

public interface Translator {
	long translate(SyntaxTree syntaxTree);
}
