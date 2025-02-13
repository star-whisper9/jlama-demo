package space.sotis.jlamademo.service;

import space.sotis.jlamademo.pojo.GenerationRequest;
import space.sotis.jlamademo.pojo.GenerationResponse;

public interface IModelService {
    GenerationResponse generate(GenerationRequest request);
}
