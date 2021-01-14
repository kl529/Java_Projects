package com.example.restfulwebservice.helloworld;
//lombok 사용

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HelloWorldBean {
    private String message;

}
