package com.example.solicitacao.micro.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) para transferência de dados de solicitações entre camadas.
 * Representa uma solicitação de instalação de software em laboratório.
 * 
 * Utiliza Lombok para geração automática de getters, setters, equals, hashCode e toString.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoDTO {
    
    /**
     * Identificador único da solicitação.
     * Null para novas solicitações, preenchido automaticamente pelo sistema.
     */
    private Long id;
    
    /**
     * Nome do software solicitado para instalação.
     * Exemplo: "Eclipse IDE", "Visual Studio Code", "IntelliJ IDEA"
     */
    private String softwareName;
    
    /**
     * Versão específica do software solicitado.
     * Importante para controle de compatibilidade e licenciamento.
     * Exemplo: "2023.3", "1.85.2", "latest"
     */
    private String softwareVersion;
    
    /**
     * Identificador do laboratório onde o software deve ser instalado.
     * Referência ao laboratório de informática da instituição.
     * Exemplo: "LAB-01", "LAB-PROG", "LAB-REDES"
     */
    private String labId;
    
    /**
     * Data em que a solicitação foi criada.
     * Utilizada para controle temporal e priorização de solicitações.
     * Formato: yyyy-MM-dd
     */
    private LocalDate requestDate;
    
    /**
     * Status atual da solicitação no fluxo de aprovação.
     * Valores possíveis: "PENDENTE", "APROVADA", "REJEITADA", "INSTALADA"
     * Controlado pelo sistema de workflow de aprovação.
     */
    private String status;
}