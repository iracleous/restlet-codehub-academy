package com.pfizer.restapi.resource;

import com.pfizer.restapi.exception.BadEntityException;
import com.pfizer.restapi.exception.NotFoundException;
import com.pfizer.restapi.model.Customer;
import com.pfizer.restapi.model.Product;
import com.pfizer.restapi.repository.CustomerRepository;
import com.pfizer.restapi.repository.ProductRepository;
import com.pfizer.restapi.repository.util.JpaUtil;
import com.pfizer.restapi.representation.CustomerRepresentation;
import com.pfizer.restapi.representation.ProductRepresentation;
import com.pfizer.restapi.resource.util.ResourceValidator;
import com.pfizer.restapi.security.ResourceUtils;
import com.pfizer.restapi.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerListResourceImpl extends ServerResource implements CustomerListResource {

    public static final Logger LOGGER = Engine.getLogger(CustomerListResourceImpl.class);
    private CustomerRepository customerRepository;
    private EntityManager em;

    private Date startDate;
    private Date endDate;

    /**
     * This release method closes the entityManager
     */
    @Override
    protected void doRelease() {
        em.close();
    }

    /**
     * Initializes the customer repository
     */
    @Override
    protected void doInit() {
        LOGGER.info("Initialising customer resource starts");
        try {
            em = JpaUtil.getEntityManager();
            customerRepository = new CustomerRepository(em);
    try {
        String startDateString = getQueryValue("from");
        String   endDateString = getQueryValue("to");
        String[] words = startDateString.split("-");

        startDate = new Date(Integer.parseInt(words[0])-1900,
                Integer.parseInt(words[1]) - 1, Integer.parseInt(words[2])  );

         words = endDateString.split("-");
        endDate = new Date(Integer.parseInt(words[0])-1900,
                 Integer.parseInt(words[1]) - 1, Integer.parseInt(words[2])  );
    }
    catch(Exception e)
    {
        startDate =null; endDate =null;
    }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

        LOGGER.info("Initialising customer resource ends");
    }

    /**
     *
     * @param customerIn    representation of a Customer given by the frontEnd
     * @return  a representation of the persisted object
     * @throws BadEntityException
     */
    @Override
    public CustomerRepresentation add(CustomerRepresentation customerIn) throws BadEntityException {

        LOGGER.finer("Add a new customer.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        LOGGER.finer("User allowed to add a customer.");

        // Check entity

        if (customerIn == null) throw new BadEntityException("bad entity");

        LOGGER.finer("customerIn checked");

        try {

            // Convert CompanyRepresentation to Company
            Customer customer = customerIn.createCustomer();

            Optional<Customer> customerOptOut = customerRepository.save(customer);

            Customer customerOut;
            if (customerOptOut.isPresent())
                customerOut = customerOptOut.get();
            else
                throw new
                        BadEntityException(" Product has not been created");

            CustomerRepresentation result = new CustomerRepresentation(customerOut);

            LOGGER.finer("Customer successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a customer", ex);

            throw new ResourceException(ex);
        }


    }

    /**
     *
     * @return all active customers
     * @throws NotFoundException
     */

    @Override
    public List<CustomerRepresentation> getCustomers() throws NotFoundException {
        LOGGER.finer("Select all customers.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        try {

            List<Customer> customers;
            if (startDate ==null || endDate ==null)
            // find customers within a range dates
                 customers = customerRepository.findAll();

            else
                customers = customerRepository.findAll(startDate , endDate  );


            List<CustomerRepresentation> result = new ArrayList<>();
            customers.forEach(customer -> result.add(new CustomerRepresentation(customer)));
            return result;
        } catch (Exception e) {
            throw new NotFoundException("customers not found");
        }
    }
}
