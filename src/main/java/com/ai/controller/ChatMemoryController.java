package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.ChatMemoryService;
import  static  org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID ;
@RestController
@RequestMapping("/api")
public class ChatMemoryController {

	@Autowired
	private ChatMemoryService chatMemorySerivce;

	@GetMapping("/chat-memory")
	public String stuffHRPoliciesTemplate(@RequestParam("message") String message,@RequestHeader("username") String username) {
		System.out.println("Calling prompt stuff controller");
		return chatMemorySerivce.chatMemoryCall(message,username);

	}

}
