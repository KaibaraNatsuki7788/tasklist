package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class HomeRestController {

//    ユーザー情報
    record User(int id,String name,String mail){}
    private List<User> users = new ArrayList<>();

//    タスク情報
    record TaskItem(String id,String name,String task,String deadline, boolean done){}
    private List<TaskItem> taskItems = new ArrayList<>();

    @RequestMapping("/resthello")
    String hello(){
        return "hello";
    }

    @GetMapping("/restadd")
    String addItem(@RequestParam("name") String name,
                   @RequestParam("task") String task,
                   @RequestParam("deadline") String deadline) {
        String task_id = UUID.randomUUID().toString().substring(0,8);
        TaskItem item = new TaskItem(task_id,name,task,deadline,false);
        taskItems.add(item);
        return "タスク追加";
    }

    @GetMapping("/restlist")
    String listItems(){
        String result = taskItems.stream()
                .map(TaskItem::toString)
                .collect(Collectors.joining(","));
            return result;
    }
}
