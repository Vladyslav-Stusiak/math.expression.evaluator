package edu.nix.math.expression.translator;

import edu.nix.math.expression.lexer.domain.Associativity;
import edu.nix.math.expression.lexer.domain.Precedence;
import edu.nix.math.expression.lexer.domain.TokenType;
import edu.nix.math.expression.lexer.domain.TypeAwareToken;
import edu.nix.math.expression.parser.domain.SyntaxTree;
import edu.nix.math.expression.parser.domain.SyntaxTreeImplementation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class TranslatorTest {
	private final Translator translator = new DepthFirstTranslator();

	public static Stream<Arguments> configureMockSyntaxTree() {
		Mockito.lenient();

		final SyntaxTree additionSyntaxTree = Mockito.mock(SyntaxTree.class);
		final SyntaxTree.Node additionSyntaxTreeNode = Mockito.mock(SyntaxTree.Node.class);
		when(additionSyntaxTreeNode.getToken())
				.thenReturn(new TypeAwareToken(TokenType.OPERATOR_PLUS, "+", Associativity.LEFT, Precedence.of(5)));
		when(additionSyntaxTreeNode.getLeft())
				.thenReturn(Optional.of(new SyntaxTreeImplementation.NodeImplementation(new TypeAwareToken(TokenType.NUMBER,
						"1", Associativity.LEFT, Precedence.of(0)))));
		when(additionSyntaxTreeNode.getRight())
				.thenReturn(Optional.of(new SyntaxTreeImplementation.NodeImplementation(new TypeAwareToken(TokenType.NUMBER,
						"2", Associativity.LEFT, Precedence.of(0)))));
		final var rootAdditionSyntaxTreeNode = new SyntaxTreeImplementation.NodeImplementation(
				new TypeAwareToken(TokenType.UNKNOWN, "", Associativity.NONE, Precedence.of(0)));
		rootAdditionSyntaxTreeNode.setLeft(additionSyntaxTreeNode);
		when(additionSyntaxTree.getRoot())
				.thenReturn(rootAdditionSyntaxTreeNode);

		final SyntaxTree subtractionSyntaxTree = Mockito.mock(SyntaxTree.class);
		final SyntaxTree.Node subtractionSyntaxTreeNode = Mockito.mock(SyntaxTree.Node.class);
		when(subtractionSyntaxTreeNode.getToken())
				.thenReturn(new TypeAwareToken(TokenType.OPERATOR_MINUS, "-", Associativity.LEFT, Precedence.of(5)));
		when(subtractionSyntaxTreeNode.getLeft())
				.thenReturn(Optional.of(new SyntaxTreeImplementation.NodeImplementation(new TypeAwareToken(TokenType.NUMBER,
						"1", Associativity.LEFT, Precedence.of(0)))));
		when(subtractionSyntaxTreeNode.getRight())
				.thenReturn(Optional.of(new SyntaxTreeImplementation.NodeImplementation(new TypeAwareToken(TokenType.NUMBER,
						"2", Associativity.LEFT, Precedence.of(0)))));
		final var rootSubtractionSyntaxTreeNode = new SyntaxTreeImplementation.NodeImplementation(
				new TypeAwareToken(TokenType.UNKNOWN, "", Associativity.NONE, Precedence.of(0)));
		rootSubtractionSyntaxTreeNode.setLeft(subtractionSyntaxTreeNode);
		when(subtractionSyntaxTree.getRoot())
				.thenReturn(rootSubtractionSyntaxTreeNode);


		final SyntaxTree multiplicationSyntaxTree = Mockito.mock(SyntaxTree.class);
		final SyntaxTree.Node multiplicationSyntaxTreeNode = Mockito.mock(SyntaxTree.Node.class);
		when(multiplicationSyntaxTreeNode.getToken())
				.thenReturn(new TypeAwareToken(TokenType.OPERATOR_MULTIPLY, "*", Associativity.LEFT, Precedence.of(5)));
		when(multiplicationSyntaxTreeNode.getLeft())
				.thenReturn(Optional.of(new SyntaxTreeImplementation.NodeImplementation(new TypeAwareToken(TokenType.NUMBER,
						"1", Associativity.LEFT, Precedence.of(0)))));
		when(multiplicationSyntaxTreeNode.getRight())
				.thenReturn(Optional.of(new SyntaxTreeImplementation.NodeImplementation(new TypeAwareToken(TokenType.NUMBER,
						"2", Associativity.LEFT, Precedence.of(0)))));
		final var rootMultiplicationSyntaxTreeNode = new SyntaxTreeImplementation.NodeImplementation(
				new TypeAwareToken(TokenType.UNKNOWN, "", Associativity.NONE, Precedence.of(0)));
		rootMultiplicationSyntaxTreeNode.setLeft(multiplicationSyntaxTreeNode);
		when(multiplicationSyntaxTree.getRoot())
				.thenReturn(rootMultiplicationSyntaxTreeNode);


		return Stream.of(Arguments.of(additionSyntaxTree, 3L),
				Arguments.of(subtractionSyntaxTree, -1L),
				Arguments.of(multiplicationSyntaxTree, 2L));
	}

	@MethodSource("configureMockSyntaxTree")
	public @ParameterizedTest void assureTranslatorEvaluatesSyntaxTreeCorrectly(final SyntaxTree syntaxTree, final long expectedResult) {
		final var evaluationResult = translator.translate(syntaxTree);
		assertThat(evaluationResult)
				.isEqualTo(expectedResult);
	}
}
