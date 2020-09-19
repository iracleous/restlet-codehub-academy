package com.pfizer.restapi.representation;

import com.pfizer.restapi.model.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerRepresentation {


    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private String email;
    private String password;
    private Date creationDate;
    private String creationDateString;
    private boolean active;

    private String uri;

    public CustomerRepresentation(Customer customer) {
        if (customer != null) {
            firstName = customer.getFirstName();
            lastName = customer.getLastName();
            address = customer.getAddress();
            city = customer.getCity();
            zipCode = customer.getZipCode();
            phoneNumber = customer.getPhoneNumber();
            email = customer.getEmail();
            password = customer.getPassword();
            creationDate = customer.getCreationDate() ;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            creationDateString = sdf.format(customer.getCreationDate() );
            active = customer.isActive();
            uri =  "http://localhost:9000/v1/customer/" + customer.getId();
        }

    }

    public Customer createCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setZipCode(zipCode);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setCreationDate(creationDate);
        customer.setActive(active);
        return customer;
    }


}




