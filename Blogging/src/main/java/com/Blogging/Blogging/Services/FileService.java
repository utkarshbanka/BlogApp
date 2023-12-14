package com.Blogging.Blogging.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService  {

    String uploadImage(String path, MultipartFile file) throws IOException;

    InputStream getResoucre(String path,String filname) throws FileNotFoundException;
}
