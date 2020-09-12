package com.pfizer.restapi.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;    /** Technical identifier.  primary key */
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private String email;
    private String password;
    private Date creationDate;

    @OneToMany(mappedBy = "customer")
    private List<Cart> carts = new ArrayList<>();
}
