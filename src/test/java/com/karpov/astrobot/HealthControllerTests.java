package com.karpov.astrobot;

import com.karpov.astrobot.controllers.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
public class HealthControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void healthPageTest() throws Exception {
		this.mockMvc.perform(get("/health")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("OK")));
	}
}
