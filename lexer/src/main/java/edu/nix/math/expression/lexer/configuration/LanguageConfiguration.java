package edu.nix.math.expression.lexer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nix.math.expression.lexer.domain.TokenType;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public record LanguageConfiguration(String formalLanguageName, Lexeme[] lexemes) {
	public record Lexeme(TokenType type, Pattern regex) { }

	public static LanguageConfiguration of(final InputStream inputStream) throws IOException {
		final var jacksonParser = new ObjectMapper();
		return jacksonParser.readValue(inputStream, LanguageConfiguration.class);
	}
}
