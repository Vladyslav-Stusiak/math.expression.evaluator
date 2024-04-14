package edu.nix.math.expression.lexer;

import edu.nix.math.expression.lexer.configuration.LanguageConfiguration;
import edu.nix.math.expression.lexer.domain.Expression;
import edu.nix.math.expression.lexer.domain.LexicalAnalysisException;
import edu.nix.math.expression.lexer.domain.Token;
import edu.nix.math.expression.lexer.domain.TypeAwareToken;

import java.util.function.Function;

import static java.util.Arrays.stream;

public final class DefaultLexicalAnalyzer implements LexicalAnalyzer {
	private final Function<Character, Token> tokenMapper;

	public DefaultLexicalAnalyzer(final LanguageConfiguration languageConfiguration) {
		this.tokenMapper = (character) -> stream(languageConfiguration.lexemes())
				.filter(lexeme -> lexeme.regex().asPredicate().test(String.valueOf(character)))
				.findFirst()
				.map(lexeme -> new TypeAwareToken(lexeme.type(), String.valueOf(character)))
				.orElseThrow(() -> new LexicalAnalysisException("Unexpected character: " + character));
	}

	@Override
	public Token[] tokenize(final Expression expression) {
		return expression.expression()
				.chars()
				.mapToObj(character -> tokenMapper.apply((char) character))
				.toArray(Token[]::new);
	}
}
