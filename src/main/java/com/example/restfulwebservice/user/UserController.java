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
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if (user == null){
            throw new UserNotFoundException(String.format("id[%s] not found",id));
        }
    }

    @PutMapping("/users/{id}") //링크에 숫자를 더해줘야하는 버젼
    public void editUser(@RequestBody User user, @PathVariable int id){
        User editUser = service.editById(user,id);
    }
//    PUT - http://localhost:8088/users/2
//    {
//        "name": "New User",
//            "joinDate": "2021-01-14T10:55:11.877+00:00"
//    }
//    이렇게 해주면 2번 자료가 New User로 바뀜

    @PutMapping("/users") //링크에 숫자를 안하고 그냥 PUT으로 정보를 줄때 숫자를 더해주는 버젼
    public void editUser(@RequestBody User user){
        User editUser = service.editById(user);
    }

//    PUT - http://localhost:8088/users
//    {
//        "id": 2,
//            "name": "New User",
//            "joinDate": "2021-01-14T10:55:11.877+00:00"
//    }
//    이렇게 해주면 2번 자료가 똑같이 바뀜
}
