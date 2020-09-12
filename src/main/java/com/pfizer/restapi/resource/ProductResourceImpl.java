package com.pfizer.restapi.resource;

import com.pfizer.restapi.ApiMain;
import com.pfizer.restapi.exception.BadEntityException;
import com.pfizer.restapi.exception.NotFoundException;
import com.pfizer.restapi.model.Product;
import com.pfizer.restapi.repository.ProductRepository;
import com.pfizer.restapi.repository.util.JpaUtil;
import com.pfizer.restapi.representation.ProductRepresentation;
import com.pfizer.restapi.resource.contant.Constants;
import com.pfizer.restapi.resource.util.ResourceValidator;
import com.pfizer.restapi.security.ResourceUtils;
import com.pfizer.restapi.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductResourceImpl
        extends ServerResource implements ProductResource{

    public static final Logger LOGGER = Engine.getLogger(ProductResourceImpl.class);

    private long id;
private ProductRepository productRepository ;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising product resource starts");
      try {
          productRepository =
                  new ProductRepository (JpaUtil.getEntityManager()) ;
          id = Long.parseLong(getAttribute("id"));
      
      }
      catch(Exception e)
      {
          id =-1;
      }

      LOGGER.info("Initialising product resource ends");
    }


    @Override
    public ProductRepresentation getProduct()
            throws NotFoundException {
        LOGGER.info("Retrieve a product");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);


        // Initialize the persistence layer.
        ProductRepository productRepository = new ProductRepository(JpaUtil.getEntityManager());
        Product product;
        try {
            Optional<Product> oproduct = productRepository.findById(id);


            setExisting(oproduct.isPresent());
            if (!isExisting()) {
                LOGGER.config("product id does not exist:" + id);
                throw new NotFoundException("No product with  : " + id);
            } else {
                product = oproduct.get();
                LOGGER.finer("User allowed to retrieve a product.");
                //System.out.println(  userId);
                ProductRepresentation result =
                        new ProductRepresentation();
                result.setInventoryQuantity(product.getInventoryQuantity());
                result.setName(product.getName());
                result.setPrice(product.getPrice());
                result.setUri("http://localhost:9000/v1/product/"+id);

                LOGGER.finer("Product successfully retrieved");

                return result;

            }


        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

    }

    @Override
    public void remove() throws NotFoundException {

    }

    @Override
    public ProductRepresentation store(ProductRepresentation productReprIn) throws NotFoundException, BadEntityException {
        return null;
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
