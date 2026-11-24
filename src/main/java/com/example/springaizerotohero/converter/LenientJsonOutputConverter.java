package com.example.springaizerotohero.converter;

import java.util.regex.Pattern;

import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.StructuredOutputConverter;

public class LenientJsonOutputConverter<T> implements StructuredOutputConverter<T> {

    private static final Pattern JSON_FENCE = Pattern.compile(
            "(?m)^[\\t ]*```(?:json)?[\\t ]*\\R([\\s\\S]*?)^[\\t ]*```[\\t ]*$",
            Pattern.CASE_INSENSITIVE);

    private final BeanOutputConverter<T> delegate;

    public LenientJsonOutputConverter(Class<T> targetType) {
        this.delegate = new BeanOutputConverter<>(targetType);
    }

    @Override
    public String getFormat() {
        return delegate.getFormat();
    }

    @Override
    public String getJsonSchema() {
        return delegate.getJsonSchema();
    }

    @Override
    public T convert(String source) {
        var matcher = JSON_FENCE.matcher(source);
        if (!matcher.find()) {
            return delegate.convert(source);
        }

        String json = matcher.group(1).trim();
        if (matcher.find()) {
            throw new IllegalArgumentException("Response contains multiple JSON code blocks");
        }
        return delegate.convert(json);
    }
}
