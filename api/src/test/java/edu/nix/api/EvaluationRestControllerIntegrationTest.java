package edu.nix.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nix.dto.EvaluationRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EvaluationRestControllerIntegrationTest {
	private @Autowired MockMvc mockMvc;
	private @Autowired ObjectMapper objectMapper;

	@CsvSource(value = {
			"2+2, 4.0",
			"2-2, 0.0",
			"2*2, 4.0",
			"2/2, 1.0",
			"2+2+2, 6.0",
			"2+2+4, 8.0",
			"2+(2*4), 10"
	})
	public @ParameterizedTest void assureEvaluationEndpointReturnsCorrectResult(final String expression, final double expectedResult) throws Exception {
		assertThat(mockMvc)
				.isNotNull();

		mockMvc.perform(post("/evaluate")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(new EvaluationRequest(expression))))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.value").value(expectedResult));
	}
}
