package com.ai;

import org.springframework.ai.vectorstore.qdrant.autoconfigure.QdrantVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        QdrantVectorStoreAutoConfiguration.class
    })
public class SpringbootAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAiApplication.class, args);
	}

}
