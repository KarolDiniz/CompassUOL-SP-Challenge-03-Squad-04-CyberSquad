package br.com.compassuol.pb.challenge.msproducts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProductDTO {

    private Long id;

    private LocalDate date;

    @NotBlank
    private String description;

    @NotBlank
    private String name;

    private String imgUrl;
    @NotNull
    private BigDecimal price;

    private List<CategoryDTO> categories;
}