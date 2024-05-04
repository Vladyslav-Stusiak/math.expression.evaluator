package edu.nix.math.expression.parser.domain;

import edu.nix.math.expression.lexer.domain.Token;

import java.util.Objects;
import java.util.Optional;

public record SyntaxTreeImplementation(Node root) implements SyntaxTree {
	public static class NodeImplementation implements SyntaxTree.Node {
		private final Token token;
		private Node left;
		private Node right;

		public NodeImplementation(final Token token) {
			this.token = token;
		}

		@Override
		public Token getToken() {
			return token;
		}

		@Override
		public Optional<Node> getLeft() {
			return Optional.ofNullable(left);
		}

		@Override
		public Optional<Node> getRight() {
			return Optional.ofNullable(right);
		}

		public void setLeft(final Node left) {
			this.left = left;
		}

		public void setRight(final Node right) {
			this.right = right;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof NodeImplementation node)) return false;
			return Objects.equals(getToken(), node.getToken())
					&& Objects.equals(getLeft(), node.getLeft())
					&& Objects.equals(getRight(), node.getRight());
		}

		@Override
		public int hashCode() {
			return Objects.hash(getToken(), getLeft(), getRight());
		}

		@Override
		public String toString() {
			return "NodeImpl{" +
					"token=" + token +
					", left=" + left +
					", right=" + right +
					'}';
		}
	}

	@Override
	public Node getRoot() {
		return root();
	}
}
