package br.com.compassuol.pb.challenge.msproducts.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_product")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    private String description;

    private String name;

    private String imgUrl;

    private BigDecimal price;
    @ManyToMany
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

}