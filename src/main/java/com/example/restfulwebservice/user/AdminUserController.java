package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin") //이걸 설정해주면 모든 값 앞에 admin이 기본으로 설정됨
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    //모든 유저 보기
    @GetMapping("/users") //localhost:8088/users 하면 이게 실행됨
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    //GET /users/1 or /users/10 ->String
    @GetMapping("/users/{id}") //뒤에 숫자 입력하면 그사람 아이디 검색
    public MappingJacksonValue retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if (user ==null){ //존재하지 않는 데이터 추가하면 예외 처리
            throw new UserNotFoundException(String.format("ID[%s] not found",id ));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","password","ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);
        return mapping;
    }
}
