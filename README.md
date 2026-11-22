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

## Spring AI Series — Day 4: Advisors API

Today’s focus is Spring AI’s **Advisors API**: reusable components that inspect, transform, or enrich a request before it reaches the model and can process the response on its way back. Advisors let an application add common AI patterns without embedding that logic in every controller or service.

### How the advisor chain works

The `ChatClient` creates a request from the prompt and invokes registered advisors in order. Advisors can modify the request or stop the chain; the final step sends the request to the chat model. The response then passes back through the advisors in reverse order. Lower `getOrder()` values run earlier for request processing and later for response processing.

Spring AI has separate `CallAdvisor` and `StreamAdvisor` contracts. `BaseAdvisor` provides before/after hooks for advisors that should support both synchronous calls and streaming. Keep advisors focused, and use the shared advisor context when one advisor needs to pass state to another.

### Implementing RE2 as a custom advisor

This project registers [`ReReadingAdvisor`](./src/main/java/com/example/springaizerotohero/advisor/ReReadingAdvisor.java) as a default advisor in [`ChatClientConfig`](./src/main/java/com/example/springaizerotohero/config/ChatClientConfig.java). Before the model call, it repeats the question using the RE2 prompt pattern:

```text
What does a Spring AI advisor do?
Read the question again: What does a Spring AI advisor do?
```

The custom advisor implements `BaseAdvisor`, updates the user message in `before()`, leaves the response unchanged in `after()`, and sets its order to `0`. Register it with `defaultAdvisors()` so it participates in each call made by this `ChatClient`. This is a learning example; remove or change it if you do not want query repetition in normal chat requests.

### Built-in advisors

Spring AI also provides reusable advisors for common patterns:

- **Chat memory advisors**, such as `MessageChatMemoryAdvisor`, add conversation history to prompts. Pass a conversation ID on each request to keep conversations separate.
- **Question-answer and retrieval advisors**, such as `QuestionAnswerAdvisor` and `RetrievalAugmentationAdvisor`, retrieve relevant documents from a vector store and add context for RAG. These require a configured vector store and are not enabled in this Ollama starter.
- **`SafeGuardAdvisor`** can help guard model output, and **`ToolCallingAdvisor`** supports tool execution as part of the advisor chain.

Register applicable advisors at `ChatClient` build time with `defaultAdvisors()` or add request-specific advisors through the fluent API. For streaming, an advisor must correctly implement `StreamAdvisor`; use `BaseAdvisor` when the same before/after behavior is suitable for both streaming and non-streaming flows.

The [`ReReadingAdvisorTest`](./src/test/java/com/example/springaizerotohero/advisor/ReReadingAdvisorTest.java) verifies the transformed prompt without requiring Ollama to be running.

## Spring AI Series — Day 5: Recursive Advisors

Today’s focus is **recursive advisors**: advisors that call the downstream advisor chain more than once during a single request. A recursive advisor can retry a model call with a corrected prompt, repeat tool execution until no further tools are needed, or evaluate and refine an answer. `CallAdvisorChain.copy(this)` creates a sub-chain containing the advisors after the current advisor; calling that sub-chain again avoids rerunning advisors that already ran before it.

Recursive advisors are useful when a request needs multiple model calls, such as a tool loop or an iterative refinement. This project covers schema validation and the built-in self-correction advisor in the Day 7 section.

### Other recursive patterns and usage accounting

- **`ToolCallingAdvisor`** manages the model/tool loop and is auto-registered by `ChatClient` when tools are present. This project does not register any tools yet, so the chat endpoints do not trigger tool execution.
- **Custom recursive advisors** should use `CallAdvisorChain.copy(this)` to repeat only the downstream part of the advisor chain, preserving ordering and letting downstream advisors observe each iteration.
- Every recursive model call can consume tokens. A custom advisor must accumulate usage across rounds and apply the cumulative total to its final response; Spring AI provides `UsageAccumulator` for this purpose. Built-in advisors should be preferred when they cover the required pattern.

