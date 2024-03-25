package com.cafe.com.cafe.management.Controller;

import com.cafe.com.cafe.management.dto.Categorydto;
import com.cafe.com.cafe.management.service.CategoryserviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryserviceImpl categoryservice;

    @PostMapping("/postcategory")
    public ResponseEntity<String> postcategory(@RequestBody Categorydto categorydto){
        return categoryservice.postcategory(categorydto);
    }

    @GetMapping("/getallcategory")
    public ResponseEntity<List<Categorydto>> getall(@RequestParam(required = false) String filtervalue){
        return categoryservice.getallcategories(filtervalue);
    }
    @PostMapping("/updatecategory")
    public ResponseEntity<String> updatecategory(@RequestBody Categorydto categorydto){
        return categoryservice.updatecategory(categorydto);
    }
}
