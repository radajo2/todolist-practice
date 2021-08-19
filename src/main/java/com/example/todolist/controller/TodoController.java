package com.example.todolist.controller;

import com.example.todolist.Entity.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos(){
        return todoService.getAllTodo();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.addTodo(todo);
    }

    @PutMapping (path ="/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todoInfo){
        return todoService.updateTodo(id, todoInfo);
    }

    @DeleteMapping(path = "/{id}")
    public Todo deleteTodo(@PathVariable Integer id){
        return todoService.removeTodo(id);
    }


}
