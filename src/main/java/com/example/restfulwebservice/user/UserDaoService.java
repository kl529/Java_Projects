package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    //유저 수
    private static int usersCount = 3;

    //유저 정보
    static{
        users.add(new User(1,"Kenn", new Date()));
        users.add(new User(2,"Alice", new Date()));
        users.add(new User(3,"Jiwon", new Date()));
    }

    //유저 정보들 한눈에 보기
    public List<User> findAll(){
        return users;
    }

    //유저 저장하기 - 없으면 추가
    public User save(User user){
        if (user.getId() == null){
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    // 한명만 찾기
    public User findOne(int id){
        for (User user : users) {
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }
}
