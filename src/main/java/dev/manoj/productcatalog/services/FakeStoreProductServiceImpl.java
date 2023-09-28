package dev.manoj.productcatalog.services;


import dev.manoj.productcatalog.dtos.FakeStoreProductDto;
import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.models.Category;
import dev.manoj.productcatalog.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class FakeStoreProductServiceImpl implements ProductService {

    //Rest Template Builder is used to create instance of rest template
    //and using rest template we can do a http call to 3rd party apis
    private RestTemplateBuilder restTemplateBuilder;

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod,String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate=restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return (ResponseEntity)restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto){
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        return Product.builder()
                .title(fakeStoreProductDto.getTitle())
                .price(fakeStoreProductDto.getPrice())
                .imageUrl(fakeStoreProductDto.getImage())
                .category(category)
                .build();
    }

    public FakeStoreProductDto convertProductToFakeStoreDto(Product product){
        return FakeStoreProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImageUrl())
                .category(product.getCategory().getName())
                .build();
    }
    @Override
    public  List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> ProductsArray = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );
        List<Product> productList = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : ProductsArray.getBody()){
            Product product = Product.builder()
                    .title(fakeStoreProductDto.getTitle())
                    .description(fakeStoreProductDto.getDescription())
                    .price(fakeStoreProductDto.getPrice())
                    .imageUrl(fakeStoreProductDto.getImage())
                    .rating(fakeStoreProductDto.getRating().toRating())
                    .build();
            productList.add(product);

        }
        return productList;
    }


        @Override
        public ResponseEntity<FakeStoreProductDto> getSingleProduct(Long productId) {
            //Created a rest template instance to call fake store api
            RestTemplate restTemplate = restTemplateBuilder.build();
            //It will return the response entity of the product DTO from the API call
            ResponseEntity<FakeStoreProductDto> fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, productId);
            System.out.println("Product DTO id : "+fakeStoreProductDto.getBody().getId());
            return fakeStoreProductDto;
        }

    @Override
    public FakeStoreProductDto addNewProduct(FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto1 = restTemplate.postForEntity("https://fakestoreapi.com/products",
                fakeStoreProductDto, //Request Body
                FakeStoreProductDto.class //Return type class
                );

        return fakeStoreProductDto1.getBody();
    }

    @Override
    //Put request
    public Product replaceProduct(Long productId, Product product) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = convertProductToFakeStoreDto(product);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
                HttpMethod.PUT,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );

        return convertFakeStoreProductDtoToProduct(fakeStoreProductDtoResponseEntity.getBody());
    }

    @Override
    //PATCH Request
    public Product updateProduct(Long productId, Product product) {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(HttpComponentsClientHttpRequestFactory.class).build();
        FakeStoreProductDto fakeStoreProductDto = convertProductToFakeStoreDto(product);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
                HttpMethod.PATCH,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDtoResponseEntity.getBody());
    }

    @Override

    public Product deleteProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = getSingleProduct(productId).getBody();
        Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        product.setIsDeleted(true);
        return product;
    }

    //RequestForEntity methods

}

