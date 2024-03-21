package com.waracle.cakemgr.model;

import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.lang.IllegalArgumentException;


// This is to ensure mapping between Json input and entity without requireing and ID filed
@Data
@NoArgsConstructor
// @AllArgsConstructor
public class Cake {
    private String title;
    private String desc;
    private String image ;

    public Cake(@Size(min=1,max=100) String title, String desc, String image){
        this.title = title;
        this.desc = desc;
        this.image=image;
        if ( !isValid() ) throw new IllegalArgumentException("Invalid Cake Constructor");
    }

    public String toJson(){
        var str = new String();
        str = "{" +
              "\"title\":" + "\"" + title  + "\"," +
              "\"desc\":"  + "\"" +  desc  + "\"," +
              "\"image\":" + "\"" +  image + "\""  
                                   + "}" ;
        return str ;
    }

    public boolean isValid() {
        if ( title == null ) return false;
        if (title.length() < 1 || title.length() > 100) return false;
        if ( desc == null )  return false;
        if (desc.length() < 1 || desc.length() > 100) return false;
        if (image == null )  return false;
        if (image.length() < 1 || image.length() > 300) return false;
        return true;
    }
}
