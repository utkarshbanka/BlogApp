package com.Blogging.Blogging.payloads;

import com.Blogging.Blogging.Model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

    private List<PostDto> content;
    private Integer pageNumber;

    private Integer pageSize;

    private Long totalElement;

    private Integer  totalPages;

    private Boolean lastpage;
}
