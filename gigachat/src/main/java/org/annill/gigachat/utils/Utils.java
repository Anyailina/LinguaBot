package org.annill.gigachat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Utils {
    private final ObjectMapper objectMapper;

    public Utils() {
        this.objectMapper = new ObjectMapper();
    }
}
