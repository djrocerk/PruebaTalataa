package PruebaT.ecommerce.exception;

public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(String message) {
        super(message);
    }
}
