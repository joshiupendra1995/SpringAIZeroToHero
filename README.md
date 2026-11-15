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

## Get started

The [concepts section](https://docs.spring.io/spring-ai/reference/concepts.html) provides a high-level overview of AI concepts and how they are represented in Spring AI.

The [Getting Started guide](https://docs.spring.io/spring-ai/reference/getting-started.html) shows you how to create your first AI application. Subsequent sections explore each component and common use cases with a code-focused approach.
