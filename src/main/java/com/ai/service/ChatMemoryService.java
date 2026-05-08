package com.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID ;

@Service
public class ChatMemoryService {

	private ChatClient chatClient;

	ChatMemoryService(@Qualifier("customChatMemory") ChatClient chatClientMemory) {
		this.chatClient = chatClientMemory;
	}

	public String chatMemoryCall(String message,String username) {
		System.out.println("Calling ChatMemoryService...");
		return chatClient.prompt()
				.advisors(advisorSpec->advisorSpec.param(CONVERSATION_ID, username))
				.user(message)
				.call()
				.content();

	}

}
