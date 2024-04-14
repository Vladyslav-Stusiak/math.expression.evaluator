package edu.nix.math.expression.lexer.domain;

public class LexicalAnalysisException extends RuntimeException {
	public LexicalAnalysisException(final String message) {
		super(message);
	}

	public LexicalAnalysisException(final String message, final Throwable cause) {
		super(message, cause);
	}

	@Override
	public String toString() {
		return "LexicalAnalysisException{} " + super.toString();
	}
}
