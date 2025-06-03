package com.example.solicitacao.micro.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade JPA que representa uma solicitação de instalação de software.
 * Mapeada para a tabela 'solicitacao' no banco de dados.
 * 
 * Esta entidade é o modelo de domínio central do microserviço,
 * representando o ciclo de vida completo de uma solicitação desde
 * a criação até a instalação do software no laboratório.
 */
@Entity
@Getter
@Setter
public class Solicitacao {

    /**
     * Chave primária da solicitação.
     * Gerada automaticamente pelo banco de dados usando estratégia IDENTITY.
     * Utilizada como identificador único em todas as operações CRUD.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do software solicitado para instalação.
     * Campo obrigatório que identifica o programa a ser instalado.
     * Exemplos: "IntelliJ IDEA", "Eclipse", "Visual Studio Code"
     */
    private String softwareName;

    /**
     * Versão específica do software solicitado.
     * Importante para garantir compatibilidade e atender requisitos pedagógicos.
     * Pode conter valores como "2023.3.2", "latest", "17.0.1"
     */
    private String softwareVersion;

    /**
     * Identificador do laboratório de destino.
     * Referencia o laboratório onde o software deve ser instalado.
     * Formato típico: "LAB-01", "LAB-PROG", "SALA-204"
     */
    private String labId;

    /**
     * Data de criação da solicitação.
     * Registra quando a solicitação foi submetida ao sistema.
     * Utilizada para ordenação, relatórios e controle de SLA.
     */
    private LocalDate requestDate;

    /**
     * Status atual da solicitação no workflow de aprovação.
     * Controla o estado da solicitação no processo de aprovação e instalação.
     * Valores típicos: "PENDENTE", "APROVADA", "REJEITADA", "INSTALADA", "CANCELADA"
     */
    private String status;

    /**
     * Email do professor solicitante.
     * Identifica o usuário responsável pela solicitação.
     * Utilizado para controle de acesso, notificações e auditoria.
     * Obtido do contexto de autenticação JWT no momento da criação.
     */
    private String professorEmail;
}