package com.example.rentacarv1.services.concretes;

import com.example.rentacarv1.Entities.Model;
import com.example.rentacarv1.core.utilities.mappers.ModelMapperService;
import com.example.rentacarv1.repositories.ModelRepository;
import com.example.rentacarv1.services.abstracts.ModelService;
import com.example.rentacarv1.services.dtos.requests.model.AddModelRequest;
import com.example.rentacarv1.services.dtos.requests.model.UpdateModelRequest;
import com.example.rentacarv1.services.dtos.responses.model.GetModelListResponse;
import com.example.rentacarv1.services.dtos.responses.model.GetModelResponse;
import com.example.rentacarv1.services.rules.ModelBusinessRules;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ModelManager implements ModelService {
    private final ModelRepository modelRepository;
    private final ModelMapperService modelMapperService;

    private final ModelBusinessRules modelBusinessRules;

    @Override
    public List<GetModelListResponse> getAll() {
        List<Model> models = modelRepository.findAll();
        List<GetModelListResponse> getModelListResponses = models.stream().map
                (model -> this.modelMapperService.forResponse().map(model, GetModelListResponse.class))
                .collect(Collectors.toList());
        return getModelListResponses;
    }

    @Override
    public void add(AddModelRequest addModelRequest) {

        modelBusinessRules.existsByModel(addModelRequest.getName());

        Model model = this.modelMapperService.forRequest().map(addModelRequest,Model.class);
        this.modelRepository.save(model);
    }

    @Override
    public GetModelResponse getById(int id) {
        Model model = modelRepository.findById(id).orElseThrow();
        GetModelResponse getModelResponse = this.modelMapperService.forResponse().map(model,GetModelResponse.class);
        return getModelResponse;
    }

    @Override
    public void update(UpdateModelRequest updateModelRequest) {
        Model model = this.modelMapperService.forRequest().map(updateModelRequest,Model.class);
        this.modelRepository.save(model);
    }

    @Override
    public void delete(int id) {
        this.modelRepository.deleteById(id);
    }
}
