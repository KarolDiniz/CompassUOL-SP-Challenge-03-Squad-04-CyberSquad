package br.com.compassuol.pb.challenge.msproducts.exception;
public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super("Page not found");
    }
}
