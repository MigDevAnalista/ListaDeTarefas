package br.com.miguelDev.todoList.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.miguelDev.todoList.model.Tarefa;

public interface ITarefaRepository extends JpaRepository<Tarefa, UUID>{
    
}
