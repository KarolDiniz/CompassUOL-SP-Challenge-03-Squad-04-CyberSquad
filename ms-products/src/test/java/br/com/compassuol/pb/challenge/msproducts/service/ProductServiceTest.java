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
        // Configuração padrão do comportamento do mock para o método findAll()
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
    }
    @Test
    void createProduct_ValidData_ReturnsProduct() {
        // Criação de dados de teste válidos
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setCategories(new ArrayList<>());

        // Configuração do comportamento do mock para o método save()
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId(1L); // Define um ID para o produto salvo
            return savedProduct;
        });

        // Execução do método a ser testado
        Product savedProduct = productService.createProduct(productDTO);

        // Verificação dos resultados
        assertNotNull(savedProduct.getId());
        assertEquals(productDTO.getName(), savedProduct.getName());
        assertNotNull(savedProduct.getCategories()); // Verifica se a lista de categorias não é nula
        assertTrue(savedProduct.getCategories().isEmpty()); // Verifica se a lista de categorias está vazia
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById_ExistingId_ReturnsProduct() {
        // Criação de dados de teste
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        // Execução do método a ser testado
        Product retrievedProduct = productService.getProductById(productId);

        // Verificação dos resultados
        assertNotNull(retrievedProduct);
        assertEquals(productId, retrievedProduct.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductById_NonExistingId_ThrowsException() {
        // Criação de dados de teste
        long nonExistingId = 999L;
        when(productRepository.findById(nonExistingId)).thenReturn(java.util.Optional.empty());

        // Execução do método a ser testado e verificação da exceção
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(nonExistingId));
        verify(productRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void getAllProducts_ReturnsListOfProducts() {
        // Criação de dados de teste
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productRepository.findAll()).thenReturn(products);

        // Execução do método a ser testado
        List<Product> retrievedProducts = productService.getAllProducts();

        // Verificação dos resultados
        assertEquals(products.size(), retrievedProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getAllProducts_Pageable_ReturnsPageOfProducts() {
        // Criação de dados de teste
        Page<Product> productPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // Execução do método a ser testado
        Page<Product> retrievedProductPage = productService.getAllProducts(pageable);

        // Verificação dos resultados
        assertNotNull(retrievedProductPage);
        assertEquals(productPage, retrievedProductPage);
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void deleteAllProducts_CallsProductRepositoryDeleteAll() {
        // Execução do método a ser testado
        productService.deleteAllProducts();

        // Verificação do comportamento do mock
        verify(productRepository, times(1)).deleteAll();
    }

    @Test
    void deleteProduct_ExistingId_CallsProductRepositoryDeleteById() {
        // Criação de dados de teste
        long productId = 1L;

        // Execução do método a ser testado
        productService.deleteProduct(productId);

        // Verificação do comportamento do mock
        verify(productRepository, times(1)).deleteById(productId);
    }
}
