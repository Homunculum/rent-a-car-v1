package com.example.rentacarv1.services.dtos.requests.car;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddCarRequest {

    @NotNull(message = "The daily price field cannot be null !")
    @DecimalMin(value = "0.0",inclusive = false,message = "Daily price must be greater than 0.")
    private double daily_price;


    @NotNull(message = "The kilometer field cannot be null !")
    @Min(value = 0,message = "Kilometer must be greater than or equal to 0.")
    private int kilometer;


    @NotBlank(message = "The plate field can't be empty.")
    @Size(min=5,max=10,message = "Licence plate must be between 5 and 9 characters")
    private String plate;


    @NotNull(message = "The year field cannot be null")
    @Min(value = 2005,message = "Year must be greater than or equal to 2005.")
    @Max(value = 2024,message = "Car model year can not be higher than ")
    private int year;

    @NotNull(message = "The model id cannot be null.")
    @Positive(message = "Id must be a positive number.")
    private int colorId;

    @NotNull(message = "The model id cannot be null.")
    @Positive(message = "Id must be a positive number.")
    private int modelId;

}
