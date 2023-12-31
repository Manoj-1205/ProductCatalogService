package dev.manoj.productcatalog.clients.fakeStoreApi;

import dev.manoj.productcatalog.dtos.RatingDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class FakeStoreProductDto {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
    private RatingDTO rating;


}
