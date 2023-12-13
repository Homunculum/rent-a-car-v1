package com.example.rentacarv1.services.concretes;

import com.example.rentacarv1.Entities.Car;
import com.example.rentacarv1.Entities.Rental;
import com.example.rentacarv1.core.utilities.mappers.ModelMapperService;
import com.example.rentacarv1.repositories.BrandRepository;
import com.example.rentacarv1.repositories.RentalRepository;
import com.example.rentacarv1.services.abstracts.RentalService;
import com.example.rentacarv1.services.dtos.requests.rental.AddRentalRequest;
import com.example.rentacarv1.services.dtos.requests.rental.UpdateRentalRequest;
import com.example.rentacarv1.services.dtos.responses.car.GetCarListResponse;
import com.example.rentacarv1.services.dtos.responses.car.GetCarResponse;
import com.example.rentacarv1.services.dtos.responses.rental.GetRentalListResponse;
import com.example.rentacarv1.services.dtos.responses.rental.GetRentalResponse;

import java.util.List;
import java.util.stream.Collectors;

public class RentalManager implements RentalService {

    private RentalRepository rentalRepository;
    private ModelMapperService modelMapperService;
    @Override
    public List<GetRentalListResponse> getAll() {
        List<Rental> rentals=rentalRepository.findAll();
        List<GetRentalListResponse> rentalListResponse=rentals.stream().map(rental -> this.modelMapperService.forResponse()
                .map(rental,GetRentalListResponse.class)).collect(Collectors.toList());

        return rentalListResponse;
    }

    @Override
    public GetRentalResponse getById(int id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        GetRentalResponse getRentalResponse=this.modelMapperService.forRequest().map(rental,GetRentalResponse.class);
        return getRentalResponse;
    }

    @Override
    public void add(AddRentalRequest addRentalRequest) {
        Rental rental=this.modelMapperService.forRequest().map(addRentalRequest,Rental.class);
        this.rentalRepository.save(rental);
    }

    @Override
    public void update(UpdateRentalRequest updateRentalRequest) {
        Rental rental =this.modelMapperService.forRequest().map(updateRentalRequest,Rental.class);
        rentalRepository.save(rental);
    }

    @Override
    public void delete(int id) {
        rentalRepository.deleteById(id);
    }
}
