package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.PromptStuffingTemplateService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class PromptStuffingTemplateController {

	@Autowired
	private PromptStuffingTemplateService promptStuffingTemplateService;

	@GetMapping("/prompt/stuff")
	public String stuffHRPoliciesTemplate(@RequestParam("message") String message) {
       System.out.println("Calling prompt stuff controller");
		return promptStuffingTemplateService.promptStuffingTemplate(message);

	}
	
	@GetMapping("/prompt/stream")
	public Flux<String> stuffHRPoliciesStreamTemplate(@RequestParam("message") String message) {
       System.out.println("Calling prompt stuff controller");
		return promptStuffingTemplateService.promptStuffingStreamTemplate(message);

	}


}
