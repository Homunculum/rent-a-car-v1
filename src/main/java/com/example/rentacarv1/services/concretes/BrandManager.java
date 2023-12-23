package com.example.rentacarv1.services.concretes;

import com.example.rentacarv1.Entities.Brand;
import com.example.rentacarv1.core.utilities.mappers.ModelMapperService;
import com.example.rentacarv1.repositories.BrandRepository;
import com.example.rentacarv1.services.abstracts.BrandService;
import com.example.rentacarv1.services.dtos.requests.brand.AddBrandRequest;
import com.example.rentacarv1.services.dtos.requests.brand.UpdateBrandRequest;
import com.example.rentacarv1.services.dtos.responses.brand.GetBrandListResponse;
import com.example.rentacarv1.services.dtos.responses.brand.GetBrandResponse;
import com.example.rentacarv1.services.rules.BrandBusinessRules;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapperService modelMapperService;

    private final BrandBusinessRules brandBusinessRules;


    @Override
    public List<GetBrandListResponse> getAll() {

        List<Brand> brands=brandRepository.findAll();
        List<GetBrandListResponse> brandListResponse=brands.stream().map(brand -> this.modelMapperService.forResponse()
                .map(brand,GetBrandListResponse.class)).collect(Collectors.toList());

        return brandListResponse;
    }

    @Override
    public GetBrandResponse getById(int id) {

        Brand brand = brandRepository.findById(id).orElseThrow();
        GetBrandResponse getBrandResponse=this.modelMapperService.forRequest().map(brand,GetBrandResponse.class);
        return getBrandResponse;
    }

    @Override
    public void add(AddBrandRequest addBrandRequest) {

        brandBusinessRules.existsByBrand(addBrandRequest.getName());

        Brand brand=this.modelMapperService.forRequest().map(addBrandRequest,Brand.class);
        this.brandRepository.save(brand);
    }

    @Override
    public void update(UpdateBrandRequest updateBrandRequest) {
        Brand brand =this.modelMapperService.forRequest().map(updateBrandRequest,Brand.class);
        brandRepository.save(brand);
    }

    @Override
    public void delete(int id) {
        brandRepository.deleteById(id);
    }

}
