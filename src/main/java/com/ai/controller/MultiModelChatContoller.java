package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.MultiModelChatService;

@RestController
@RequestMapping("/api")
public class MultiModelChatContoller {

	@Autowired
	private MultiModelChatService multiModelChatService;

	@GetMapping("/chat/ollama")
	public String chat(@RequestParam("message") String prompt) {
		System.out.println("------------=====================Calling ollama model controller");
		return multiModelChatService.chatOllama(prompt);
	}

	@GetMapping("/chat/ollama/default")
	public String chatDefault(@RequestParam("message") String prompt) {
		System.out.println("------------=====================Calling ollama model default controller");
		return multiModelChatService.chatOllamaDefault(prompt);
	}

	@GetMapping("/chat/open/default")
	public String chatOpenDefault(@RequestParam("message") String prompt) {
		System.out.println("------------=====================Calling ollama model default controller");
		return multiModelChatService.chatOpenAIDefault(prompt);
	}

	@GetMapping("/chat/openai")
	public String askRole(@RequestParam("message") String prompt) {
		System.out.println("------------=====================calling openai model controller");
		return multiModelChatService.chatOpenAI(prompt);
	}
}
