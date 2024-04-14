package edu.nix.math.expression.lexer.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nix.math.expression.lexer.domain.TokenType;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public record LanguageConfiguration(String formalLanguageName, Lexeme[] lexemes) {
	public record Lexeme(TokenType type, Pattern regex) {
		public static @JsonCreator Lexeme of(final @JsonProperty("type") String rawType, final @JsonProperty("regex") Pattern regex) {
			TokenType type;
			try {
				type = TokenType.valueOf(rawType);
			} catch (IllegalArgumentException illegalArgumentException) {
				type = TokenType.UNKNOWN;
			}
			return new Lexeme(type, regex);
		}
	}

	public static LanguageConfiguration of(final InputStream inputStream) throws IOException {
		final var jacksonParser = new ObjectMapper();
		jacksonParser.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return jacksonParser.readValue(inputStream, LanguageConfiguration.class);
	}
}
