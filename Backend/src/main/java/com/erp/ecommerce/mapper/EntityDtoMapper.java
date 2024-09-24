package com.erp.ecommerce.mapper;

import com.erp.ecommerce.dto.*;
import com.erp.ecommerce.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {


    //user entity to DTO

    public UserDto mapUserToDtoBasic(User user){

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setName(user.getName());
        return userDto;

    }


    //Adress to DTO Basic

    public AdressDto mapAddressToDtoBasic(Adress adress){
        AdressDto adressDto = new AdressDto();
        adressDto.setId(adress.getId());
        adressDto.setCity(adress.getCity());
        adressDto.setStreet(adress.getStreet());
        adressDto.setState(adress.getState());
        adressDto.setCountry(adress.getCountry());
        adressDto.setZipCode(adress.getZipCode());
        return adressDto;

    }

    //Category to DTO basic

    public CategoryDto mapCategoryToDtoBasic(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    //OrderItem to DTO basic
    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreateAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

    //Product to DTO Basic
    public ProductDto mapProductToDtoBasic(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(productDto.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageURL());
        return productDto;
    }

    public UserDto mapUserToDtoPlusAddress(User user){
        UserDto userDto = mapUserToDtoBasic(user);
        if (user.getAdress() != null){
            AdressDto adressDto = mapAddressToDtoBasic(user.getAdress());
            userDto.setAdress(adressDto);

        }
        return userDto;
    }

    //orderItem to Dto plus product

    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem){
        OrderItemDto orderItemDto = mapOrderItemToDtoBasic(orderItem);
        if (orderItem.getProduct() != null){
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }

    //OrderItem to Dto plus product and user

    public OrderItemDto mapOrderItemDtoPlusProductAndUser(OrderItem orderItem){
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);
        if (orderItem.getUser() != null){
            UserDto userDto = mapUserToDtoPlusAddress(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }

    //User to DTO with Address and Order items History

    public UserDto mapUserToDtoPlusAddressAndOrderHistory(User user){
        UserDto userDto = mapUserToDtoPlusAddress(user);
        if (user.getOrderItemList() != null &&  !user.getOrderItemList().isEmpty()){
            userDto.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDtoPlusProduct)
                    .collect(Collectors.toList()));
        }

        return userDto;
    }

}
