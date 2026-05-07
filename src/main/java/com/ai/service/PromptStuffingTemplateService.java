package com.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class PromptStuffingTemplateService {

	private ChatClient chatClient;

	@Value("classpath:/promptTemplates/systemPromptTemplate.st")
	private Resource promptTemplate;

	PromptStuffingTemplateService(@Qualifier("ollama") ChatClient chatClientOllama) {
		this.chatClient = chatClientOllama;
	}

	public String promptStuffingTemplate(String prompt) {

		return chatClient
				.prompt().system(promptTemplate).user(prompt)
				.call().content();

	}
}
