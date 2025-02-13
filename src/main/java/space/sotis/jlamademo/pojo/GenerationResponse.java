package space.sotis.jlamademo.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class GenerationResponse {
    private String responseText;
    private long time;
    private long tokens;

    private UUID sessionId;

    public GenerationResponse(String responseText, long time, long tokens, UUID sessionId) {
        this.responseText = responseText;
        this.time = time;
        this.tokens = tokens;
        this.sessionId = sessionId;
    }
}
