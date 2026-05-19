package org.example.session05_b3.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("ko tìm thấy sản phẩm id: " + id);
    }
}