See the [Recursive Advisors guide](https://docs.spring.io/spring-ai/reference/api/advisors-recursive.html) for the full details on recursive chains, tool loops, validation retries, and token usage.

## Spring AI Series — Day 6: Prompts

Today’s focus is the Prompt API: how Spring AI represents instructions and conversation context as messages, and how reusable templates replace placeholders at runtime.

### Prompt structure and message roles

A `Prompt` is a collection of `Message` objects plus optional model options. Spring AI maps roles to `MessageType`:

- `SYSTEM`: guides behavior and response style.
- `USER`: contains the user's question or command.
- `ASSISTANT`: represents model output and may contain tool-call requests.
- `TOOL`: carries tool/function output back into the conversation.

Spring AI also provides convenience methods such as `getUserMessage()`, `getSystemMessage()`, `getUserMessages()`, and `getSystemMessages()` for inspecting a prompt. The project demonstrates explicit role construction through [`PromptService`](./src/main/java/com/example/springaizerotohero/service/PromptService.java).

Try the roles endpoint with Ollama running:

```bash
curl -X POST http://localhost:8080/api/chat/roles \
  -H "Content-Type: application/json" \
  -d '{"message":"Explain PromptTemplate briefly","name":"SpringAI Coach","voice":"a concise teacher"}'
```

The service builds a `SystemMessage` and `UserMessage` and passes both to `ChatClient`.

### PromptTemplate

`PromptTemplate` replaces placeholders in prompt text. The default renderer uses `{}` syntax:

```java
PromptTemplate template = PromptTemplate.builder()
        .template("Tell me a {adjective} joke about {topic}")
        .build();

String prompt = template.render(Map.of(
        "adjective", "clever",
        "topic", "Spring AI"
));
```

Call `/api/chat/template`:

```bash
curl -X POST http://localhost:8080/api/chat/template \
  -H "Content-Type: application/json" \
  -d '{"message":"unused","adjective":"clever","topic":"Spring AI"}'
```

For JSON-heavy prompts, a custom `TemplateRenderer` can use delimiters such as `<` and `>` instead of `{}`. Spring AI's `NoOpTemplateRenderer` is available when no placeholder substitution is desired.

### Prompt templates loaded from resources

The project includes [`system-message.st`](./src/main/resources/prompts/system-message.st), loaded with Spring's `Resource` abstraction. `SystemPromptTemplate` fills `name` and `voice` placeholders and creates a system-role message:

```bash
curl -X POST http://localhost:8080/api/chat/resource-template \
  -H "Content-Type: application/json" \
  -d '{"message":"Explain message roles","name":"SpringAI Mentor","voice":"a patient teacher"}'
```

### Prompt engineering and tokens

Effective prompts distinguish four concerns: clear instructions, relevant external context, the user input, and the expected output format. Zero-shot, few-shot, chain-of-thought, and ReAct techniques are useful approaches, but should be selected only when they fit the task.

Prompts are converted into tokens. Input and output tokens may affect billing and model context-window limits. Keep prompts focused and avoid exceeding the selected model's context window. Models expose response metadata such as token usage; the application should account for it when monitoring cost and cumulative usage across recursive flows.

## Spring AI Series — Day 7: Schema Validation and Self-Correction

Structured output asks the model to return data matching a target Java type, but the model can still return malformed JSON, omit a field, or include prose. Mapping alone is not a guarantee that the response conforms to the expected schema.

The `/api/chat/structured` endpoint maps the response to [`StructuredAnswer`](./src/main/java/com/example/springaizerotohero/dto/StructuredAnswer.java). In [`ChatService`](./src/main/java/com/example/springaizerotohero/service/ChatService.java), the `validateSchema()` option enables Spring AI's `StructuredOutputValidationAdvisor` for this call:

```java
.call()
.entity(StructuredAnswer.class, spec -> spec.validateSchema());
```

Spring AI derives a JSON schema from the output type, validates the completed response, and returns the typed object when it passes. If validation fails, it adds the validation error to the prompt and asks the model to correct its response. The default allows up to three attempts. Streaming is not supported with this validation loop because the advisor needs the complete response before it can validate it.

Try it with Ollama running:

```bash
curl -X POST http://localhost:8080/api/chat/structured \
  -H "Content-Type: application/json" \
  -d '{"message":"Explain what a Spring AI advisor does."}'
```

Use the auto-configured `validateSchema()` behavior for the default retry policy. For a custom policy or a schema supplied by the application, register an explicit advisor on the relevant `ChatClient`; an explicitly registered validation advisor replaces the auto-registered one:

```java
var validationAdvisor = StructuredOutputValidationAdvisor.builder()
        .outputType(StructuredAnswer.class)
        .maxRepeatAttempts(5)
        .build();

ChatClient chatClient = ChatClient.builder(chatModel)
        .defaultAdvisors(validationAdvisor)
        .build();
```

Use `outputJsonSchema(mySchema)` instead of `outputType(...)` when providing a schema string; those options are mutually exclusive. Validation uses JSON Schema Draft 2020-12. Provider-native structured output can complement this response-side validation:

```java
.entity(StructuredAnswer.class, spec -> spec
        .useProviderStructuredOutput()
        .validateSchema());
```

Provider-native output constrains the request where supported; schema validation remains a response-side check and can correct provider edge cases. The original `/api/chat` free-form endpoint is unchanged.

## Spring AI Series — Day 8: Chat Response Metadata and Usage

Today’s focus is reading provider metadata from a `ChatResponse`: response ID, model name, token usage, prompt-cache metrics, and rate-limit information. The project keeps the existing `POST /api/chat` response unchanged and exposes these details through `POST /api/chat/metadata`.

```bash
curl -X POST http://localhost:8080/api/chat/metadata \
  -H "Content-Type: application/json" \
  -d '{"message":"Explain Spring AI in one sentence."}'
```

The response includes the answer, response ID and model, plus `usage` and `rateLimit` objects when the provider supplies those values. Usage contains prompt, completion, and total tokens, as well as unified cache-read and cache-write token counts. Rate-limit reset values are returned as durations. If a provider does not report usage or rate limits, the corresponding object is `null`; an `EmptyRateLimit` is treated as unavailable instead of reporting its zero-valued accessors as a real limit.

The endpoint reads the full `ChatResponse` from `ChatClient` and maps stable, provider-neutral fields into the API response. Provider-specific usage details are intentionally not serialized; use `Usage.getNativeUsage()` inside application code when a provider-specific integration needs them. Availability depends on the selected model implementation. The sample uses Ollama, which may omit metrics that hosted providers expose.

For multi-step calls such as tool loops or validation retries, use the cumulative usage reported by the final response. Custom recursive advisors must aggregate usage across iterations (for example with Spring AI's `UsageAccumulator`) rather than reporting only the last model call.

## Get started

The [concepts section](https://docs.spring.io/spring-ai/reference/concepts.html) provides a high-level overview of AI concepts and how they are represented in Spring AI.

Continue with the [usage handling guide](https://docs.spring.io/spring-ai/reference/api/usage-handling.html), the [Recursive Advisors guide](https://docs.spring.io/spring-ai/reference/api/advisors-recursive.html), and the [Advisors guide](https://docs.spring.io/spring-ai/reference/api/advisors.html), then use the [Spring AI Getting Started guide](https://docs.spring.io/spring-ai/reference/getting-started.html) to explore more components and common use cases.
