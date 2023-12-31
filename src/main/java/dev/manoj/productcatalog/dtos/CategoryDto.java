package dev.manoj.productcatalog.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class CategoryDto {
    private String name;
    private String description;
}
