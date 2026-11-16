# Spring AI

The **Spring AI** project aims to streamline the development of applications that incorporate artificial intelligence functionality without unnecessary complexity.

The project draws inspiration from notable Python projects such as LangChain and LlamaIndex, but Spring AI is not a direct port of those projects. It was founded on the belief that the next wave of Generative AI applications will not be limited to Python developers, but will be ubiquitous across many programming languages.

Spring AI addresses the fundamental challenge of AI integration: **connecting your enterprise data and APIs with AI models.**

Spring AI provides abstractions that serve as the foundation for developing AI applications. These abstractions have multiple implementations, enabling components to be swapped with minimal code changes.

## Features

- **Portable model APIs** for chat, text-to-image, and embedding models, with synchronous and streaming options and access to model-specific features.
- **Support for major [AI model providers](https://docs.spring.io/spring-ai/reference/api/index.html)**, including Anthropic, OpenAI, Microsoft, Amazon, Google, and Ollama. Supported model types include:
  - [Chat completion](https://docs.spring.io/spring-ai/reference/api/chatmodel.html)
  - [Embedding](https://docs.spring.io/spring-ai/reference/api/embeddings.html)
  - [Text to image](https://docs.spring.io/spring-ai/reference/api/imageclient.html)
  - [Audio transcription](https://docs.spring.io/spring-ai/reference/api/audio/transcriptions.html)
  - [Text to speech](https://docs.spring.io/spring-ai/reference/api/audio/speech.html)
  - [Moderation](https://docs.spring.io/spring-ai/reference/api/moderation.html)
  - [Structured outputs](https://docs.spring.io/spring-ai/reference/api/structured-output.html), mapping AI model output to POJOs.
- **Support for major [vector database providers](https://docs.spring.io/spring-ai/reference/api/vectordbs.html)**, including Apache Cassandra, Azure Vector Search, Chroma, Elasticsearch, GemFire, MariaDB, Milvus, MongoDB Atlas, Neo4j, OpenSearch, Oracle, PostgreSQL/PGVector, Pinecone, Qdrant, Redis, Typesense, and Weaviate.
- **Portable vector store API**, including a SQL-like metadata filter API.
- [**Tools and function calling**](https://docs.spring.io/spring-ai/reference/api/tools.html), allowing a model to request client-side tools and functions to access real-time information or take action.
- [**Observability**](https://docs.spring.io/spring-ai/reference/observability/index.html) for insights into AI-related operations.
- A document ingestion [**ETL framework**](https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html) for data engineering.
- [**AI model evaluation**](https://docs.spring.io/spring-ai/reference/api/testing.html) utilities to evaluate generated content and help protect against hallucinated responses.
- [**MCP (Model Context Protocol)**](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-overview.html) integration for building AI applications that consume MCP servers or expose Spring-based services to the AI ecosystem.
- Spring Boot auto-configuration and starters for AI models and vector stores.
- [**ChatClient API**](https://docs.spring.io/spring-ai/reference/api/chatclient.html), a fluent API for communicating with AI chat models, idiomatically similar to WebClient and RestClient.
- [**Advisors API**](https://docs.spring.io/spring-ai/reference/api/advisors.html), which encapsulates recurring Generative AI patterns, transforms data sent to and from language models (LLMs), and provides portability across models and use cases.
- Support for [**chat conversation memory**](https://docs.spring.io/spring-ai/reference/api/chatclient.html#_chat_memory) and [**Retrieval Augmented Generation (RAG)**](https://docs.spring.io/spring-ai/reference/api/chatclient.html#_retrieval_augmented_generation).

This feature set lets you implement common use cases, such as “Q&A over your documentation” or “Chat with your documentation.”

## Spring AI Series — Day 2: AI Concepts

Today’s focus is on the core concepts behind AI applications and how Spring AI helps bring them together. See the [Spring AI concepts guide](https://docs.spring.io/spring-ai/reference/concepts.html) for the full explanation.

- **Models** process inputs and produce outputs such as text, images, audio, or embeddings. Different model types suit different tasks.
- **Prompts** provide the instructions and context sent to a model. Chat prompts can contain multiple messages with different roles, such as system instructions and user input. **Prompt templates** make prompts reusable by filling placeholders with application data.
- **Embeddings** represent content as vectors. Comparing vectors helps find semantically similar content, which is useful for semantic search and Retrieval Augmented Generation (RAG).
- **Tokens and context windows** affect how much content a model can process in one request. Hosted model usage may also be billed by token, so applications should manage prompt and response size.
- **Structured output** turns model responses into application-friendly data structures, rather than relying on unvalidated text that merely looks like JSON.
- **Connecting models to application data and APIs** can be done with RAG or tool calling. RAG retrieves relevant content and includes it with a user’s prompt; tool calling lets a model request that the application invoke registered functions or services.
- **Response evaluation** helps assess whether a generated answer is relevant and consistent with the user’s request and supporting context.

These concepts provide the foundation for building Spring AI applications that can work with your data and services—not just generate text.

## Spring AI Series — Day 3: Getting Started

Today’s focus is setting up a Spring AI project and understanding how its dependencies are selected and versioned. Follow the official [Getting Started guide](https://docs.spring.io/spring-ai/reference/getting-started.html) when creating a project.

### 1. Generate a project

Use [Spring Initializr](https://start.spring.io/) to create a Spring Boot project. Select the AI model provider and vector store that match your use case; you do not need to add every Spring AI integration. Spring AI 2.0.x supports Spring Boot 4.0.x and 4.1.x. This repository now includes a Maven-based example using Spring Boot 4.0.0 and Spring AI 2.0.0.

### 2. Use the right artifact repository

For released Spring AI versions, Maven Central is already enabled by default in Maven, so no extra repository configuration is normally required.

Snapshots are development builds, not regular releases. Add the snapshot repositories only when you intentionally need a `-SNAPSHOT` version:

```xml
<repositories>
    <repository>
        <id>spring-snapshots</id>
        <url>https://repo.spring.io/snapshot</url>
        <releases>
            <enabled>false</enabled>
        </releases>
    </repository>
    <repository>
        <id>central-portal-snapshots</id>
        <url>https://central.sonatype.com/repository/maven-snapshots/</url>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

If your Maven `settings.xml` mirrors every repository (`<mirrorOf>*</mirrorOf>`), the mirror can prevent Maven from reaching those snapshot repositories. Exclude them in `mirrorOf`, for example: `<mirrorOf>*,!spring-snapshots,!central-portal-snapshots</mirrorOf>`.

### 3. Manage versions with the Spring AI BOM

The Spring AI Bill of Materials (BOM) keeps the versions of Spring AI modules aligned. Import it once, then omit versions from Spring AI dependencies. The BOM manages Spring AI dependencies; continue using your Spring Boot parent or Spring Boot BOM to manage Spring Boot dependencies.

For Maven, add this to the existing `pom.xml`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>2.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

For Gradle, import the BOM as a platform in the `dependencies` block:

```groovy
dependencies {
    implementation platform("org.springframework.ai:spring-ai-bom:2.0.0")
}
```

### 4. Add only the components you need

After importing the BOM, choose the dependencies for your selected integration. Spring AI documents the required artifacts for [chat models](https://docs.spring.io/spring-ai/reference/api/chatmodel.html), [embeddings](https://docs.spring.io/spring-ai/reference/api/embeddings.html), [image generation](https://docs.spring.io/spring-ai/reference/api/imageclient.html), [audio](https://docs.spring.io/spring-ai/reference/api/audio/transcriptions.html), and [vector databases](https://docs.spring.io/spring-ai/reference/api/vectordbs.html). The BOM supplies their versions; your build file still needs to declare the components your application uses.

**Takeaway:** generate a Spring Boot project with the integrations you need, use Maven Central for releases, and import the Spring AI BOM so component versions stay consistent. Use snapshot repositories only when you deliberately depend on a development build.

### Run this project with Ollama

The project uses the `spring-ai-starter-model-ollama` dependency, managed by the Spring AI BOM in [`pom.xml`](./pom.xml). It demonstrates Spring AI's fluent `ChatClient` API and keeps HTTP handling, AI behavior, and configuration in separate layers:

```text
src/main/java/com/example/springaizerotohero/
├── config/ChatClientConfig.java  # ChatClient bean and default system prompt
├── controller/ChatController.java # HTTP endpoint and request validation
├── dto/ChatRequest.java          # Validated incoming request
├── dto/ChatResponse.java         # JSON response shape
├── service/ChatService.java      # Calls the model through ChatClient
└── SpringAiZeroToHeroApplication.java
```

Spring Boot auto-configures a `ChatClient.Builder` for the Ollama chat model. [`ChatClientConfig.java`](./src/main/java/com/example/springaizerotohero/config/ChatClientConfig.java) builds a reusable `ChatClient` bean and sets a default system message; [`ChatService.java`](./src/main/java/com/example/springaizerotohero/service/ChatService.java) supplies each user message and calls the model:

```java
chatClient.prompt()
        .user(message)
        .call()
        .content();
```

The fluent API can also set system or user prompts per request, fill prompt-template parameters, stream output, return a full `ChatResponse` with model metadata, or map structured output to a Java type. The current example uses the simplest synchronous string response. No hosted-provider API key is needed.

1. Install and start [Ollama](https://ollama.com/), then download the default model:

   ```bash
   ollama pull llama3.2
   ```

2. Start the application:

   ```bash
   mvn spring-boot:run
   ```

3. Send a chat request:

   ```bash
   curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message":"Explain what Spring AI is in one sentence."}'
   ```

   The response is JSON with an `answer` field. The model defaults to `llama3.2`; override it with `OLLAMA_MODEL`. Set `OLLAMA_BASE_URL` if Ollama is running somewhere other than `http://localhost:11434`.

## Get started

The [concepts section](https://docs.spring.io/spring-ai/reference/concepts.html) provides a high-level overview of AI concepts and how they are represented in Spring AI.

Continue with the [Spring AI Getting Started guide](https://docs.spring.io/spring-ai/reference/getting-started.html) to create your first AI application. Subsequent sections explore each component and common use cases with a code-focused approach.
