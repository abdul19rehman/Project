package com.khan.Ecom_Project.Services;

import com.khan.Ecom_Project.model.Product;
import com.khan.Ecom_Project.reop.ProdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
@Service
public class ProdService {
   @Autowired

   private ProdRepo repo;

   public List<Product> getProducts(){
      return repo.findAll();
   }

   public Product getProductById(int id) {
      return repo.findById(id).get();
   }

   public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
      product.setImageDate(imageFile.getBytes());
      product.setImageType(imageFile.getContentType());
      product.setImageName(imageFile.getOriginalFilename());




      return repo.save(product);
   }

   public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
      product.setImageName(imageFile.getOriginalFilename());
      product.setImageType(imageFile.getContentType());
      product.setImageDate(imageFile.getBytes());
      return repo.save(product);
   }

   public void deleteProduct(int id) {
         repo.deleteById(id);
      }

      public List<Product> searchProducts(String keyword) {
         return repo.searchProducts(keyword);
      }

}

