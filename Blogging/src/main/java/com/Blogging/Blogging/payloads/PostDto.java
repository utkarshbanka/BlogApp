package com.Blogging.Blogging.payloads;

import com.Blogging.Blogging.Model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {



    private Integer postId;

    private String title;

    private String content;

    private String imageName;

    private UserDto userDto;

    private CategoryDto categoryDto;

    private Set<CommentDto> comments = new HashSet<>();



}
