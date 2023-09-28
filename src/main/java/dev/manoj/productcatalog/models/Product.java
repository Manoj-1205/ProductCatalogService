package dev.manoj.productcatalog.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseModel {
    private String title;
    private double price;
    private String description;
    private Category category;
    private String imageUrl;
    private Rating rating;
    private Boolean isDeleted;


}
