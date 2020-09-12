package com.pfizer.restapi.resource;

import com.pfizer.restapi.exception.BadEntityException;
import com.pfizer.restapi.exception.NotFoundException;
import com.pfizer.restapi.representation.ProductRepresentation;
import org.restlet.resource.ServerResource;

public class ProductResourceImpl extends ServerResource implements ProductResource{
    @Override
    public ProductRepresentation getProduct() throws NotFoundException {
        return null;
    }

    @Override
    public void remove() throws NotFoundException {

    }

    @Override
    public ProductRepresentation store(ProductRepresentation productReprIn) throws NotFoundException, BadEntityException {
        return null;
    }

    @Override
    public ProductRepresentation add(ProductRepresentation companyReprIn) throws BadEntityException {
        return null;
    }
}
