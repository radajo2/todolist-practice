package com.example.todolist.integration;

import com.example.todolist.Entity.Todo;
import com.example.todolist.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;


    @Test
    void should_return_all_todo_when_getAllTodos() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("first to do item"))
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }

    @Test
    void should_create_new_todo_when_addTodo_given_todo_information() throws Exception {
        String todo = "{\n" +
                "    \"text\": \"integration test 1\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("integration test 1"));
    }

    @Test
    void should_update_todo_when_updateTodo_given_todo_information() throws Exception {
        final Todo todo = new Todo(100, "integration test 2", true);
        final Todo savedTodo = todoRepository.save(todo);

        String updateTodo = "{\n" +
                "    \"text\": \"integration test 2\",\n" +
                "    \"done\": false\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/todos/{id}", savedTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateTodo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("integration test 2"))
                .andExpect(jsonPath("$.done").value(false));
    }

}
