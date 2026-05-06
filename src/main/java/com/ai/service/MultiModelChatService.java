package com.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MultiModelChatService {
	private ChatClient chatClientOpenAI;
	private ChatClient chatClientOllama;
	private ChatClient chatClientOllamaDefault;
	private ChatClient chatClientOpenDefault;

	public MultiModelChatService(@Qualifier("openai") ChatClient chatClientOpenAI,
			@Qualifier("ollama") ChatClient chatClientOllama,
			@Qualifier("defaultollama") ChatClient chatClientOllamaDefault,
			@Qualifier("defaultopen") ChatClient chatClientOpenDefault) {
		this.chatClientOpenAI = chatClientOpenAI;
		this.chatClientOllama = chatClientOllama;
		this.chatClientOllamaDefault = chatClientOllamaDefault;
		this.chatClientOpenDefault = chatClientOpenDefault;
	}

	public String chatOllama(String prompt) {
		System.out.println("------------=====================Calling ollama model service");
		return chatClientOllama.prompt().system("""
				You are a Java Spring Boot microservices expert.

				Rules:
				1. Answer ONLY questions related to Java, Spring Boot, and microservices.
				2. If the question is NOT related, reply exactly:
				"I can only help with Java Spring Boot microservices questions."
				3. Keep answers short and practical (max 5 bullet points).
				""").user(prompt).call().content();
	}

	public String chatOllamaDefault(String prompt) {
		System.out.println("------------=====================Calling ollama model default service");
		return chatClientOllamaDefault.prompt().user(prompt).call().content();
	}

	public String chatOpenAI(String prompt) {
		System.out.println("------------=====================calling openai model service");
		return chatClientOpenAI.prompt().system("""
				You are a Java Spring Boot microservices expert.

				Rules:
				1. Answer ONLY questions related to Java, Spring Boot, and microservices.
				2. If the question is NOT related, reply exactly:
				"I can only help with Java Spring Boot microservices questions."
				3. Keep answers short and practical (max 5 bullet points).
				""").user(prompt).call().content();
	}

	public String chatOpenAIDefault(String prompt) {
		System.out.println("------------=====================Calling ollama model default service");
		return chatClientOpenDefault.prompt().user(prompt).call().content();
	}

}
