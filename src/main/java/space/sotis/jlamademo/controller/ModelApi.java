package space.sotis.jlamademo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.sotis.jlamademo.pojo.GenerationRequest;
import space.sotis.jlamademo.pojo.GenerationResponse;
import space.sotis.jlamademo.service.IModelService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ModelApi {
    private final IModelService modelService;

    @Autowired
    public ModelApi(IModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping("/generate")
    public GenerationResponse generate(@RequestBody @Validated GenerationRequest request) {
        if (request.getSessionId() == null) {
            request.setSessionId(UUID.randomUUID());
        }
        return modelService.generate(request);
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
