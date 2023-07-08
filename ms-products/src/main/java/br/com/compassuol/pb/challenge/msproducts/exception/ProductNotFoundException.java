package br.com.compassuol.pb.challenge.msproducts.exception;
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found with ID: " + id);
    }
}