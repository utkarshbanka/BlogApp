package com.Blogging.Blogging.Controller;

import com.Blogging.Blogging.Services.FileService;
import com.Blogging.Blogging.Services.PostService;
import com.Blogging.Blogging.payloads.ApiResponse;
import com.Blogging.Blogging.payloads.ImageResponse;
import com.Blogging.Blogging.payloads.PostDto;
import com.Blogging.Blogging.payloads.PostResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {


    @Autowired
    private PostService postService;


    @Autowired
    private FileService fileService;
   @Value("${project.image}")
    private String path;

    @PostMapping("/userId/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto>  savepost(@RequestBody PostDto postDto , @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categorId)
    {

        PostDto savepost = postService.createPost(postDto,userId,categorId);
        return new ResponseEntity<PostDto>(savepost, HttpStatus.CREATED);


    }

    @GetMapping("userId/{UserId}/posts")
     public ResponseEntity<List<PostDto>> getPostbyUser(@PathVariable("UserId")Integer userId)
    {
        List<PostDto> alluserPost =   postService.getpostbyUser(userId);
        return new ResponseEntity<List<PostDto>>(alluserPost,HttpStatus.OK);
    }

    @GetMapping("catgoery/{catid}/posts")
    public ResponseEntity<List<PostDto>> getPostbyCatgoery(@PathVariable("catid") Integer catid)
    {
        List<PostDto> allpost =  postService.getPostByCategory(catid);
        return new ResponseEntity<List<PostDto>>(allpost,HttpStatus.OK);
    }


    @GetMapping("allpost/post")
    public ResponseEntity<PostResponse> getallpost(@RequestParam(value = "pageNumber",defaultValue = "0", required = false)Integer pageNumber, @RequestParam(value = "pageSize",defaultValue = "4",required = false)Integer pageSize,@RequestParam(value ="sortby",defaultValue = "Id",required = false)String sortby,@RequestParam(value = "sortdirection",defaultValue = "asc",required = false)String sortdirection)
    {
        PostResponse allpost =  postService.getAllPost(pageNumber, pageSize,sortby,sortdirection);

        return new ResponseEntity<PostResponse>(allpost,HttpStatus.OK);
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable("postId")Integer postId)
    {
        PostDto single = postService.getPostbyid(postId);


        return new ResponseEntity<PostDto>(single,HttpStatus.OK);
    }

    @PutMapping("post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto post, @PathVariable("postId") Integer postId)
    {
        PostDto updatepost = postService.updatePost(post,postId);

        return new ResponseEntity<PostDto>(updatepost,HttpStatus.OK);
    }

    @DeleteMapping("post/{postId}")
    public ResponseEntity<ApiResponse> deletPost(@PathVariable("postId") Integer postId)
    {
          postService.deletPost(postId);

          ApiResponse response = new ApiResponse("Post Deleted Sucessfully", true);

          return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<PostDto>> serachpost(@PathVariable("title") String keyword)
    {
        List<PostDto> allpost = postService.searchPost(keyword);
        return new ResponseEntity<List<PostDto>>(allpost,HttpStatus.OK);
    }


//    Post image upload
    @PostMapping("/post/image/upload/{postId}")
   public ResponseEntity<PostDto> uploadPostImage(@RequestParam("iamges")MultipartFile iamge, @PathVariable("postId") Integer postId) throws IOException
   {
         String fileName =  fileService.uploadImage(path,iamge);
       PostDto post = postService.getPostbyid(postId);
       post.setImageName(fileName);
      PostDto postupate = postService.updatePost(post,postId);
      return  new ResponseEntity<PostDto>(postupate,HttpStatus.OK);

   }

   @GetMapping(value = "/post/images/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
   public void downloadImages(@PathVariable("imageName")String imageName, HttpServletResponse response) throws IOException
   {
       InputStream resource = fileService.getResoucre(path,imageName);
       response.setContentType(MediaType.IMAGE_JPEG_VALUE);
       StreamUtils.copy(resource,response.getOutputStream());
   }


}
