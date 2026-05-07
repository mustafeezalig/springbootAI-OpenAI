package com.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.PromptEmailTemplateService;

@RestController
@RequestMapping("/api")
public class PromptEmailTemplateController {

	@Autowired
	private PromptEmailTemplateService promptEmailTemplateService;

	@GetMapping("/email")
	public String emailTemplate(@RequestParam("customerName") String customerName,
			@RequestParam("customerMessage") String customerMessage) {

		return promptEmailTemplateService.emailTemplate(customerName, customerMessage)

	}

}
