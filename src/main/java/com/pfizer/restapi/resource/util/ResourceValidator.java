package com.pfizer.restapi.resource.util;

import com.pfizer.restapi.exception.BadEntityException;
import com.pfizer.restapi.representation.ProductRepresentation;

public class ResourceValidator {
    /**
     * Checks that the given entity is not null.
     *
     * @param entity
     *            The entity to check.
     * @throws BadEntityException
     *             In case the entity is null.
     */
    public static void notNull(Object entity) throws BadEntityException {
        if (entity == null) {
            throw new BadEntityException("No input entity");
        }
    }

    /**
     * Checks that the given company is valid.
     *
     * @param productRepresentation
     * @throws BadEntityException
     */
    public static void validate(ProductRepresentation productRepresentation)
            throws BadEntityException {
        if ( productRepresentation.getName()==null) {
            throw new BadEntityException(
                    "product name cannot be null");
        }
    }
}
