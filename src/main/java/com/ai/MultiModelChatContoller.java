package com.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MultiModelChatContoller {

	private ChatClient chatClientOpenAI;
	private ChatClient chatClientOllama;
	private ChatClient chatClientOllamaDefault;
	private ChatClient chatClientOpenDefault;

	public MultiModelChatContoller(@Qualifier("openai") ChatClient chatClientOpenAI,
			@Qualifier("ollama") ChatClient chatClientOllama,@Qualifier("defaultollama") ChatClient chatClientOllamaDefault,@Qualifier("defaultopen") ChatClient chatClientOpenDefault) {
		this.chatClientOpenAI = chatClientOpenAI;
		this.chatClientOllama = chatClientOllama;
		this.chatClientOllamaDefault = chatClientOllamaDefault;
		this.chatClientOpenDefault = chatClientOpenDefault;
	}

	@GetMapping("/chat/ollama")
	public String chat(@RequestParam("message") String prompt) {
		System.out.println("------------=====================Calling ollama model");
		return chatClientOllama.prompt().system("""
				You are a Java Spring Boot microservices expert.

				Rules:
				1. Answer ONLY questions related to Java, Spring Boot, and microservices.
				2. If the question is NOT related, reply exactly:
				"I can only help with Java Spring Boot microservices questions."
				3. Keep answers short and practical (max 5 bullet points).
				""").user(prompt).call().content();
	}

	@GetMapping("/chat/ollama/default")
	public String chatDefault(@RequestParam("message") String prompt) {
		System.out.println("------------=====================Calling ollama model default");
		return chatClientOllamaDefault.prompt().user(prompt).call().content();
	}
	
	@GetMapping("/chat/open/default")
	public String chatOpenDefault(@RequestParam("message") String prompt) {
		System.out.println("------------=====================Calling ollama model default");
		return chatClientOpenDefault.prompt().user(prompt).call().content();
	}

	@GetMapping("/chat/openai")
	public String askRole(@RequestParam("message") String prompt) {
		System.out.println("------------=====================calling openai model");
		return chatClientOpenAI.prompt().system("""
				You are a Java Spring Boot microservices expert.

				Rules:
				1. Answer ONLY questions related to Java, Spring Boot, and microservices.
				2. If the question is NOT related, reply exactly:
				"I can only help with Java Spring Boot microservices questions."
				3. Keep answers short and practical (max 5 bullet points).
				""").user(prompt).call().content();
	}
}
