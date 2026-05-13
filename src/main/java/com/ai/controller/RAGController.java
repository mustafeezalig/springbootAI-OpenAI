package com.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import java.util.List;
import java.util.stream.Collectors;

//https://github.com/qdrant/qdrant
@RestController
@RequestMapping("/api/rag")
public class RAGController {
	private ChatClient chatClient;
	private ChatClient webSearchChatClient;
	private VectorStore vectorStore;

	@Value("classpath:/promptTemplates/systemPromptRandomDataTemplate.st")
	private Resource promptTemplate;

	@Value("classpath:/promptTemplates/systemPromptTemplateRag.st")
	private Resource hrSystemTemplate;

	public RAGController(@Qualifier("customChatMemory") ChatClient chatClient,
			@Qualifier("webSearchRAGChatClient") ChatClient webSearchChatClient, VectorStore vectorStore) {
		super();
		this.chatClient = chatClient;
		this.vectorStore = vectorStore;
		this.webSearchChatClient = webSearchChatClient;
	}

	@GetMapping("/random/chat")
	public ResponseEntity<String> randomChat(@RequestHeader("username") String username,
			@RequestParam("message") String message) {
		SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).similarityThreshold(.2).build();
		List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
		String similarContext = similarDocs.stream().map(Document::getText)
				.collect(Collectors.joining(System.lineSeparator()));
		String answer = chatClient.prompt()
				.system(promptSystemSpec -> promptSystemSpec.text(promptTemplate).param("documents", similarContext))
				.advisors(a -> a.param(CONVERSATION_ID, username)).user(message).call().content();
		return ResponseEntity.ok(answer);
	}

	@GetMapping("/document/chat")
	public ResponseEntity<String> documentChat(@RequestHeader("username") String username,
			@RequestParam("message") String message) {
		//SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).build();
		//List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
		//String similarContext = similarDocs.stream().map(Document::getText)
				//.collect(Collectors.joining(System.lineSeparator()));
		String answer = chatClient.prompt()
				//.system(promptSystemSpec -> promptSystemSpec.text(hrSystemTemplate).param("documents", similarContext))
				.advisors(a -> a.param(CONVERSATION_ID, username)).user(message).call().content();
		return ResponseEntity.ok(answer);
	}

	@GetMapping("/web-search/chat")
	public ResponseEntity<String> webSearchChat(@RequestHeader("username") String username,
			@RequestParam("message") String message) {
		String answer = webSearchChatClient.prompt().advisors(a -> a.param(CONVERSATION_ID, username)).user(message)
				.call().content();
		return ResponseEntity.ok(answer);
	}

}
