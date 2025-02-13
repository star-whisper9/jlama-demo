package space.sotis.jlamademo.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import space.sotis.jlamademo.validation.Decimal;

import java.util.UUID;

@Data
public class GenerationRequest {
    @NotNull
    private String modelName;

    @NotNull
    private String userPrompt;

    private String systemPrompt;

    @Decimal(min = 0.0, max = 1.0)
    private float temperature;

    private int maxContextLength;

    private int maxTokens;

    private UUID sessionId;
}
