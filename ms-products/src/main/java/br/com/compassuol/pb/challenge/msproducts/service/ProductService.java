package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.domain.Category;
import br.com.compassuol.pb.challenge.msproducts.domain.Product;
import br.com.compassuol.pb.challenge.msproducts.dto.CategoryDTO;
import br.com.compassuol.pb.challenge.msproducts.dto.ProductDTO;
import br.com.compassuol.pb.challenge.msproducts.repository.CategoryRepository;
import br.com.compassuol.pb.challenge.msproducts.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public Product createProduct(ProductDTO productDTO) {
        log.info("Creating a new product: {}", productDTO);
        var product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        setCategories(productDTO, product);
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }


    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        log.info("Updating product with id: {}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        Product product = optionalProduct.get();
        BeanUtils.copyProperties(productDTO, product);
        setCategories(productDTO, product);
        return productRepository.save(product);
    }



    public Page<Product> getAllProducts(Pageable pageable) {
        log.info("Fetching all products with pagination");
        return productRepository.findAll(pageable);
    }

    public void deleteAllProducts() {
        log.info("Deleting all products");
        productRepository.deleteAll(); }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        productRepository.deleteById(id);
    }

    private void setCategories(ProductDTO productDTO, Product product) {
        List<CategoryDTO> categoryDTOs = productDTO.getCategories();
        if (categoryDTOs != null && !categoryDTOs.isEmpty()) {
            List<Long> categoryIds = categoryDTOs.stream()
                    .map(CategoryDTO::getId)
                    .collect(Collectors.toList());
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            if (categories.size() != categoryIds.size()) {
                throw new IllegalArgumentException("One or more categories not found");
            }
            product.setCategories(categories);
        } else {
            product.setCategories(new ArrayList<>()); // Define uma lista vazia
        }
    }


}
