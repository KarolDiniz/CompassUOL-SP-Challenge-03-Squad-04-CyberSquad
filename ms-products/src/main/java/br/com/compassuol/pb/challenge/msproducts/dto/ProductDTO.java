package br.com.compassuol.pb.challenge.msproducts.dto;
import br.com.compassuol.pb.challenge.msproducts.domain.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String date;
    private String description;
    private String name;
    private String imgUrl;
    private BigDecimal price;
    private List<CategoryDTO> categories;
}