package br.com.compassuol.pb.challenge.msproducts.controller;
import br.com.compassuol.pb.challenge.msproducts.domain.Category;
import br.com.compassuol.pb.challenge.msproducts.dto.CategoryDTO;
import br.com.compassuol.pb.challenge.msproducts.service.CategoryService;
import br.com.compassuol.pb.challenge.msproducts.service.CategoryServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoryControllerTest {


    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createCategoryTest() {
        // Criação de dados de teste
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");

        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        // Configuração do comportamento do mock
        when(categoryService.createCategory(categoryDTO)).thenReturn(category);

        // Execução do método a ser testado
        ResponseEntity<Category> response = categoryController.createCategory(categoryDTO);

        // Verificação dos resultados
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).createCategory(categoryDTO);
    }

    @Test
    void getAllCategoriesTest() {
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

        // Configuração do comportamento do mock
        when(categoryService.getAllCategories()).thenReturn(categories);

        // Execução do método a ser testado
        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        // Verificação dos resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void createCategory_InvalidData_ReturnsBadRequest() {
        // Criação de dados de teste inválidos
        CategoryDTO categoryDTO = new CategoryDTO();
        // Não definir o nome da categoria

        // Execução do método a ser testado
        ResponseEntity<Category> response = categoryController.createCategory(categoryDTO);

        // Verificação dos resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(categoryService, never()).createCategory(categoryDTO);
    }

    @Test
    void getAllCategories_NoCategories_ReturnsEmptyList() {
        // Configuração do comportamento do mock
        when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());

        // Execução do método a ser testado
        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        // Verificação dos resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(categoryService, times(1)).getAllCategories();
    }

}


