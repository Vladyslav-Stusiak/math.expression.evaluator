package edu.nix.api;

import edu.nix.dto.EvaluationRequest;
import edu.nix.dto.EvaluationResult;
import edu.nix.service.EvaluationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationRestControllerImplementation
		implements EvaluationRestController {
	private final EvaluationService evaluationService;

	public EvaluationRestControllerImplementation(final EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

	@Override
	public EvaluationResult evaluate(final EvaluationRequest request) {
		return new EvaluationResult(evaluationService.evaluate(request.expression()));
	}
}
