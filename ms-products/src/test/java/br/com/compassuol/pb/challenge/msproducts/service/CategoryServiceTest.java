package br.com.compassuol.pb.challenge.msproducts.service;
import br.com.compassuol.pb.challenge.msproducts.domain.Category;
import br.com.compassuol.pb.challenge.msproducts.dto.CategoryDTO;
import br.com.compassuol.pb.challenge.msproducts.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;
    @Test
    void createCategory_ValidData_ReturnsCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Test Category");
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        Category createdCategory = categoryService.createCategory(categoryDTO);

        assertNotNull(createdCategory);
        assertEquals(savedCategory.getId(), createdCategory.getId());
        assertEquals(savedCategory.getName(), createdCategory.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void createCategory_NullData_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(null));
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void getAllCategories_ReturnsListOfCategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> retrievedCategories = categoryService.getAllCategories();

        assertNotNull(retrievedCategories);
        assertEquals(2, retrievedCategories.size());
        assertEquals(category1.getId(), retrievedCategories.get(0).getId());
        assertEquals(category1.getName(), retrievedCategories.get(0).getName());
        assertEquals(category2.getId(), retrievedCategories.get(1).getId());
        assertEquals(category2.getName(), retrievedCategories.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }
}