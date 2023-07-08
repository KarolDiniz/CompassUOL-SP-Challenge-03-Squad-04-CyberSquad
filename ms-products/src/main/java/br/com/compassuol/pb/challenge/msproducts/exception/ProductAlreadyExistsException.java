package br.com.compassuol.pb.challenge.msproducts.exception;
public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String name) {
        super("Product with name '" + name + "' already exists");
    }
}
