package com.pfizer.restapi.repository;



import com.pfizer.restapi.model.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    private EntityManager entityManager;

    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Product> findById(Long id) {
        Product product = entityManager.find(Product.class, id);
        return product != null ? Optional.of(product) : Optional.empty();
    }

    public List<Product> findAll() {
        return entityManager.createQuery("from Product").getResultList();
    }

    public Optional<Product> findByName(String name) {
        Product product = entityManager.createQuery("SELECT b FROM Product b WHERE b.name = :name", Product.class)
                .setParameter("name", name)
                .getSingleResult();
        return product != null ? Optional.of(product) : Optional.empty();
    }

    public Optional<Product> findByNameNamedQuery(String name) {
        Product product = entityManager.createNamedQuery("Product.findByName", Product.class)
                .setParameter("name", name)
                .getSingleResult();
        return product != null ? Optional.of(product) : Optional.empty();
    }

    public Optional<Product> save(Product product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(product);
            entityManager.getTransaction().commit();
            return Optional.of(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean remove(Long id){
        entityManager.remove(findById(id));
        return false;
    }
}
