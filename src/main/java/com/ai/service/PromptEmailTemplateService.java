package com.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class PromptEmailTemplateService {

	private ChatClient chatClient;

	@Value("classpath:/promptTemplates/promptEmailTemplate.st")
	private Resource promptTemplate;

	PromptEmailTemplateService(@Qualifier("ollama") ChatClient chatClientOllama) {
		this.chatClient = chatClientOllama;
	}

	public String emailTemplate(String customerName, String customerMessage) {

		return chatClient
				.prompt().system("""
						You are a propessional customer Support assistent which helps drafting email
						response to improve the productivity of customer support team.
						""").user(promptTempSpec -> promptTempSpec.text(promptTemplate)
						.param("customerName", customerName).param("customerMessage", customerMessage))
				.call().content();

	}
}
