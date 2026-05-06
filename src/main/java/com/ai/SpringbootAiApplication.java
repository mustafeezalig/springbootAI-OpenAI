package com.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		exclude = {
				org.springframework.ai.model.openai.autoconfigure.OpenAiAudioSpeechAutoConfiguration.class
		}
)
public class SpringbootAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAiApplication.class, args);
	}

}
