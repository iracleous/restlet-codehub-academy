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
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

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
                        new ProductRepresentation(product);


                LOGGER.finer("Product successfully retrieved");

                return result;

            }


        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

    }

    @Override
    public void remove() throws NotFoundException {

        LOGGER.finer("Removal of product");

        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        LOGGER.finer("User allowed to remove a product.");

        try {

            // Delete company in DB: return true if deleted
            Boolean isDeleted = productRepository.remove(id);

            // If product has not been deleted: if not it means that the id must
            // be wrong
            if (!isDeleted) {
                LOGGER.config("Product id does not exist");
                throw new NotFoundException(
                        "Product with the following identifier does not exist:"
                                + id);
            }
            LOGGER.finer("Product successfully removed.");

        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a product", ex);
            throw new ResourceException(ex);
        }



    }

    @Override
    public ProductRepresentation store(ProductRepresentation productReprIn) throws NotFoundException, BadEntityException {
        LOGGER.finer("Update a product.");

        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        LOGGER.finer("User allowed to update a product.");

        // Check given entity
        ResourceValidator.notNull(productReprIn);
        ResourceValidator.validate(productReprIn);
        LOGGER.finer("Company checked");

        try {

            // Convert CompanyRepresentation to Company
            Product productIn = productReprIn.createProduct();
            productIn.setId(id);

            Optional<Product> productOut;

            Optional<Product> oProduct = productRepository.findById(id);

            setExisting(oProduct.isPresent());

            // If product exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update product.");

                // Update product in DB and retrieve the new one.
                productOut = productRepository.save(productIn);



                // Check if retrieved product is not null : if it is null it
                // means that the id is wrong.
                if (!productOut.isPresent()) {
                    LOGGER.finer("Product does not exist.");
                    throw new NotFoundException(
                            "Product with the following id does not exist: "
                                    + id);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException(
                        "Company with the following id does not exist: " + id);
            }

            LOGGER.finer("Company successfully updated.");
            return new ProductRepresentation(productOut.get());

        } catch (Exception ex) {

            throw new ResourceException(ex);
        }



    }


}
