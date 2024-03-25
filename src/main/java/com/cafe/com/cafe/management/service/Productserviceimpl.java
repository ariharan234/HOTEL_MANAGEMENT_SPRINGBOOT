package com.cafe.com.cafe.management.service;

import com.cafe.com.cafe.management.Entity.CategoryEntity;
import com.cafe.com.cafe.management.Entity.ProductEntity;
import com.cafe.com.cafe.management.JWT.JWTFilter;
import com.cafe.com.cafe.management.dto.Productdto;
import com.cafe.com.cafe.management.repository.Categoryrepository;
import com.cafe.com.cafe.management.repository.Productrepository;
import com.cafe.com.cafe.management.service.impl.Productserviceinterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Productserviceimpl implements Productserviceinterface {

    @Autowired
    private Productrepository productrepository;
    @Autowired
    private JWTFilter jwtFilter;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Categoryrepository categoryrepository;
    @Override
    public ResponseEntity<String> addproduct(Productdto productdto) {
        if(jwtFilter.isAdmin()){
            ProductEntity productEntity=modelMapper.map(productdto,ProductEntity.class);
            Optional<CategoryEntity> categoryEntity= categoryrepository.findById(productdto.getCategoryEntity().getId());
            productEntity.setCategoryEntity(categoryEntity.get());
            productEntity.setStatus("true");
            productrepository.save(productEntity);
            return ResponseEntity.ok("Product added successfully");
        }
        else{
            return new ResponseEntity<>("Only Admin can add product", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<List<Productdto>> getallproducts() throws Exception {

            List<ProductEntity> list=productrepository.findAll();
            List<Productdto> list1=list.stream().map(l->modelMapper.map(l,Productdto.class)).toList();
            return new ResponseEntity<>(list1,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> updateproduct(Productdto productdto) {
        if(jwtFilter.isAdmin()){
            Optional<ProductEntity> optional=productrepository.findById(productdto.getId());
            if(!optional.isEmpty()){
                ProductEntity product=modelMapper.map(productdto,ProductEntity.class);
                product.setStatus(optional.get().getStatus());
                productrepository.save(product);
                return ResponseEntity.ok("Product updated successfully");
            }
            else{
                return new ResponseEntity<>("Id does not exist",HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>("Only Admin can update product",HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<String> deleteproductbyid(Integer id) {
        if(jwtFilter.isAdmin()){
            Optional<ProductEntity> optional=productrepository.findById(id);
            if(!optional.isEmpty()){
                productrepository.deleteById(id);
                return ResponseEntity.ok("Product is deleted");
            }
            else{
                return new ResponseEntity<>("Id does not exist",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("Only admin can delete the product",HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<String> updatestatus(Map<String, String> map) {
        if(jwtFilter.isAdmin()){
            Optional<ProductEntity> optional=productrepository.findById(Integer.parseInt(map.get("id")));
            if(!optional.isEmpty()){
                optional.get().setStatus(map.get("status"));
                productrepository.save(optional.get());
                return ResponseEntity.ok("Status updated");
            }
            else{
                return new ResponseEntity<>("Id does not exist",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("Only Admin can update status of product",HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Productdto> getproductbyid(Integer id) {
        Optional<ProductEntity> optional=productrepository.findById(id);
        if(!optional.isEmpty()){
            Productdto productdto=modelMapper.map(optional,Productdto.class);
            return new ResponseEntity<>(productdto,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<Productdto>> getproductbycategoryid(Integer id) {
        List<ProductEntity> list=productrepository.findAll();
        List<Productdto> list1= list.stream()
                .filter(l->l.getCategoryEntity().getId().equals(id))
                .map(l->modelMapper.map(l,Productdto.class))
                .toList();
        return new ResponseEntity<>(list1,HttpStatus.OK);

    }
}
