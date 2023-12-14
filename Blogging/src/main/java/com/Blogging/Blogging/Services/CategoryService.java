package com.Blogging.Blogging.Services;

import com.Blogging.Blogging.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categorId);


    public void deletCategory(Integer categoryId);

    public  CategoryDto getCategory(Integer categorId);

    public List<CategoryDto> getallCategory();
}
