package edu.nix.math.expression.lexer;

import edu.nix.math.expression.lexer.configuration.LanguageConfiguration;
import edu.nix.math.expression.lexer.domain.Expression;
import edu.nix.math.expression.lexer.domain.LexicalAnalysisException;
import edu.nix.math.expression.lexer.domain.Token;
import edu.nix.math.expression.lexer.domain.TypeAwareToken;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public final class DefaultLexicalAnalyzer implements LexicalAnalyzer {
	private final Function<String, Token> tokenMapper;
	private final LanguageConfiguration languageConfiguration;

	public DefaultLexicalAnalyzer(final LanguageConfiguration languageConfiguration) {
		this.languageConfiguration = languageConfiguration;
		this.tokenMapper = (token) -> stream(languageConfiguration.lexemes())
				.filter(lexeme -> Pattern.compile(lexeme.regex()).asPredicate().test(token))
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
		final var patternBuilder = new StringBuilder();
		for (final LanguageConfiguration.Lexeme lexeme : languageConfiguration.lexemes()) {
			patternBuilder.append(lexeme.regex());
			patternBuilder.append('|');
		}
		final var combinedPattern = Pattern.compile(patternBuilder.append(".").toString());

		final var matcher = combinedPattern.matcher(expression.expression());

		final var tokensBuilder = Stream.<String>builder();
		while (matcher.find()) {
			tokensBuilder.add(matcher.group());
		}

		return tokensBuilder.build();
	}
}
