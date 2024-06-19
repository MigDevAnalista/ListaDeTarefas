package br.com.miguelDev.todoList.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.miguelDev.todoList.model.Tarefa;
import br.com.miguelDev.todoList.repository.ITarefaRepository;

@RestController
@RequestMapping("/tarefa")
public class TarefaController { 
    
    //outra forma de usar a injeção do repository sem usar o Autowired
    
    private final ITarefaRepository iTarefaRepository;

    public TarefaController(ITarefaRepository iTarefaRepository){
        this.iTarefaRepository = iTarefaRepository;
    }

    @PostMapping("/")
    public ResponseEntity cadastrarTarefa(@RequestBody Tarefa tarefa){
        var retorno =  iTarefaRepository.save(tarefa);
        return ResponseEntity.status(HttpStatus.OK).body(retorno);
    }
    
}
