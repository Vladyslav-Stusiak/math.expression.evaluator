package edu.nix.math.expression.lexer.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nix.math.expression.lexer.domain.Associativity;
import edu.nix.math.expression.lexer.domain.Precedence;
import edu.nix.math.expression.lexer.domain.TokenType;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public record LanguageConfiguration(String formalLanguageName, Lexeme[] lexemes) {
	public record Lexeme(TokenType type, String regex, Associativity associativity, Precedence precedence) {
		public static @JsonCreator Lexeme of(final @JsonProperty("type") String rawType,
		                                     final @JsonProperty("regex") String regex,
		                                     final @JsonProperty("associativity") Associativity associativity,
		                                     final @JsonProperty("precedence") Precedence precedence) {
			TokenType type;
			try {
				type = TokenType.valueOf(rawType);
			} catch (IllegalArgumentException illegalArgumentException) {
				type = TokenType.UNKNOWN;
			}
			return new Lexeme(type, regex, associativity, precedence);
		}
	}

	public static LanguageConfiguration of(final InputStream inputStream) throws IOException {
		final var jacksonParser = new ObjectMapper();
		jacksonParser.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return jacksonParser.readValue(inputStream, LanguageConfiguration.class);
	}
}
