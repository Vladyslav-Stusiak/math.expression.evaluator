package edu.nix.api;

import edu.nix.dto.EvaluationResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/evaluate")
public interface EvaluationRestController {
	@PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	EvaluationResult evaluate(@RequestBody final String expression);
}
