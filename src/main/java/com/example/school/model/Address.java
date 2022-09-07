package com.example.school.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
public class Address extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int addressId;

    @NotBlank(message = "first line of address can't be blank")
    @Size(min=3, message = "Address should consist of minimum of 3 characters")
    private String address1;

    private String address2;

    @NotBlank(message = "City must not be blank")
    @Size(min=3, message = "City name should consist of minimum of 3 characters")
    private String city;

    @NotBlank(message = "City must not be blank")
    @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip code must be 5 digits without dashes")
    private String zipCode;


}
