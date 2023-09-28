package dev.manoj.productcatalog.controllers;


import dev.manoj.productcatalog.dtos.FakeStoreProductDto;
import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.models.Category;
import dev.manoj.productcatalog.models.Product;
import dev.manoj.productcatalog.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
//@AllArgsConstructor
//@NoArgsConstructor

public class ProductController {
    private ProductService productService;
    public ProductController( ProductService productService) {
        this.productService = productService;
    }
    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable Long productId) {
        MultiValueMap<String, String> headers=new LinkedMultiValueMap<>();
        headers.add("auth-token","permission-granted");
        headers.add("auth-token","Temporary access");

        FakeStoreProductDto productDto = productService.getSingleProduct(productId).getBody();
        Product product = Product.builder()

                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImage())
                .rating(productDto.getRating().toRating())
                .build();
        product.setId(productDto.getId());
        ResponseEntity<Product> responseEntity = new ResponseEntity<>(product,headers, HttpStatus.OK);

        return responseEntity;
//        return productService.getSingleProduct(productId).getBody();
    }


    @PostMapping()
    public ResponseEntity<Product> addNewProduct(@RequestBody FakeStoreProductDto productDtoObj) {
        FakeStoreProductDto productDto = productService.addNewProduct(productDtoObj);
        Product postProduct = Product.builder()
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImage())
                .build();
        postProduct.setId(productDtoObj.getId());
        return new ResponseEntity<>(postProduct, HttpStatus.OK);

    }

    @PutMapping("/{productId}")
    public ProductDto replaceProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) {
        Product product = convertProductDtoToProduct(productDto);
        return convertProductToProductDto(productService.replaceProduct(productId, product));

    }
    @PatchMapping("/{productId}")
    public ProductDto updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) {

        Product product = productService.updateProduct(productId, convertProductDtoToProduct(productDto));
        System.out.println("Product Image "+product.getImageUrl());
        return convertProductToProductDto(product);

    }

    @DeleteMapping("/{productId}")
    public ProductDto deleteProduct(@PathVariable("productId") Long productId) {
        Product product=productService.deleteProduct(productId);
        ProductDto responseProductDto = convertProductToProductDto(product);
        responseProductDto.setIsDeleted(product.getIsDeleted());
        return responseProductDto;
    }


    public Product convertProductDtoToProduct(ProductDto productDto){
        Category category=new Category();
        category.setName(productDto.getCategory());
        return Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .imageUrl(productDto.getImage())
                .category(category)
                .build();
    }
    public ProductDto convertProductToProductDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImageUrl())
                .category(product.getCategory().getName())
                .build();
    }
}
