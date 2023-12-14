package com.Blogging.Blogging.Exception;

public class ResourceNotFoundException  extends  RuntimeException{


    String resoucname;
    String fieldName;

    Long fieldValue;

    public ResourceNotFoundException(String resoucname, String fieldName, Long fieldValue)
    {
//
        super(resoucname+" "+ fieldName+"Not found With this Id: "+fieldName);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.resoucname = resoucname;
    }

}
