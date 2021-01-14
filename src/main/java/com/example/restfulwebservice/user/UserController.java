package com.example.restfulwebservice.user;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service){
        this.service = service;
    }

    //모든 유저 보기
    @GetMapping("/users") //localhost:8088/users 하면 이게 실행됨
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    //GET /users/1 or /users/10 ->String
    @GetMapping("/users/{id}") //뒤에 숫자 입력하면 그사람 아이디 검색
    public User retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if (user ==null){ //존재하지 않는 데이터 추가하면 예외 처리
            throw new UserNotFoundException(String.format("ID[%s] not found",id ));
        }

        return user;
    }

    @PostMapping("/users") // 유저 추가해주는 것
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
