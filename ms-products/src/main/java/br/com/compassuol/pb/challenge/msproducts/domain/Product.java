package br.com.compassuol.pb.challenge.msproducts.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "tb_product")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private LocalDate date;

    @NotBlank
    @Size(min = 5)
    @Column(name = "description", unique = true)
    private String description;

    @NotBlank
    @Size(min = 3)
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "imgUrl")
    private String imgUrl;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @ManyToMany
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

}