package br.com.compassuol.pb.challenge.msproducts.exception;
public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String fieldName, String message) {
        super("Invalid product: " + fieldName + " - " + message);
    }
}