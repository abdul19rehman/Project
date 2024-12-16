package com.khan.Ecom_Project.Controller;

import com.khan.Ecom_Project.Services.ProdService;
import com.khan.Ecom_Project.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin

@RestController
@RequestMapping("/api")
public class ProdController {
   @Autowired

   private ProdService service;
   @RequestMapping("/products")
   public ResponseEntity<List<Product>> getProducts(){

      return new ResponseEntity<>(service.getProducts(),HttpStatus.OK);
   }
   @RequestMapping("/product/{id}")
   public ResponseEntity<Product> getProductById(@PathVariable  int id){
      Product product=service.getProductById(id);
      if(product!=null)
      return new ResponseEntity<>( product,HttpStatus.OK);
      else
         return new ResponseEntity<>( product,HttpStatus.NOT_FOUND);
   }

   @PostMapping("/product")
   public ResponseEntity<?> addProduct(@RequestPart Product product,
   @RequestPart MultipartFile imageFile){
      try{
         Product product1=service.addProduct(product,imageFile);
         return  new ResponseEntity<>(product1,HttpStatus.CREATED);
      }
      catch (Exception e){
         return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }


   @GetMapping("/product/{productId}/image")
   public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){

      Product product = service.getProductById(productId);
      byte[] imageFile = product.getImageDate();

      return ResponseEntity.ok()
           .contentType(MediaType.valueOf(product.getImageType()))
           .body(imageFile);

   }


   @PutMapping("/product/{id}")
   public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
                                               @RequestPart MultipartFile imageFile ) throws IOException {

      Product product1= service.updateProduct(id,product,imageFile);
      if(product1!=null){
         return new ResponseEntity<>("Updated ",HttpStatus.OK);
      }
      else {
         return new ResponseEntity<>("Product not updated" , HttpStatus.BAD_REQUEST);
      }
   }


   @DeleteMapping("/product/{id}")
      public  ResponseEntity<String> deleteProduct(@PathVariable int id){
      Product product1=service.getProductById(id);
      if(product1!=null){
          service.deleteProduct(id);
          return  new ResponseEntity<>("Deleted", HttpStatus.OK);
      }
      else {
         return  new ResponseEntity<>("Couldn't Deleted", HttpStatus.NOT_FOUND);


      }

   }

   @GetMapping("/products/search")
   public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
      List<Product> products = service.searchProducts(keyword);
      System.out.println("searching with " + keyword);
      return new ResponseEntity<>(products, HttpStatus.OK);
   }

}
