package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskListDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    TaskListDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//        タスク追加
        public void add (HomeController.TaskItem taskItem){
            SqlParameterSource param = new BeanPropertySqlParameterSource(taskItem);
            SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("tasklist");
            insert.execute(param);
        }

//        テーブルのタスクすべて取得
        public List<HomeController.TaskItem> findAll () {
            String query = "SELECT * FROM tasklist";

            List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
            List<HomeController.TaskItem> taskItems = result.stream()
                    .map((Map<String, Object> row) -> new HomeController.TaskItem(
                            row.get("id").toString(),
                            row.get("name").toString(),
                            row.get("task").toString(),
                            row.get("deadline").toString(),
                            (Boolean) row.get("done")))
                    .toList();
            return taskItems;
        }

        public int delete(String id){
            int number = jdbcTemplate.update("DELETE FROM tasklist WHERE id = ?", id);
            return number;
        }

        public int update(HomeController.TaskItem taskItem){
            int number = jdbcTemplate.update(
                    "UPDATE tasklist SET name = ?, task = ?, deadline = ?, done = ? WHERE id = ?",
                    taskItem.name(),
                    taskItem.task(),
                    taskItem.deadline(),
                    taskItem.done(),
                    taskItem.id());
            return  number;
        }
}

