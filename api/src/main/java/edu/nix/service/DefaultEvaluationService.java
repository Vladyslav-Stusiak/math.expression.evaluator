package edu.nix.service;

import edu.nix.math.expression.lexer.LexicalAnalyzer;
import edu.nix.math.expression.lexer.domain.StringExpression;
import edu.nix.math.expression.parser.SyntaxAnalyzer;
import edu.nix.math.expression.translator.Translator;
import org.springframework.stereotype.Service;

@Service
public class DefaultEvaluationService implements EvaluationService {
	private final LexicalAnalyzer lexicalAnalyzer;
	private final SyntaxAnalyzer syntaxAnalyzer;
	private final Translator translator;

	public DefaultEvaluationService(final LexicalAnalyzer lexicalAnalyzer,
	                                final SyntaxAnalyzer syntaxAnalyzer,
	                                final Translator translator) {
		this.lexicalAnalyzer = lexicalAnalyzer;
		this.syntaxAnalyzer = syntaxAnalyzer;
		this.translator = translator;
	}

	@Override
	public Double evaluate(final String expression) {
		final var tokens = lexicalAnalyzer.tokenize(new StringExpression(expression));
		final var syntaxTree = syntaxAnalyzer.analyze(tokens);
		return translator.translate(syntaxTree);
	}
}
