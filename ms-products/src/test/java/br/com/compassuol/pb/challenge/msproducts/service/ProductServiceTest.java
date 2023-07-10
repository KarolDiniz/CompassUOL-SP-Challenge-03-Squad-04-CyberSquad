package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.domain.Product;
import br.com.compassuol.pb.challenge.msproducts.dto.ProductDTO;
import br.com.compassuol.pb.challenge.msproducts.repository.CategoryRepository;
import br.com.compassuol.pb.challenge.msproducts.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
    }
    @Test
    void createProduct_ValidData_ReturnsProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setCategories(new ArrayList<>());

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId(1L);
            return savedProduct;
        });

        Product savedProduct = productService.createProduct(productDTO);

        assertNotNull(savedProduct.getId());
        assertEquals(productDTO.getName(), savedProduct.getName());
        assertNotNull(savedProduct.getCategories());
        assertTrue(savedProduct.getCategories().isEmpty());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById_ExistingId_ReturnsProduct() {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        Product retrievedProduct = productService.getProductById(productId);

        assertNotNull(retrievedProduct);
        assertEquals(productId, retrievedProduct.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductById_NonExistingId_ThrowsException() {
        long nonExistingId = 999L;
        when(productRepository.findById(nonExistingId)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(nonExistingId));
        verify(productRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void getAllProducts_ReturnsListOfProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productRepository.findAll()).thenReturn(products);

        List<Product> retrievedProducts = productService.getAllProducts();

        assertEquals(products.size(), retrievedProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getAllProducts_Pageable_ReturnsPageOfProducts() {
        Page<Product> productPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<Product> retrievedProductPage = productService.getAllProducts(pageable);

        assertNotNull(retrievedProductPage);
        assertEquals(productPage, retrievedProductPage);
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void deleteAllProducts_CallsProductRepositoryDeleteAll() {
        productService.deleteAllProducts();
        verify(productRepository, times(1)).deleteAll();
    }

    @Test
    void deleteProduct_ExistingId_CallsProductRepositoryDeleteById() {
        long productId = 1L;
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }
}
