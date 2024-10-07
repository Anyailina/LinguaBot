package org.annill.linguabot.parsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class ChatCompletion {
    private List<Choice> choices;
    private long created;
    private String model;
    private String object;
    private Usage usage;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Choice {
        private Message message;
        private int index;
        private String finish_reason;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        private String content;
        private String role;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;

    }
}