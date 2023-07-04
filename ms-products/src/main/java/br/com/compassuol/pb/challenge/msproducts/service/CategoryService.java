package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.domain.Category;
import br.com.compassuol.pb.challenge.msproducts.dto.CategoryDTO;
import br.com.compassuol.pb.challenge.msproducts.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
