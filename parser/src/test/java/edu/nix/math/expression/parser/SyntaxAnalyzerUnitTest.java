package edu.nix.math.expression.parser;

import edu.nix.math.expression.lexer.domain.*;
import edu.nix.math.expression.parser.domain.SyntaxTree;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({MockitoExtension.class})
class SyntaxAnalyzerUnitTest {
	private final SyntaxAnalyzer syntaxAnalyzer = new ShuntingYardSyntaxAnalyzer();

	public @Test void assureAnalyzerBuildCorrectTree() {
		assertThat(syntaxAnalyzer)
				.isNotNull();

		final var syntaxTree = syntaxAnalyzer.analyze(buildTokens());
		assertSyntaxTreeIsCorrect(syntaxTree);
	}

	private static void assertSyntaxTreeIsCorrect(final SyntaxTree syntaxTree) {
		assertThat(syntaxTree)
				.isNotNull();
		assertThat(syntaxTree.getRoot())
				.extracting(node -> node.getToken().getType())
				.isEqualTo(TokenType.OPERATOR_PLUS);
		assertThat(syntaxTree.getRoot())
				.extracting(SyntaxTree.Node::getLeft)
				.extracting(Optional::get)
				.matches(left -> left.getToken().getType() == TokenType.NUMBER)
				.matches(left -> left.getToken().getRawValue().equals("1"));
		assertThat(syntaxTree.getRoot())
				.extracting(SyntaxTree.Node::getRight)
				.extracting(Optional::get)
				.matches(right -> right.getToken().getType() == TokenType.OPERATOR_MINUS);
		assertThat(syntaxTree.getRoot())
				.extracting(SyntaxTree.Node::getRight)
				.extracting(Optional::get)
				.extracting(SyntaxTree.Node::getLeft)
				.extracting(Optional::get)
				.matches(left -> left.getToken().getType() == TokenType.NUMBER)
				.matches(left -> left.getToken().getRawValue().equals("2"));
		assertThat(syntaxTree.getRoot())
				.extracting(SyntaxTree.Node::getRight)
				.extracting(Optional::get)
				.extracting(SyntaxTree.Node::getRight)
				.extracting(Optional::get)
				.matches(right -> right.getToken().getType() == TokenType.NUMBER)
				.matches(right -> right.getToken().getRawValue().equals("1"));
	}

	private static Token[] buildTokens() {
		return new Token[]{
				new TypeAwareToken(TokenType.NUMBER, "1", Associativity.NONE, Precedence.of(0)),
				new TypeAwareToken(TokenType.OPERATOR_PLUS, "+", Associativity.LEFT, Precedence.of(5)),
				new TypeAwareToken(TokenType.OPEN_PARENTHESIS, "(", Associativity.NONE, Precedence.of(15)),
				new TypeAwareToken(TokenType.NUMBER, "2", Associativity.NONE, Precedence.of(0)),
				new TypeAwareToken(TokenType.OPERATOR_MINUS, "-", Associativity.LEFT, Precedence.of(5)),
				new TypeAwareToken(TokenType.NUMBER, "1", Associativity.NONE, Precedence.of(0)),
				new TypeAwareToken(TokenType.CLOSE_PARENTHESIS, ")", Associativity.NONE, Precedence.of(15))
		};
	}
}