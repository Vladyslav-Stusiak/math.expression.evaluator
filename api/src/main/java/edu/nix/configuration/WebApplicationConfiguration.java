package edu.nix.configuration;

import edu.nix.math.expression.lexer.DefaultLexicalAnalyzer;
import edu.nix.math.expression.lexer.LexicalAnalyzer;
import edu.nix.math.expression.lexer.configuration.LanguageConfiguration;
import edu.nix.math.expression.parser.ShuntingYardSyntaxAnalyzer;
import edu.nix.math.expression.parser.SyntaxAnalyzer;
import edu.nix.math.expression.translator.DepthFirstTranslator;
import edu.nix.math.expression.translator.Translator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Optional;

@Configuration
public class WebApplicationConfiguration {
	@Bean
	public LexicalAnalyzer lexicalAnalyzer()
			throws IOException {
		final var classLoader = WebApplicationConfiguration.class.getClassLoader();
		final var configurationStream = Optional.of(classLoader)
				.map(loader -> loader.getResourceAsStream("mathematical_expressions_language_definition_dictionary.json"))
				.orElseThrow();
		return new DefaultLexicalAnalyzer(LanguageConfiguration.of(configurationStream));
	}

	@Bean
	public SyntaxAnalyzer syntaxAnalyzer() {
		return new ShuntingYardSyntaxAnalyzer();
	}

	@Bean
	public Translator translator() {
		return new DepthFirstTranslator();
	}
}
