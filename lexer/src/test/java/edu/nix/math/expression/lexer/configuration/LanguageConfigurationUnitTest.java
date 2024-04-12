package edu.nix.math.expression.lexer.configuration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LanguageConfigurationUnitTest {
	public @Test void assureConfigurationIsLoaded() throws Exception {
		try (final var dictionaryStream = LanguageConfigurationUnitTest.class.getClassLoader()
				.getResourceAsStream("mathematical_expressions_language_definition_dictionary.json")) {
			final var config = LanguageConfiguration.of(dictionaryStream);

			assertThat(config)
					.isNotNull();
		}
	}
}