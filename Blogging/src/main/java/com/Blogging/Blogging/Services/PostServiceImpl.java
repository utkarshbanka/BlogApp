package com.Blogging.Blogging.Services;

import com.Blogging.Blogging.Exception.ResourceNotFoundException;
import com.Blogging.Blogging.Model.Category;
import com.Blogging.Blogging.Model.Comment;
import com.Blogging.Blogging.Model.Post;
import com.Blogging.Blogging.Model.User;
import com.Blogging.Blogging.Repositry.CategoryRepo;
import com.Blogging.Blogging.Repositry.CommentRepo;
import com.Blogging.Blogging.Repositry.PostRepo;
import com.Blogging.Blogging.Repositry.UserRepo;
import com.Blogging.Blogging.payloads.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl  implements  PostService{


    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private CategoryRepo categoryRepo;


    @Autowired
   private CommentRepo commentRepo;

    @Override
    public PostDto createPost(PostDto postdto, Integer userId, Integer CategorId) {


        User user  = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", Long.parseLong(userId.toString())));

        Category cat = categoryRepo.findById(CategorId).orElseThrow(()->new ResourceNotFoundException("Category","Id", Long.parseLong(CategorId.toString())));

        Post post = modelMapper.map(postdto,Post.class);
        post.setImageName("default.png");
        post.setCategory(cat);
        post.setAddedDate(new Date());
        post.setTitle(postdto.getTitle());
        post.setContent(postdto.getContent());
        post.setUser(user);
        postRepo.save(post);

        PostDto postdet = modelMapper.map(post, PostDto.class);
        postdet.setUserDto(modelMapper.map(user, UserDto.class));
        postdet.setCategoryDto(modelMapper.map(cat, CategoryDto.class));
        return postdet;

    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id) {

        Post post = postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post Does Not Exit","Id", id.longValue()));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

         postRepo.save(post);
        PostDto postdto = modelMapper.map(post,PostDto.class);


        return postdto;
    }

    @Override
    public void deletPost(Integer id) {

        Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post Does Not Exit With","Id",id.longValue() ));

        postRepo.delete(post);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortby,String sortdirection) {

        Sort sort =  (sortdirection.equalsIgnoreCase("asc"))?Sort.by(sortby).ascending():Sort.by(sortby).descending();


        Pageable pageable =  PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagpost = postRepo.findAll(pageable);


        List<Post> allpost = pagpost.getContent();
        List<PostDto> posttodto = allpost.stream().map(post->{

            PostDto po = modelMapper.map(post,PostDto.class);

            if(post.getUser()!=null)
            {
                po.setUserDto(modelMapper.map(post.getUser(), UserDto.class));
            }
            if(post.getCategory()!=null)
            {
                po.setCategoryDto(modelMapper.map(post.getCategory(),CategoryDto.class));
            }

            return po;
        }).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posttodto);
        postResponse.setPageNumber(pagpost.getNumber());
        postResponse.setLastpage(pagpost.isLast());
        postResponse.setPageSize(pagpost.getSize());
        postResponse.setTotalElement(pagpost.getTotalElements());
        postResponse.setTotalPages(pagpost.getTotalPages());


        return postResponse;
    }

    @Override
    public PostDto getPostbyid(Integer id) {

        Post post = postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",Long.valueOf(id.toString())));

        Optional<User> user =  userRepo.findById(post.getUser().getId());
        Optional<Category> cat = categoryRepo.findById(post.getCategory().getCategoryId());




        PostDto pos = modelMapper.map(post,PostDto.class);
        if(user.get()!=null && cat.get()!=null)
        {
            pos.setUserDto(modelMapper.map(user.get(), UserDto.class));
            pos.setCategoryDto(modelMapper.map(cat.get(),CategoryDto.class));
            List<Comment> comm = commentRepo.findByPost(post);

            Set<CommentDto> commentdto = comm.stream().map(com->modelMapper.map(com,CommentDto.class)).collect(Collectors.toSet());

            pos.setComments(commentdto);
//            pos.getComments().add()
        }


        return pos;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer catId) {

        Category category =  categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Catorgery","Id",  Long.parseLong(catId.toString())));
        List<Post> allpost = postRepo.findByCategory(category);
        List<PostDto> postto = allpost.stream().map(cat-> {
          PostDto po = modelMapper.map(cat,PostDto.class);
          po.setCategoryDto(modelMapper.map(category,CategoryDto.class));
//          po.setUserDto(modelMapper.map(cat.getUser(), UserDto.class));



            return po;
        }).collect(Collectors.toList());


        return postto;
    }

    @Override
    public List<PostDto> getpostbyUser(Integer userid) {

        User user = userRepo.findById(userid).orElseThrow(()->new ResourceNotFoundException("User", "Id", Long.parseLong(userid.toString())));

        List<Post> allpost = postRepo.findByUser(user);
//         List<Comment>    =
        List<PostDto> postodto =  allpost.stream().map(cat-> {

            PostDto po = modelMapper.map(cat,PostDto.class);
            po.setUserDto(modelMapper.map(user, UserDto.class));
            po.setCategoryDto(modelMapper.map(cat.getCategory(),CategoryDto.class));
//            po.getComments().add(modelMapper.map())

            List<Comment> comm = commentRepo.findByPost(cat);

            Set<CommentDto> commentdto = comm.stream().map(com->modelMapper.map(com,CommentDto.class)).collect(Collectors.toSet());

             po.setComments(commentdto);



            return po;
        }).collect(Collectors.toList());

        return postodto;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {

        List<Post> post = postRepo.findBytitleContaining(keyword);
        List<PostDto> allpost = post.stream().map(poste->modelMapper.map(poste,PostDto.class)).collect(Collectors.toList());

        return allpost;
    }
}
