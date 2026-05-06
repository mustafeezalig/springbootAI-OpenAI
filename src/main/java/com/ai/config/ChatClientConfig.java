package com.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
	@Bean("openai")
	public ChatClient openAiChatClient(OpenAiChatModel chatModel) {
		return ChatClient.create(chatModel);
	}

	@Bean("ollama")
	public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
		ChatClient.Builder chatClientBulder = ChatClient.builder(ollamaChatModel);
		return chatClientBulder.build();
	}

	@Bean("defaultollama")
	public ChatClient ollamaChatClientDefault(OllamaChatModel model) {
		return ChatClient.builder(model).defaultSystem("""
				    You are an IT Support Assistant specialized in password reset issues.
				    Rules:
				    1. Help users reset passwords for email, VPN, Windows, applications.
				    2. Provide step-by-step solution.
				    3. Never ask for passwords or OTP.
				    4. Keep answer short.
				    5. If unrelated, say:
				       "I can only assist with password reset related issues."
				""").defaultUser("How can you help me ?").build();
	}
	
	@Bean("defaultopen")
	public ChatClient openChatClientDefault(OpenAiChatModel model) {
		return ChatClient.builder(model).defaultSystem("""
				    You are an IT Support Assistant specialized in password reset issues.
				    Rules:
				    1. Help users reset passwords for email, VPN, Windows, applications.
				    2. Provide step-by-step solution.
				    3. Never ask for passwords or OTP.
				    4. Keep answer short.
				    5. If unrelated, say:
				       "I can only assist with password reset related issues."
				""").defaultUser("How can you help me ?").build();
	}
}
