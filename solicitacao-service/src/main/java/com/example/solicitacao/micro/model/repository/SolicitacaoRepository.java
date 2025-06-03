package com.example.solicitacao.micro.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.solicitacao.micro.model.entity.Solicitacao;

/**
 * Repositório JPA para operações de persistência relacionadas à entidade Solicitacao.
 * 
 * Estende JpaRepository que fornece operações CRUD básicas e métodos de paginação/sorting.
 * 
 * Estratégias importantes:
 * 1. Nomeclatura de métodos segue convenção do Spring Data JPA para geração automática de queries
 * 2. Interface leve focada em declarações de métodos sem implementação
 * 3. Integração perfeita com o Spring Data JPA para minimizar código boilerplate
 */
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    
    /**
     * Busca solicitações associadas a um professor pelo seu email.
     * 
     * Método gerado automaticamente pelo Spring Data JPA seguindo a convenção:
     * findBy[Atributo][Operador] onde Professor é um relacionamento e Email é atributo
     * 
     * @param professorEmail Email do professor para filtrar as solicitações
     * @return Lista de solicitações associadas ao professor, ordenação depende da implementação padrão
     * 
     * Observação estratégica: 
     * - Pode ser combinado com Pageable para paginação se o volume de dados for grande
     * - Considerar adicionar índices no banco para o campo professor.email se esta consulta for frequente
     */
    List<Solicitacao> findByProfessorEmail(String professorEmail);
}