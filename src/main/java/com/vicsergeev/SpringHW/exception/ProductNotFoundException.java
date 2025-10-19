package com.vicsergeev.SpringHW.exception;

import lombok.Getter;

/**
 * Created by Victor 12.10.2025
 */
@Getter
public class ProductNotFoundException extends RuntimeException {
    private final Long productId;
    public ProductNotFoundException(Long productId) {
        super("product with id " + productId + " not found");
        this.productId = productId;
    }
}