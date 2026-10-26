package com.UBSDB.UnerBasicSchoolFinancialSystem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 *
 * @author enock
 */
@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {
  

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    /*    
    Path imageUploadDir=Paths.get("./pro-pics");
    String imageUploadPath=imageUploadDir.toFile().getAbsolutePath();
    registry.addResourceHandler("/pro-pics/**").addResourceLocations("file:/"+imageUploadPath+"/");
    */
    Path imageUploadDir=Paths.get("./pro-pics");
     Path StudUploadDir=Paths.get("./stud-pics");
    String imageUploadPath=imageUploadDir.toFile().getAbsolutePath();
    String StudimageUploadPath=StudUploadDir.toFile().getAbsolutePath();
    
    
    //System.out.println(imageUploadPath);
    registry.addResourceHandler("/css/pro-pics/**").addResourceLocations("file:/"+imageUploadPath+"/");
    registry.addResourceHandler("/script/**").addResourceLocations("classpath:/static/script/");
   registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/css/pro-pics/**").addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry
                .addResourceHandler("/css/page/stud-pics/**")
                .addResourceLocations("file:/var/data/stud-pics/");
    }
    
}
