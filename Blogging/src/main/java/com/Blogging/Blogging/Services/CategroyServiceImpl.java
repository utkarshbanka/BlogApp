package com.Blogging.Blogging.Services;

import com.Blogging.Blogging.Exception.ResourceNotFoundException;
import com.Blogging.Blogging.Model.Category;
import com.Blogging.Blogging.Repositry.CategoryRepo;
import com.Blogging.Blogging.payloads.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategroyServiceImpl  implements  CategoryService{


    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category categorydt  = modelMapper.map(categoryDto,Category.class);

        Category  save =  categoryRepo.save(categorydt);

        CategoryDto re = modelMapper.map(save,CategoryDto.class);

        return re;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categorId) {

        Category get = categoryRepo.findById(categorId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categorId.longValue()));

        get.setCategoryTitle(categoryDto.getCategorytitle());
        get.setCategroyDescription(categoryDto.getCategroyDescription());

          Category updatecat =  categoryRepo.save(get);


        return  modelMapper.map(get,CategoryDto.class);
    }

    @Override
    public void deletCategory(Integer categoryId) {

       Category getCat = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id", categoryId.longValue())) ;

         categoryRepo.delete(getCat);
    }

    @Override
    public CategoryDto getCategory(Integer categorId) {

        Category cat = categoryRepo.findById(categorId).orElseThrow(()->new ResourceNotFoundException("Category","Id", categorId.longValue()));


        return modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getallCategory() {
        List<Category> allcat = categoryRepo.findAll();
        List<CategoryDto> allca = allcat.stream().map(cat -> modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());


        return allca;
    }







}
