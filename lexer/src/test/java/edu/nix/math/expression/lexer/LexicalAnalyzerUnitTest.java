package edu.nix.math.expression.lexer;


import edu.nix.math.expression.lexer.configuration.LanguageConfiguration;
import edu.nix.math.expression.lexer.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class LexicalAnalyzerUnitTest {
	private LexicalAnalyzer lexicalAnalyzer;
	private @Mock Expression mockExpression;
	private final LanguageConfiguration mockLanguageConfiguration;

	{
		try {
			mockLanguageConfiguration = LanguageConfiguration.of(LexicalAnalyzerUnitTest.class.getClassLoader()
					.getResourceAsStream("mathematical_expressions_language_definition_dictionary.json"));
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@BeforeEach
	public void setUpLexer() {
		lexicalAnalyzer = new DefaultLexicalAnalyzer(mockLanguageConfiguration);
	}

	@MethodSource("provideExpressionsWithExpectedTokens")
	public @ParameterizedTest void assureExpressionIsTokenized(final String expression, final Token... tokens) {
		when(mockExpression.expression())
				.thenReturn(expression);
		assertThat(lexicalAnalyzer).isNotNull();
		assertThat(lexicalAnalyzer.tokenize(mockExpression))
				.isNotNull()
				.containsExactly(tokens);
	}

	public @Test void assureExceptionIsThrownOnUnexpectedToken() {
		when(mockExpression.expression())
				.thenReturn("a_b+c");
		assertThatThrownBy(() -> lexicalAnalyzer.tokenize(mockExpression))
				.isInstanceOf(LexicalAnalysisException.class)
				.message()
				.startsWith("Unexpected token: ");
	}

	private static Stream<Arguments> provideExpressionsWithExpectedTokens() {
		return Stream.of(Arguments.of("1+2", new Token[]{
						new TypeAwareToken(TokenType.NUMBER, "1", Associativity.NONE, Precedence.of(0)),
						new TypeAwareToken(TokenType.OPERATOR_PLUS, "+", Associativity.LEFT, Precedence.of(5)),
						new TypeAwareToken(TokenType.NUMBER, "2", Associativity.NONE, Precedence.of(0))}),
				Arguments.of("1+2+3", new Token[]{
						new TypeAwareToken(TokenType.NUMBER, "1", Associativity.NONE, Precedence.of(0)),
						new TypeAwareToken(TokenType.OPERATOR_PLUS, "+", Associativity.LEFT, Precedence.of(5)),
						new TypeAwareToken(TokenType.NUMBER, "2", Associativity.NONE, Precedence.of(0)),
						new TypeAwareToken(TokenType.OPERATOR_PLUS, "+", Associativity.LEFT, Precedence.of(5)),
						new TypeAwareToken(TokenType.NUMBER, "3", Associativity.NONE, Precedence.of(0))}),
				Arguments.of("(2+3)", new Token[]{
						new TypeAwareToken(TokenType.OPEN_PARENTHESIS, "(", Associativity.LEFT, Precedence.of(15)),
						new TypeAwareToken(TokenType.NUMBER, "2", Associativity.NONE, Precedence.of(0)),
						new TypeAwareToken(TokenType.OPERATOR_PLUS, "+", Associativity.LEFT, Precedence.of(5)),
						new TypeAwareToken(TokenType.NUMBER, "3", Associativity.NONE, Precedence.of(0)),
						new TypeAwareToken(TokenType.CLOSE_PARENTHESIS, ")", Associativity.LEFT, Precedence.of(15))}));
	}
}
