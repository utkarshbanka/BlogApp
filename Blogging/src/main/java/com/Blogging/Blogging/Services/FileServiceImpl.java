package com.Blogging.Blogging.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl  implements  FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
        String filepath = path+ File.separator+fileName;

        File f = new File(path);
        if(!f.exists())
        {
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filepath));

        return fileName;
    }

    @Override
    public InputStream getResoucre(String path, String filname) throws FileNotFoundException {

        String fullpath = path+File.separator+filname;
        InputStream is = new FileInputStream(fullpath);
        return is;
    }


}
