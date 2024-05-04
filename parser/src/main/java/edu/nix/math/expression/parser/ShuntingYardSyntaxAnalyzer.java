package edu.nix.math.expression.parser;

import edu.nix.math.expression.lexer.domain.Associativity;
import edu.nix.math.expression.lexer.domain.Token;
import edu.nix.math.expression.lexer.domain.TokenType;
import edu.nix.math.expression.parser.domain.SyntaxTree;
import edu.nix.math.expression.parser.domain.SyntaxTreeImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public final class ShuntingYardSyntaxAnalyzer implements SyntaxAnalyzer {
	private final Stack<Token> operatorStack;
	private final List<SyntaxTree.Node> outputQueue;

	public ShuntingYardSyntaxAnalyzer() {
		this.operatorStack = new Stack<>();
		this.outputQueue = new ArrayList<>();
	}

	@Override
	public SyntaxTree analyze(Token... tokens) {
		for (Token token : tokens) {
			switch (token.getType()) {
				case NUMBER -> outputQueue.add(createNode(token));
				case OPERATOR_PLUS, OPERATOR_MINUS, OPERATOR_MULTIPLY, OPERATOR_DIVIDE -> handleOperator(token);
				case OPEN_PARENTHESIS -> operatorStack.push(token);
				case CLOSE_PARENTHESIS -> processOperatorsUntilLeftParen();
			}
		}
		processRemainingOperators();

		if (outputQueue.size() != 1) {
			throw new IllegalArgumentException("Invalid expression");
		}

		return new SyntaxTreeImplementation(outputQueue.get(0));
	}

	private SyntaxTree.Node createNode(Token token) {
		return new SyntaxTreeImplementation.NodeImplementation(token);
	}

	private void handleOperator(Token operator) {
		while (!operatorStack.isEmpty() &&
				canApplyOperator(operatorStack.peek(), operator)) {
			processOperator(operatorStack.pop());
		}
		operatorStack.push(operator);
	}

	private boolean canApplyOperator(Token top, Token current) {
		if (top.getType() == TokenType.OPEN_PARENTHESIS) {
			return false;
		}
		return (top.getAssociativity() == Associativity.LEFT &&
				top.getPrecedence().value() >= current.getPrecedence().value()) ||
				(top.getAssociativity() == Associativity.RIGHT &&
						top.getPrecedence().value() > current.getPrecedence().value());
	}

	private void processOperator(Token operator) {
		SyntaxTree.Node right = outputQueue.remove(outputQueue.size() - 1);
		SyntaxTree.Node left = outputQueue.remove(outputQueue.size() - 1);

		SyntaxTree.Node node = createNode(operator);
		node.setLeft(left);
		node.setRight(right);

		outputQueue.add(node);
	}

	private void processOperatorsUntilLeftParen() {
		while (!operatorStack.isEmpty() && operatorStack.peek().getType() != TokenType.OPEN_PARENTHESIS) {
			processOperator(operatorStack.pop());
		}
		operatorStack.pop(); // Remove the left parenthesis
	}

	private void processRemainingOperators() {
		while (!operatorStack.isEmpty()) {
			processOperator(operatorStack.pop());
		}
	}
}
