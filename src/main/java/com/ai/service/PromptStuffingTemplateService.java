package com.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ai.controller.TokenUsageAuditAdivisor;

@Service
public class PromptStuffingTemplateService {

	private ChatClient chatClient;

	@Value("classpath:/promptTemplates/systemPromptTemplate.st")
	private Resource promptTemplate;

	PromptStuffingTemplateService(@Qualifier("defaultollama") ChatClient chatClientOllama) {
		this.chatClient = chatClientOllama;
	}

	public String promptStuffingTemplate(String prompt) {
		System.out.println("Calling prompt stuff service");
		return chatClient
				.prompt()
				//.advisors(new TokenUsageAuditAdivisor()) //not recommended here as its common to all calls
				.system(promptTemplate)
				.user(prompt)
				.call().content();

	}
}
