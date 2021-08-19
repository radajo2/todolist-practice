package com.example.todolist.service;

import com.example.todolist.Entity.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodo(){
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Integer id, Todo todoInfo) {
        Todo updateTodo = todoRepository.findById(id)
                .map(todo -> updateTodoInfo(todo, todoInfo))
                .get();
        return todoRepository.save(updateTodo);
    }

    private Todo updateTodoInfo(Todo todo, Todo todoInfo) {
        todo.setDone(todoInfo.getDone());
        return todo;
    }

    public Todo removeTodo(Integer id) {
        Optional<Todo> removeTodo = todoRepository.findById(id);
        todoRepository.deleteById(id);
        return removeTodo.orElse(null);
    }
}
