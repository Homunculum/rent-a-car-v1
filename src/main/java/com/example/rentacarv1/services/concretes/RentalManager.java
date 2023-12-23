package com.example.rentacarv1.services.concretes;

import com.example.rentacarv1.Entities.*;
import com.example.rentacarv1.core.utilities.mappers.ModelMapperService;
import com.example.rentacarv1.repositories.*;
import com.example.rentacarv1.services.abstracts.RentalService;
import com.example.rentacarv1.services.dtos.requests.rental.AddRentalRequest;
import com.example.rentacarv1.services.dtos.requests.rental.UpdateRentalRequest;
import com.example.rentacarv1.services.dtos.responses.rental.GetRentalListResponse;
import com.example.rentacarv1.services.dtos.responses.rental.GetRentalResponse;
import com.example.rentacarv1.services.rules.RentalBusinessRules;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {

    private final RentalRepository rentalRepository;
    private final ModelMapperService modelMapperService;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final RentalBusinessRules rentalBusinessRules;


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



        Customer customer = customerRepository.findById(Integer.valueOf(addRentalRequest.getCustomer()))
                .orElseThrow(()-> new IllegalArgumentException("The specified user was not found"));
        Car car = carRepository.findById(Integer.valueOf(addRentalRequest.getCar()))
                .orElseThrow(()-> new IllegalArgumentException("The specified car was not found"));
        Employee employee = employeeRepository.findById(Integer.valueOf(addRentalRequest.getEmployee()))
                .orElseThrow(()-> new IllegalArgumentException("The specified user was not found"));

        Rental rental=this.modelMapperService.forRequest().map(addRentalRequest,Rental.class);
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setEmployee(employee);
        rental.setStartKilometer(car.getKilometer());
        int rentalLimit = rental.getStartDate().until(rental.getEndDate()).getDays() + 1;
        rental.setTotalPrice(car.getDailyPrice() * rentalLimit);
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
