package edu.nix.math.expression.parser.domain;

import edu.nix.math.expression.lexer.domain.Token;

import java.util.Optional;

public interface SyntaxTree {
	interface Node {
		Token getToken();
		Optional<Node> getLeft();
		void setLeft(Node left);
		Optional<Node> getRight();
		void setRight(Node right);
	}

	Node getRoot();
}
