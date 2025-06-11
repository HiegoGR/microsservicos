package com.estudos.proposta_app.controller;

import com.estudos.proposta_app.dto.PropostaDTO;
import com.estudos.proposta_app.service.PropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaDTO> salvar(@RequestBody PropostaDTO propostaDTO){
        PropostaDTO response = propostaService.salvar(propostaDTO);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/id")
                        .buildAndExpand(response.getId())
                        .toUri())
                        .body(response);
    }


    @GetMapping
    public ResponseEntity<List<PropostaDTO>> listar() {
        return ResponseEntity.ok(propostaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(propostaService.buscarPorId(id));
    }
}
