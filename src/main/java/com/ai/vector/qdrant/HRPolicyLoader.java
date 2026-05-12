package com.ai.vector.qdrant;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class HRPolicyLoader {
	private VectorStore vectorStore;

	@Value("classpath:Eazybytes_HR_Policies.pdf")
	Resource policyFile;

	public HRPolicyLoader(VectorStore vectorStore) {
		super();
		this.vectorStore = vectorStore;
	}

	@PostConstruct
	public void pdfLoaderIntoVectorStore() {
		TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(policyFile);
		List<Document> docs = tikaDocumentReader.get();
		TextSplitter textSplitter = TokenTextSplitter.builder().withChunkSize(200).withMaxNumChunks(400).build();
		vectorStore.add(textSplitter.split(docs));
		// vectorStore.add(docs);
	}
}
