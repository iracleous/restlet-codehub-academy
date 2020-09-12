package com.pfizer.restapi.resource;

import com.pfizer.restapi.exception.BadEntityException;
import com.pfizer.restapi.exception.NotFoundException;
import com.pfizer.restapi.representation.ProductRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface ProductListResource {

    @Post("json")
    public ProductRepresentation add(ProductRepresentation companyReprIn)
            throws BadEntityException;
    @Get("json")
    public List<ProductRepresentation> getProducts() throws NotFoundException;



}
