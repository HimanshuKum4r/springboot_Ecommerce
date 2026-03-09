package com.ecommerce.sb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @NotBlank
    @Size(min =5 , message = "must be atleast 5 characters")
    private String Street;
    @NotBlank
    @Size(min =5 , message = "buildingname be atleast 5 characters")
    private String buildingName;
    @NotBlank
    @Size(min =4 , message = "ciity must be atleast 4 characters")
    private  String city;
    @NotBlank
    @Size(min =2 , message = "statename must be atleast 2 characters")
    private String state;
    @NotBlank
    @Size(min =6 , message = "pincode must be atleast 6 characters")
    private Integer pincode;
    @NotBlank
    @Size(min =2, message = "country must be atleast 2 characters")
    private String country;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String street, String buildingName, String city, String state, Integer pincode, String country) {
        Street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
    }
}
