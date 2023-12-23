package com.example.rentacarv1.services.concretes;

import com.example.rentacarv1.Entities.Car;
import com.example.rentacarv1.core.utilities.mappers.ModelMapperService;
import com.example.rentacarv1.repositories.CarRepository;
import com.example.rentacarv1.services.abstracts.CarService;
import com.example.rentacarv1.services.dtos.requests.car.AddCarRequest;
import com.example.rentacarv1.services.dtos.requests.car.UpdateCarRequest;
import com.example.rentacarv1.services.dtos.responses.car.GetByIdCarResponse;
import com.example.rentacarv1.services.dtos.responses.car.GetCarResponse;
import com.example.rentacarv1.services.rules.CarBusinessRules;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarManager implements CarService {

    private CarRepository carRepository;
    private ModelMapperService modelMapperService;
    private CarBusinessRules carBusinessRules;

    @Override
    public List<GetCarResponse> getAll() {
      List<Car> cars= carRepository.findAll();
      List<GetCarResponse> carResponses=cars.stream()
              .map(car -> this.modelMapperService.forResponse()
                      .map(car,GetCarResponse.class)).collect(Collectors.toList());
      return carResponses;
    }

    @Override
    public GetByIdCarResponse getById(int id) {
        Car car=this.carRepository.findById(id).orElseThrow();
        GetByIdCarResponse byIdCarResponse=this.modelMapperService.forResponse()
                .map(car, GetByIdCarResponse.class);
        return  byIdCarResponse;

    }

    @Override
    public void add(AddCarRequest addCarRequest) {

        addCarRequest.setPlate(carBusinessRules.plateValidator(addCarRequest.getPlate()));
        carBusinessRules.existsByPlate(addCarRequest.getPlate());

        Car car =this.modelMapperService.forRequest().map(addCarRequest,Car.class);

        this.carRepository.save(car);

    }

    @Override
    public void update(UpdateCarRequest updateCarRequest) {
       Car car=this.modelMapperService.forRequest().map(updateCarRequest,Car.class);
       this.carRepository.save(car);

    }

    @Override
    public void delete(int id) {
         this.carRepository.deleteById(id);

    }
}
