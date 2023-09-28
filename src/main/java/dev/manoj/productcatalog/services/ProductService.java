package dev.manoj.productcatalog.services;


import dev.manoj.productcatalog.dtos.FakeStoreProductDto;
import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    ResponseEntity<FakeStoreProductDto> getSingleProduct(Long productId);

    FakeStoreProductDto addNewProduct(FakeStoreProductDto product);

    /*
    Product object has only those fields filled which need to be updated.
    Everything else is null
     */
    Product replaceProduct(Long productId, Product product);
    Product updateProduct(Long productId, Product product);
    // if (product.getImageUrl() != null) {
    //
    // }

    Product deleteProduct(Long productId);
}

// update product with id 123
// {
//   name: iPhone 15
// }
