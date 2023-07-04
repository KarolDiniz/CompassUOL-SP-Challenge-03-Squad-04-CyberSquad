package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.domain.Category;
import br.com.compassuol.pb.challenge.msproducts.domain.Product;
import br.com.compassuol.pb.challenge.msproducts.dto.CategoryDTO;
import br.com.compassuol.pb.challenge.msproducts.dto.ProductDTO;
import br.com.compassuol.pb.challenge.msproducts.repository.CategoryRepository;
import br.com.compassuol.pb.challenge.msproducts.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        setCategories(productDTO, product);
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = getProductById(id);
        BeanUtils.copyProperties(productDTO, product);
        setCategories(productDTO, product);
        return productRepository.save(product);
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
            product.setCategories(null);
        }
    }
}
