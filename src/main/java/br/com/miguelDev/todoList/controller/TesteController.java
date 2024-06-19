package br.com.miguelDev.todoList.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota")
public class TesteController {
    
    @GetMapping("/mensagem")
    public String getMensagem(){
       return "Olá Mundo!";  
    }

}
