package com.pfizer.restapi.resource;

import com.pfizer.restapi.exception.BadEntityException;
import com.pfizer.restapi.exception.NotFoundException;
import com.pfizer.restapi.model.Product;
import com.pfizer.restapi.repository.ProductRepository;
import com.pfizer.restapi.repository.util.JpaUtil;
import com.pfizer.restapi.representation.ProductRepresentation;
import com.pfizer.restapi.resource.util.ResourceValidator;
import com.pfizer.restapi.security.ResourceUtils;
import com.pfizer.restapi.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductListResourceImpl

        extends ServerResource implements ProductListResource
{

    public static final Logger LOGGER = Engine.getLogger(ProductResourceImpl.class);


    private ProductRepository productRepository ;


    @Override
    protected void doInit() {
        LOGGER.info("Initialising product resource starts");
        try {
            productRepository =
                    new ProductRepository (JpaUtil.getEntityManager()) ;

        }
        catch(Exception e)
        {

        }

        LOGGER.info("Initialising product resource ends");
    }




    public List<ProductRepresentation> getProducts() throws NotFoundException{

        LOGGER.finer("Select all products.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);

        try{

            List<Product> products =
                    productRepository.findAll();
            List<ProductRepresentation> result =
                    new ArrayList<>();

        }


    }





    @Override
    public ProductRepresentation add
            (ProductRepresentation productRepresentationIn)
            throws BadEntityException {

        LOGGER.finer("Add a new product.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        LOGGER.finer("User allowed to add a product.");

        // Check entity

        ResourceValidator.notNull(productRepresentationIn);
        ResourceValidator.validate(productRepresentationIn);
        LOGGER.finer("Product checked");

        try {

            // Convert CompanyRepresentation to Company
            Product productIn = new Product();
            productIn.setName(productRepresentationIn.getName());
            productIn.setPrice(productRepresentationIn.getPrice());
            productIn.setInventoryQuantity(
                    productRepresentationIn.getInventoryQuantity());


            Optional<Product> productOut =
                    productRepository.save(productIn);
            Product product= null;
            if (productOut.isPresent())
                product = productOut.get();
            else
                throw new
                        BadEntityException(" Product has not been created");

            ProductRepresentation result =
                    new ProductRepresentation();
            result.setInventoryQuantity(product.getInventoryQuantity());
            result.setName(product.getName());
            result.setPrice(product.getPrice());
            result.setUri("http://localhost:9000/v1/product/"+product.getId());

            getResponse().setLocationRef(
                    "http://localhost:9000/v1/product/"+product.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.finer("Product successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a product", ex);

            throw new ResourceException(ex);
        }



    }

}
