package com.Blogging.Blogging.Controller;


import com.Blogging.Blogging.Services.CategoryService;
import com.Blogging.Blogging.payloads.ApiResponse;
import com.Blogging.Blogging.payloads.CategoryDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    @PostMapping("/create/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid  @RequestBody CategoryDto categoryDto)
    {
        CategoryDto   cat = categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(cat, HttpStatus.CREATED);
    }


    @PutMapping("/update/{CategoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("CategoryId") Integer CategoryId)
    {
         CategoryDto updatecat =  categoryService.updateCategory(categoryDto,CategoryId);

         return  new ResponseEntity<CategoryDto>(updatecat,HttpStatus.OK);
    }



    @DeleteMapping("/delete/{CategoryId}")
    public ResponseEntity<ApiResponse> deletCategory(@PathVariable Integer CategoryId)
    {
             categoryService.deletCategory(CategoryId);
             ApiResponse apiResponse  = new ApiResponse("Category Deleted Successfully",true);

             return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/category/{CategoryId}")
    public ResponseEntity<CategoryDto> getCategoryDetails(@PathVariable("CategoryId") Integer CategoryDetails)
    {
        CategoryDto cat =  categoryService.getCategory(CategoryDetails);

         return new ResponseEntity<CategoryDto>(cat,HttpStatus.OK);
    }

    @GetMapping("/category/all")
    public ResponseEntity<List<CategoryDto>> getAllCategory()
    {
        List<CategoryDto> allcat = categoryService.getallCategory();

        return new ResponseEntity<List<CategoryDto>>(allcat,HttpStatus.OK);
    }


}
