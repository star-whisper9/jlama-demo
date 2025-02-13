package space.sotis.jlamademo.config;

import com.github.tjake.jlama.safetensors.DType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(value = "classpath:llama.properties", encoding = "UTF-8")
public class DefaultModelConfig implements BaseModelConfig {
    @Value("${llama.model.default.name}")
    private String modelName;

    @Value("${llama.model.default}")
    private String modelPath;

    @Value("${llama.model.default.max-context-length}")
    private int maxContextLength;

    @Value("${llama.model.default.temperature}")
    private float temperature;

    @Value("${llama.model.default.max-tokens}")
    private int maxTokens;

    @Value("${llama.model.default.system-prompt}")
    private String systemPrompt;

    @Value("${llama.model.default.memory-q-type}")
    private DType memoryQType;

    @Value("${llama.model.default.model-q-type}")
    private DType modelQType;

//    public AbstractModel defaultModel() {
//        File model = new File(modelPath);
//        return ModelSupport.loadModel(model, memoryQType, modelQType);
//    }
}
