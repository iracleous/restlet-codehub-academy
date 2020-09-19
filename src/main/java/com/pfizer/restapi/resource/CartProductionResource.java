package com.pfizer.restapi.resource;

import com.pfizer.restapi.exception.BadEntityException;
import com.pfizer.restapi.exception.NotFoundException;
import com.pfizer.restapi.representation.ProductRepresentation;
import com.pfizer.restapi.representation.RepresentationResponse;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface CartProductionResource {

    @Get("json")
    public RepresentationResponse<ProductRepresentation> getProduct()  ;

    @Delete
    public void remove() throws NotFoundException;

    @Put("json")
    public ProductRepresentation store(ProductRepresentation productReprIn)
            throws NotFoundException, BadEntityException;


}
