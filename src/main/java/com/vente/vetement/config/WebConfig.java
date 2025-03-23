// package com.vente.vetement.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import java.nio.file.Paths;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {
//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         String uploadDir = Paths.get("uploads").toAbsolutePath().toString();
//         registry.addResourceHandler("/uploads/**")
//                 .addResourceLocations("file:" + uploadDir + "/");
//     }
// }