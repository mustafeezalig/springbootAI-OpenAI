package com.ai.config;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ai.ollama.api.OllamaChatOptions;

import com.ai.controller.TokenUsageAuditAdivisor;

@Configuration
public class ChatClientConfig {
 
	@Primary
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
		return ChatClient.builder(model)
				.defaultOptions(OllamaChatOptions.builder().model("phi3").temperature(0.7).topP(0.9).maxTokens(182))
				.defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdivisor())).defaultSystem("""
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

	//@Primary
	
	@Bean
	public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
		
		return MessageWindowChatMemory.builder().maxMessages(10).chatMemoryRepository(jdbcChatMemoryRepository).build();
	}
	
	@Bean("customChatMemory")
	public ChatClient chatMemoryClient(OpenAiChatModel chatModel, ChatMemory chatMemory,RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {

		Advisor advisorChatMemory = MessageChatMemoryAdvisor.builder(chatMemory).build();

		return ChatClient.builder(chatModel).defaultAdvisors(List.of(new SimpleLoggerAdvisor(), advisorChatMemory,retrievalAugmentationAdvisor))
				.build();
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
	
	//To use this  comment out similarContext and system from controller
	@Bean
	public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectoreStore) {
		return RetrievalAugmentationAdvisor.builder().documentRetriever(VectorStoreDocumentRetriever.builder()
				.vectorStore(vectoreStore).topK(3).similarityThreshold(0.3).build()).build();
	}
}
