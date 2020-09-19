package com.pfizer.restapi.resource;

import com.pfizer.restapi.exception.BadEntityException;
import com.pfizer.restapi.exception.NotFoundException;
import com.pfizer.restapi.representation.CustomerRepresentation;
import com.pfizer.restapi.representation.ProductRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface CustomerListResource {

    @Post("json")
    public CustomerRepresentation add(CustomerRepresentation customerIn)
            throws BadEntityException;
    @Get("json")
    public List<CustomerRepresentation> getCustomers() throws NotFoundException;




}
