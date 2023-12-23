package com.example.rentacarv1.services.concretes;

import com.example.rentacarv1.Entities.Customer;
import com.example.rentacarv1.core.utilities.mappers.ModelMapperService;
import com.example.rentacarv1.repositories.CustomerRepository;
import com.example.rentacarv1.services.abstracts.CustomerService;
import com.example.rentacarv1.services.dtos.requests.customer.AddCustomerRequest;
import com.example.rentacarv1.services.dtos.requests.customer.UpdateCustomerRequest;
import com.example.rentacarv1.services.dtos.responses.customer.GetCustomerListResponse;
import com.example.rentacarv1.services.dtos.responses.customer.GetCustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class CustomerManager implements CustomerService {

    private CustomerRepository customerRepository;
    private ModelMapperService modelMapperService;
    @Override
    public List<GetCustomerListResponse> getAll() {
        List<Customer> customers=customerRepository.findAll();
        List<GetCustomerListResponse> customerListResponse=customers.stream().map(customer -> this.modelMapperService.forResponse()
                .map(customer,GetCustomerListResponse.class)).collect(Collectors.toList());

        return customerListResponse;
    }

    @Override
    public GetCustomerResponse getById(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        GetCustomerResponse getCustomerResponse=this.modelMapperService.forRequest().map(customer,GetCustomerResponse.class);
        return getCustomerResponse;
    }

    @Override
    public void add(AddCustomerRequest addCustomerRequest) {
        Customer customer=this.modelMapperService.forRequest().map(addCustomerRequest,Customer.class);
        this.customerRepository.save(customer);
    }

    @Override
    public void update(UpdateCustomerRequest updateCustomerRequest) {
        Customer customer =this.modelMapperService.forRequest().map(updateCustomerRequest,Customer.class);
        customerRepository.save(customer);
    }

    @Override
    public void delete(int id) {
        customerRepository.deleteById(id);
    }
}
