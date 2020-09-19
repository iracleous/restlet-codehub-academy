package com.pfizer.restapi.repository;

import com.pfizer.restapi.model.Customer;
import com.pfizer.restapi.model.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {


    private EntityManager entityManager;

    public CustomerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Customer> findById(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        return customer != null ? Optional.of(customer) : Optional.empty();
    }

    public List<Customer> findAll() {

        return entityManager.createQuery("from Customer c WHERE c.active = true").getResultList();
    }

    public Optional<Customer> findByName(String name) {
        Customer customer = entityManager.createQuery("SELECT b FROM Customer b WHERE b.lastName = :name", Customer.class)
                .setParameter("name", name)
                .getSingleResult();
        return customer != null ? Optional.of(customer) : Optional.empty();
    }




    public Optional<Customer> save(Customer customer){

        try {
            entityManager.getTransaction().begin();
            entityManager.persist (customer);
            entityManager.getTransaction().commit();
            return Optional.of(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }






}
