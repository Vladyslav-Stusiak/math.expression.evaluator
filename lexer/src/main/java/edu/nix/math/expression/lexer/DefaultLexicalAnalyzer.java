package edu.nix.math.expression.lexer;

import edu.nix.math.expression.lexer.configuration.LanguageConfiguration;
import edu.nix.math.expression.lexer.domain.Expression;
import edu.nix.math.expression.lexer.domain.LexicalAnalysisException;
import edu.nix.math.expression.lexer.domain.Token;
import edu.nix.math.expression.lexer.domain.TypeAwareToken;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public final class DefaultLexicalAnalyzer implements LexicalAnalyzer {
	private final Function<String, Token> tokenMapper;

	public DefaultLexicalAnalyzer(final LanguageConfiguration languageConfiguration) {
		this.tokenMapper = (token) -> stream(languageConfiguration.lexemes())
				.filter(lexeme -> lexeme.regex().asPredicate().test(token))
				.findFirst()
				.map(lexeme -> new TypeAwareToken(lexeme.type(), token, lexeme.associativity(), lexeme.precedence()))
				.orElseThrow(() -> new LexicalAnalysisException("Unexpected token: " + token));
	}

	@Override
	public Token[] tokenize(final Expression expression) {
		return tokenizeInternal(expression)
				.map(tokenMapper)
				.toArray(Token[]::new);
	}

	private Stream<String> tokenizeInternal(final Expression expression) {
		return expression.expression()
				.chars()
				.mapToObj(character -> String.valueOf((char) character));
	}
}
