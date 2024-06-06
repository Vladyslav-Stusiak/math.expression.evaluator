package edu.nix.math.expression.translator;

import edu.nix.math.expression.parser.domain.SyntaxTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepthFirstTranslator implements Translator {
	private static final Logger LOGGER = LoggerFactory.getLogger(Translator.class);

	@Override
	public double translate(final SyntaxTree syntaxTree) {
		LOGGER.debug("Translating syntax tree: {}", syntaxTree);
		return evaluateTree(syntaxTree);
	}

	private static double evaluateTree(final SyntaxTree tree) {
		return visitNodes(tree);
	}

	private static double visitNodes(final SyntaxTree tree) {
		final var root = tree.getRoot();
		return visitNode(root);
	}

	private static double visitNode(final SyntaxTree.Node node) {
		final var leftNode = node.getLeft();
		final var rightNode = node.getRight();

		double leftEvaluation = 0, rightEvaluation = 0;

		if (leftNode.isPresent()) {
			leftEvaluation += visitNode(leftNode.get());
		}

		if (rightNode.isPresent()) {
			rightEvaluation += visitNode(rightNode.get());
		}

		LOGGER.debug("Visiting node: {}, with token {}", node, node.getToken());

		return switch (node.getToken().getType()) {
			case NUMBER -> Double.parseDouble(node.getToken().getRawValue());
			case OPERATOR_PLUS, UNKNOWN -> leftEvaluation + rightEvaluation;
			case OPERATOR_MINUS -> leftEvaluation - rightEvaluation;
			case OPERATOR_MULTIPLY -> leftEvaluation * rightEvaluation;
			case OPERATOR_DIVIDE -> leftEvaluation / rightEvaluation;
			default -> throw new IllegalStateException("Unexpected value: " + node.getToken().getType());
		};
	}
}
