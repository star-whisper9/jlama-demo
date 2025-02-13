package space.sotis.jlamademo.service;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.functions.Generator;
import com.github.tjake.jlama.safetensors.prompt.PromptContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.sotis.jlamademo.config.BaseModelConfig;
import space.sotis.jlamademo.config.DefaultModelConfig;
import space.sotis.jlamademo.pojo.GenerationRequest;
import space.sotis.jlamademo.pojo.GenerationResponse;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ModelService implements IModelService {

    private final String NULL_ID_HINT = "会话 ID 不存在，回答可能缺少上下文。";
    private final ConcurrentHashMap<String, loadedModel> loadedModels = new ConcurrentHashMap<>();
    private final DefaultModelConfig defaultModelConfig;

    private record loadedModel(AbstractModel model, BaseModelConfig config) {
    }

    @Autowired
    public ModelService(DefaultModelConfig defaultModelConfig) {
        this.defaultModelConfig = defaultModelConfig;
        loadedModels.put(defaultModelConfig.getModelName(), new loadedModel(defaultModelConfig.defaultModel(), defaultModelConfig));
    }

    @Override
    public GenerationResponse generate(GenerationRequest request) {
        AbstractModel model = checkAndGetModel(request.getModelName());
        loadedModel modelRecord = loadedModels.get(request.getModelName());
        PromptContext context;

        /* fixme 从模型record加载的接口是无法获得默认值的 */
        request.setSystemPrompt(request.getSystemPrompt() == null ? ((DefaultModelConfig) modelRecord.config).getSystemPrompt() : request.getSystemPrompt());
        // 检查模型是否支持聊天提示
        if (model.promptSupport().isPresent()) {
            context = model.promptSupport()
                    .get()
                    .builder()
                    .addSystemMessage(request.getSystemPrompt())
                    .addUserMessage(request.getUserPrompt())
                    .build();
        } else {
            context = PromptContext.of(request.getUserPrompt());
        }

        if (log.isDebugEnabled()) {
            log.debug("生成请求: {}", request);
        }
        log.info("Prompt: {}", context.getPrompt());

        Generator.Response response = model.generate(
                request.getSessionId(),
                context,
                request.getTemperature(),
                request.getMaxTokens(),
                (s, aFloat) -> {
                }
        );

        GenerationResponse finalRes = new GenerationResponse(response.responseText, (response.generateTimeMs + response.promptTimeMs) / 1000L, response.generatedTokens + response.promptTokens, request.getSessionId());

        if (log.isDebugEnabled()) {
            log.debug("生成响应: {}", finalRes);
        }
        return finalRes;
    }

    private AbstractModel checkAndGetModel(String modelName) {
        if (!loadedModels.containsKey(modelName)) {
            throw new IllegalArgumentException("未找到模型: " + modelName);
        }
        return loadedModels.get(modelName).model;
    }
}
