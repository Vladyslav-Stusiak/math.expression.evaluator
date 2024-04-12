package edu.nix.math.expression.lexer;

import edu.nix.math.expression.lexer.domain.Expression;
import edu.nix.math.expression.lexer.domain.Token;

public sealed interface LexicalAnalyzer permits DefaultLexicalAnalyzer {
	Token[] tokenize(Expression expression);
}
