package org.annill.linguabot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Token {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_at")
    private Long expires;
}
