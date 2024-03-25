package com.cafe.com.cafe.management.Controller;

import com.cafe.com.cafe.management.dto.Productdto;
import com.cafe.com.cafe.management.service.Productserviceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private Productserviceimpl productserviceimpl;

    @PostMapping("/addproduct")
    public ResponseEntity<String> addproduct(@RequestBody Productdto productdto){
        return productserviceimpl.addproduct(productdto);
    }
    @GetMapping("/getallproduct")
    public ResponseEntity<List<Productdto>> getproduct() throws Exception {
        return productserviceimpl.getallproducts();
    }

    @PostMapping("/updateproduct")
    public ResponseEntity<String> updateproduct(@RequestBody(required = true)Productdto productdto){
        return productserviceimpl.updateproduct(productdto);

    }
    @PostMapping("/deletebyid/{id}")
    public ResponseEntity<String> deletebyid(@PathVariable Integer id){
        return productserviceimpl.deleteproductbyid(id);
    }

    @PostMapping("/updateproductstatus")
    public ResponseEntity<String> updatestatus(@RequestBody  Map<String,String> map){
        return productserviceimpl.updatestatus(map);
    }

   @GetMapping("/getproductbyid/{id}")
    public ResponseEntity<Productdto> getproductbyid(@PathVariable Integer id){
        return productserviceimpl.getproductbyid(id);
   }

   @GetMapping("/getproductbycategory/{id}")
    public ResponseEntity<List<Productdto>> getproductbycategory(@PathVariable Integer id){
        return productserviceimpl.getproductbycategoryid(id);
   }


}
