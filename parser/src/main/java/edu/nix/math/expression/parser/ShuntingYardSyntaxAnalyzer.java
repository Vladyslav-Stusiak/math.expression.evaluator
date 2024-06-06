package edu.nix.math.expression.parser;

import edu.nix.math.expression.lexer.domain.Associativity;
import edu.nix.math.expression.lexer.domain.Token;
import edu.nix.math.expression.lexer.domain.TokenType;
import edu.nix.math.expression.parser.domain.SyntaxTree;
import edu.nix.math.expression.parser.domain.SyntaxTreeImplementation;

import java.util.ArrayDeque;
import java.util.Deque;


public final class ShuntingYardSyntaxAnalyzer implements SyntaxAnalyzer {
	@Override
	public SyntaxTree analyze(final Token... tokens) {
		final var operatorStack = new ArrayDeque<Token>();
		final var outputStack = new ArrayDeque<SyntaxTree.Node>();

		for (Token token : tokens) {
			switch (token.getType()) {
				case NUMBER -> outputStack.offer(SyntaxTreeImplementation.NodeImplementation.of(token));
				case OPERATOR_PLUS, OPERATOR_MINUS, OPERATOR_MULTIPLY, OPERATOR_DIVIDE ->
						handleOperator(token, operatorStack, outputStack);
				case OPEN_PARENTHESIS -> operatorStack.push(token);
				case CLOSE_PARENTHESIS -> processOperatorsUntilLeftParentheses(operatorStack, outputStack);
			}
		}

		processRemainingOperators(operatorStack, outputStack);

		if (outputStack.size() != 1) {
			throw new IllegalArgumentException("Invalid expression");
		}

		return new SyntaxTreeImplementation(outputStack.poll());
	}

	private void handleOperator(final Token operator, final Deque<Token> operatorStack, final Deque<SyntaxTree.Node> outputQueue) {
		while (!operatorStack.isEmpty() &&
				canProcessOperator(operatorStack.peek(), operator)) {
			processOperator(operatorStack.pop(), outputQueue);
		}
		operatorStack.push(operator);
	}

	private boolean canProcessOperator(final Token top, final Token current) {
		if (top.getType() == TokenType.OPEN_PARENTHESIS) {
			return false;
		}
		return (top.getAssociativity() == Associativity.LEFT &&
				top.getPrecedence().value() >= current.getPrecedence().value()) ||
				(top.getAssociativity() == Associativity.RIGHT &&
						top.getPrecedence().value() > current.getPrecedence().value());
	}

	private void processOperator(final Token operator, final Deque<SyntaxTree.Node> outputQueue) {
		SyntaxTree.Node right = outputQueue.pollLast();
		SyntaxTree.Node left = outputQueue.pollLast();

		SyntaxTree.Node node = SyntaxTreeImplementation.NodeImplementation.of(operator);
		node.setLeft(left);
		node.setRight(right);

		outputQueue.add(node);
	}

	private void processOperatorsUntilLeftParentheses(final Deque<Token> operatorStack, final Deque<SyntaxTree.Node> outputQueue) {
		while (!operatorStack.isEmpty() && operatorStack.peek().getType() != TokenType.OPEN_PARENTHESIS) {
			processOperator(operatorStack.pop(), outputQueue);
		}
		operatorStack.pop();
	}

	private void processRemainingOperators(final Deque<Token> operatorStack, final Deque<SyntaxTree.Node> outputQueue) {
		while (!operatorStack.isEmpty()) {
			processOperator(operatorStack.pop(), outputQueue);
		}
	}
}
