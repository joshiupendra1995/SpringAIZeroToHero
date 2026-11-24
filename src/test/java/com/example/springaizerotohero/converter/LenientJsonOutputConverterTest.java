package com.example.springaizerotohero.converter;

import java.util.List;

import com.example.springaizerotohero.dto.StructuredAnswer;
import org.junit.jupiter.api.Test;
import org.springframework.ai.converter.BeanOutputConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LenientJsonOutputConverterTest {

    private final LenientJsonOutputConverter<StructuredAnswer> converter =
            new LenientJsonOutputConverter<>(StructuredAnswer.class);
    private final String json = """
            {"topic":"Spring AI","summary":"A framework","keyPoints":["models","prompts"]}
            """;

    @Test
    void convertsPlainAndFencedJsonWithSurroundingText() {
        var expected = new StructuredAnswer("Spring AI", "A framework", List.of("models", "prompts"));
        assertEquals(expected, converter.convert(json));
        assertEquals(expected, converter.convert("Here is the answer:\n```json\n" + json + "```\nDone."));
        assertEquals(expected, converter.convert("```\n" + json + "```"));
    }

    @Test
    void exposesDelegateFormatAndSchema() {
        var delegate = new BeanOutputConverter<>(StructuredAnswer.class);
        assertEquals(delegate.getFormat(), converter.getFormat());
        assertEquals(delegate.getJsonSchema(), converter.getJsonSchema());
    }

    @Test
    void rejectsMalformedOrAmbiguousResponses() {
        assertThrows(RuntimeException.class, () -> converter.convert("Here is not JSON"));
        assertThrows(RuntimeException.class, () -> converter.convert("```json\n{broken}\n```"));
        assertThrows(IllegalArgumentException.class,
                () -> converter.convert("```json\n" + json + "```\n```json\n" + json + "```"));
    }
}
