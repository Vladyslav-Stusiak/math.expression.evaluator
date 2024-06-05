package edu.nix.math.expression.translator;

import edu.nix.math.expression.parser.domain.SyntaxTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepthFirstTranslator implements Translator {
	private static final Logger LOGGER = LoggerFactory.getLogger(Translator.class);

	@Override
	public long translate(final SyntaxTree syntaxTree) {
		LOGGER.debug("Translating syntax tree: {}", syntaxTree);
		return evaluateTree(syntaxTree);
	}

	private static long evaluateTree(final SyntaxTree tree) {
		return visitNodes(tree);
	}

	private static long visitNodes(final SyntaxTree tree) {
		final var root = tree.getRoot();
		return visitNode(root);
	}

	private static long visitNode(final SyntaxTree.Node node) {
		final var leftNode = node.getLeft();
		final var rightNode = node.getRight();

		long leftEvaluation = 0, rightEvaluation = 0;

		if (leftNode.isPresent()) {
			leftEvaluation += visitNode(leftNode.get());
		}

		if (rightNode.isPresent()) {
			rightEvaluation += visitNode(rightNode.get());
		}

		LOGGER.debug("Visiting node: {}, with token {}", node, node.getToken());

		return switch (node.getToken().getType()) {
			case NUMBER -> Long.parseLong(node.getToken().getRawValue());
			case OPERATOR_PLUS -> leftEvaluation + rightEvaluation;
			case OPERATOR_MINUS -> leftEvaluation - rightEvaluation;
			case OPERATOR_MULTIPLY -> leftEvaluation * rightEvaluation;
			case OPERATOR_DIVIDE -> leftEvaluation / rightEvaluation;
			case UNKNOWN -> leftEvaluation + rightEvaluation;
			default -> throw new IllegalStateException("Unexpected value: " + node.getToken().getType());
		};
	}
}
