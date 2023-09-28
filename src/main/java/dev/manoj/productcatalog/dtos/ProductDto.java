package dev.manoj.productcatalog.dtos;

import lombok.Builder;
import lombok.Getter;
import jakarta.annotation.Nullable;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
    private RatingDTO rating;
//    private String spec;
    private Boolean isDeleted;

}
