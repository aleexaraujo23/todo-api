package io.github.alex.todo.rest;

import io.github.alex.todo.model.Todo;
import io.github.alex.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin("*")
public class TodoController {

    @Autowired
    private TodoRepository repository;

    @PostMapping
    public Todo save( @RequestBody Todo todo ){
        return repository.save(todo);
    }

    @GetMapping("{id}")
    public Todo getById( @PathVariable Long id ){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Todo> getAll(){
        return repository.findAll();
    }

    @DeleteMapping("{id}")
    public void delete( @PathVariable Long id ) {
        repository.deleteById(id);
    }

    @PatchMapping("{id}/done")
    public Todo markAsDone( @PathVariable Long id ){
        return repository.findById(id).map(todo -> {
            todo.setDone(true);
            todo.setDoneDate(LocalDateTime.now());
            repository.save(todo);
            return todo;
        }).orElse(null);
    }

}
