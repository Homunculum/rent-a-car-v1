package com.example.rentacarv1.services.dtos.requests.rental;

import com.example.rentacarv1.entities.Car;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {

    @NotNull(message = "Id can not be null.")
    @Positive(message = "Id must be a positive number.")
    private  int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "The starting date cannot be further back than today.")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "The end date cannot be further back than today.")
    private LocalDate endDate;


    @Min(value = 0,message = "The discount value can not be lower than 0")
    private double discount;

    @Positive(message = "enter only positive")
    @Max(value = 2,message = "Enter the Car ID as a number between zero and two.")
    private int carId;

    @Positive(message = "enter only positive")
    @Max(value = 2,message = "Enter the customer ID as a number between zero and two.")
    private int customerId;

    @Positive(message = "enter only positive")
    @Max(value = 999999,message = "Enter the employee ID as a number")
    private int employeeId;
    @AssertTrue(message = "End date can not before than start date")
    private boolean isStartDateBeforeEndDate(){
        return startDate.isBefore(endDate);
    }
    @AssertTrue(message = "The vehicle can be rented for a maximum of 25 days")
    private boolean isRentalLimitOK(){
        int rentalLimit = startDate.until(endDate).getDays() + 1;
        if (rentalLimit > 25) {
            return false;
        }
        return true;
    }



}


