package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value={"password","ssn"}) //(방법2)
public class User {
    private Integer id;

    @Size(min=2, message="Name은 2글자 이상 입력해 주세요")
    private String name;
    @Past
    private Date joinDate;

//    @JsonIgnore //json에 출력될때 무시 됨 (방법1)
    private String password;

//    @JsonIgnore //json에 출력될때 무시 됨
    private String ssn;
}
