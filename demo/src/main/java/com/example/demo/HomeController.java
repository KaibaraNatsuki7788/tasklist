//12-05の状態
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class HomeController {
    @RequestMapping(value = "/hello")
            //    ユーザー情報
    record User(int id, String name, String mail) {
    }

    private List<HomeRestController.User> users = new ArrayList<>();

    //    タスク情報
    record TaskItem(String id, String name, String task, String deadline, boolean done) {
    }

    private List<TaskItem> taskItems = new ArrayList<TaskItem>();

    private final TaskListDAO dao;

    @Autowired
    HomeController(TaskListDAO dao) {
        this.dao = dao;
    }

    //    タスク閲覧用エンドポイント
    @GetMapping("/list")
    String listItems(Model model) {
        List<TaskItem> taskItems = dao.findAll();
        model.addAttribute("taskList", taskItems);
        return "home";
    }

    //    タスク追加用エンドポイント
    @GetMapping("/add")
    String addItem(@RequestParam("name") String name,
                   @RequestParam("task") String task,
                   @RequestParam("deadline") String deadline) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, name, task, deadline, false);
        dao.add(item);

        return "redirect:/list";
    }

    //    タスク削除用エンドポイント
    @GetMapping("/delete")
    String deleteItem(@RequestParam("id") String id) {
        dao.delete(id);
        return "redirect:/list";
    }

    @GetMapping("/update")
    String updareItem(@RequestParam("id") String id,
                      @RequestParam("name") String name,
                      @RequestParam("task") String task,
                      @RequestParam("deadline") String deadline,
                      @RequestParam("done") boolean done){
        TaskItem taskItem = new TaskItem(id,name,task,deadline,done);
        dao.update(taskItem);
        return "redirect:/list";
    }
}
