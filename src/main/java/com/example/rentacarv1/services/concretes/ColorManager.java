package com.example.rentacarv1.services.concretes;

import com.example.rentacarv1.Entities.Color;
import com.example.rentacarv1.core.utilities.mappers.ModelMapperService;
import com.example.rentacarv1.repositories.ColorRepository;
import com.example.rentacarv1.services.abstracts.ColorService;
import com.example.rentacarv1.services.dtos.requests.color.AddColorRequest;
import com.example.rentacarv1.services.dtos.requests.color.UpdateColorRequest;
import com.example.rentacarv1.services.dtos.responses.color.GetColorListResponse;
import com.example.rentacarv1.services.dtos.responses.color.GetColorResponse;
import com.example.rentacarv1.services.rules.ColorBusinessRules;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColorManager implements ColorService {
    private final ColorRepository colorRepository;
    private final ModelMapperService modelMapperService;

    private final ColorBusinessRules colorBusinessRules;

    @Override
    public List<GetColorListResponse> getAll() {
       List<Color> colors = this.colorRepository.findAll();
       List<GetColorListResponse> getColorListResponses = colors.stream().map
               (color -> this.modelMapperService.forResponse().map(color, GetColorListResponse.class)).
               collect(Collectors.toList());
       return getColorListResponses;
    }

    @Override
    public GetColorResponse getById(int id) {
        Color color  = this.colorRepository.findById(id).orElseThrow();
        GetColorResponse getColorResponse =this.modelMapperService.forResponse()
                .map(color,GetColorResponse.class);
        return getColorResponse;
    }

    @Override
    public void add(AddColorRequest addColorRequest) {

        colorBusinessRules.existsByColor(addColorRequest.getName());

        Color color = this.modelMapperService.forRequest().map(addColorRequest,Color.class);
        this.colorRepository.save(color);
    }

    @Override
    public void update(UpdateColorRequest updateColorRequest) {
        Color color = this.modelMapperService.forRequest().map(updateColorRequest,Color.class);
        this.colorRepository.save(color);
    }

    @Override
    public void delete(int id) {
        this.colorRepository.deleteById(id);
    }
}
