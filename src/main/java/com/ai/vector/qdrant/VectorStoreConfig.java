package com.ai.vector.qdrant;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.qdrant.client.QdrantClient;

@Configuration
public class VectorStoreConfig {
 
    @Bean("supportVectorStore")
    @Primary
    public VectorStore supportVectorStore(
            QdrantClient qdrantClient,
            @Qualifier("openAiEmbeddingModel")
            EmbeddingModel embeddingModel) {
        return QdrantVectorStore.builder(qdrantClient, embeddingModel)
                .collectionName("mypdf-data")
                .initializeSchema(true)
                .build();
    }
}