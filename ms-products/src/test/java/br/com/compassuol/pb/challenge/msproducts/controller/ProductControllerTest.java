package br.com.compassuol.pb.challenge.msproducts.controller;
import br.com.compassuol.pb.challenge.msproducts.domain.Product;
import br.com.compassuol.pb.challenge.msproducts.dto.ProductDTO;
import br.com.compassuol.pb.challenge.msproducts.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    public void createProduct_ValidProductDTO_ShouldReturnCreatedStatusAndProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        when(productService.createProduct(productDTO)).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(productDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).createProduct(productDTO);
    }


    @Test
    public void getAllProducts_ShouldReturnAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void getAllProductsPaged_ShouldReturnAllProductsPaged() {

        Page<Product> productPage = new PageImpl<>(new ArrayList<>());
        when(productService.getAllProducts(any(Pageable.class))).thenReturn(productPage);


        ResponseEntity<Page<Product>> response = productController.getAllProductsPaged(1, 5, "DESC", "name");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productPage, response.getBody());
        verify(productService, times(1)).getAllProducts(any(Pageable.class));
    }


    @Test
    public void getProduct_ExistingProductId_ShouldReturnProduct() {

        Long productId = 1L;
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void updateProduct_ExistingProductIdAndValidProductDTO_ShouldReturnUpdatedProduct() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        Product updatedProduct = new Product();
        when(productService.updateProduct(productId, productDTO)).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(productId, productDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
        verify(productService, times(1)).updateProduct(productId, productDTO);
    }

    @Test
    public void deleteProduct_ExistingProductId_ShouldReturnNoContentStatus() {
        Long productId = 1L;

        ResponseEntity<Void> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void deleteAllProducts_ShouldReturnNoContentStatus() {
        ResponseEntity<Void> response = productController.deleteAllProducts();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteAllProducts();
    }

}