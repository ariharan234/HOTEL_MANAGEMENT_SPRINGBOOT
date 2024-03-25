package com.cafe.com.cafe.management.service;

import com.cafe.com.cafe.management.Entity.CategoryEntity;
import com.cafe.com.cafe.management.JWT.JWTFilter;
import com.cafe.com.cafe.management.dto.Categorydto;
import com.cafe.com.cafe.management.repository.Categoryrepository;
import com.cafe.com.cafe.management.service.impl.Categoryserviceinterface;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryserviceImpl implements Categoryserviceinterface {

    @Autowired
    private Categoryrepository categoryrepository;
    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<String> postcategory(Categorydto categorydto) {
        if(jwtFilter.isAdmin()){

            Optional<CategoryEntity> entity=categoryrepository.findByCategoryName(categorydto.getCategoryName());
            if(!entity.isPresent()){
                CategoryEntity entity1=modelMapper.map(categorydto,CategoryEntity.class);
                categoryrepository.save(entity1);
                return ResponseEntity.ok("Category successfully added");
            }
            else{
                return new ResponseEntity<>("Category name already exists",HttpStatus.BAD_REQUEST);
            }

        }
        else{
            return new ResponseEntity<>("Only Admin can add category", HttpStatus.UNAUTHORIZED);
        }
    }

//    private boolean validatecategory(Categorydto categorydto, boolean validateid) {
//        if(!categorydto.getCategoryName().isEmpty()){
//            if(validateid){
//                return true;
//            }else if(!validateid){
//                return true;
//            }
//        }
//
//    }

    @Override
    public ResponseEntity<List<Categorydto>> getallcategories(String filtervalue) {
        if (filtervalue != null && !filtervalue.isEmpty() && filtervalue.equalsIgnoreCase("true")) {
            List<CategoryEntity> categorylist=categoryrepository.getallcategory();
            List<Categorydto> dtolist=categorylist.stream().map(c->modelMapper.map(c,Categorydto.class)).collect(Collectors.toList());
            return new ResponseEntity<>(dtolist,HttpStatus.OK);
        }

        else{
            List<CategoryEntity> categorylist=categoryrepository.findAll();
            List<Categorydto> dtolist=categorylist.stream().map(c->modelMapper.map(c,Categorydto.class)).collect(Collectors.toList());
            return new ResponseEntity<>(dtolist,HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<String> updatecategory(Categorydto categorydto) {
         if(jwtFilter.isAdmin()){
             Optional<CategoryEntity> entity=categoryrepository.findById(categorydto.getId());
             if(entity.isPresent()){
                 entity.get().setCategoryName(categorydto.getCategoryName());
                 categoryrepository.save(entity.get());
                 return ResponseEntity.ok("Category is Updated");

             }
             else{
                 return new ResponseEntity<>("Id does not exist",HttpStatus.BAD_REQUEST);
             }

         }
         else {
             return new ResponseEntity<>("Only Admin can update category",HttpStatus.UNAUTHORIZED);
         }
    }
}
