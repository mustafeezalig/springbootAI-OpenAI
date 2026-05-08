package com.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatMemoryService {

	private ChatClient chatClient;

	ChatMemoryService(@Qualifier("customChatMemory") ChatClient chatClientMemory) {
		this.chatClient = chatClientMemory;
	}

	public String chatMemoryCall(String message) {
		System.out.println("Calling ChatMemoryService...");
		return chatClient.prompt().advisors(new SimpleLoggerAdvisor())
				.user(message)
				.call()
				.content();

	}

}
