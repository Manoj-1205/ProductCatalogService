package dev.manoj.productcatalog.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseModel {
    private String name;
    private String description;
    private List<Product> products;
}
