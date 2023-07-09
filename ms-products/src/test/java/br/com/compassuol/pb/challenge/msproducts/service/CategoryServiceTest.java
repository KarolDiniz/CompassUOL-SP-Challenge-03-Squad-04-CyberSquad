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
import java.util.Optional;

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
        // Criação de dados de teste
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");

        // Configuração do mock
        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Test Category");
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Execução do método a ser testado
        Category createdCategory = categoryService.createCategory(categoryDTO);

        // Verificação dos resultados
        assertNotNull(createdCategory);
        assertEquals(savedCategory.getId(), createdCategory.getId());
        assertEquals(savedCategory.getName(), createdCategory.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void createCategory_NullData_ThrowsException() {
        // Execução do método a ser testado
        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(null));

        // Verificação do comportamento do mock
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void getAllCategories_ReturnsListOfCategories() {
        // Criação de dados de teste
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        // Configuração do mock
        when(categoryRepository.findAll()).thenReturn(categories);

        // Execução do método a ser testado
        List<Category> retrievedCategories = categoryService.getAllCategories();

        // Verificação dos resultados
        assertNotNull(retrievedCategories);
        assertEquals(2, retrievedCategories.size());
        assertEquals(category1.getId(), retrievedCategories.get(0).getId());
        assertEquals(category1.getName(), retrievedCategories.get(0).getName());
        assertEquals(category2.getId(), retrievedCategories.get(1).getId());
        assertEquals(category2.getName(), retrievedCategories.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }
}