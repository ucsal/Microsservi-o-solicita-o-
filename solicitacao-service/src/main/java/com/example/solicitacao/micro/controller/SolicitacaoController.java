package com.example.solicitacao.micro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.solicitacao.micro.dto.SolicitacaoDTO;
import com.example.solicitacao.micro.model.service.SolicitacaoService;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST responsável pelo gerenciamento de solicitações.
 * Implementa endpoints para operações CRUD com controle de acesso baseado em roles.
 */
@RestController
@RequestMapping("/api/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    /**
     * Busca todas as solicitações no sistema.
     * Endpoint restrito apenas para administradores.
     * 
     * @return Lista completa de solicitações
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SolicitacaoDTO>> getAll() {
        return ResponseEntity.ok(solicitacaoService.getAll());
    }

    /**
     * Busca apenas as solicitações do professor autenticado.
     * Utiliza o contexto de autenticação para filtrar os dados.
     * 
     * @param authentication Contexto de autenticação do usuário logado
     * @return Lista de solicitações do professor
     */
    @GetMapping("/minhas")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<SolicitacaoDTO>> getMinhasSolicitacoes(Authentication authentication) {
        return ResponseEntity.ok(solicitacaoService.getMinhasSolicitacoes(authentication));
    }

    /**
     * Cria uma nova solicitação no sistema.
     * Associa automaticamente a solicitação ao professor autenticado.
     * 
     * @param solicitacaoDTO Dados da solicitação a ser criada
     * @param authentication Contexto de autenticação para associar ao professor
     * @return Solicitação criada com status HTTP 201
     */
    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<SolicitacaoDTO> create(@RequestBody SolicitacaoDTO solicitacaoDTO, Authentication authentication) {
        return ResponseEntity.status(201).body(solicitacaoService.create(solicitacaoDTO, authentication));
    }

    /**
     * Atualiza uma solicitação existente.
     * Operação restrita apenas para administradores.
     * 
     * @param id Identificador da solicitação
     * @param solicitacaoDTO Novos dados da solicitação
     * @return Solicitação atualizada
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SolicitacaoDTO> update(@PathVariable Long id, @RequestBody SolicitacaoDTO solicitacaoDTO) {
        return ResponseEntity.ok(solicitacaoService.update(id, solicitacaoDTO));
    }

    /**
     * Remove uma solicitação do sistema.
     * Permite que administradores excluam qualquer solicitação
     * e professores excluam suas próprias solicitações.
     * 
     * @param id Identificador da solicitação a ser excluída
     * @return Resposta vazia com status HTTP 204
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        solicitacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}