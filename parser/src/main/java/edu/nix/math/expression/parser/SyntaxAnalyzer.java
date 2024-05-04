package edu.nix.math.expression.parser;

import edu.nix.math.expression.lexer.domain.Token;
import edu.nix.math.expression.parser.domain.SyntaxTree;

public interface SyntaxAnalyzer {
	SyntaxTree analyze(Token... tokens);
}
