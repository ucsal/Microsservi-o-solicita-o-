package com.example.solicitacao.micro.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.solicitacao.micro.dto.SolicitacaoDTO;
import com.example.solicitacao.micro.model.entity.Solicitacao;
import com.example.solicitacao.micro.model.repository.SolicitacaoRepository;

import lombok.RequiredArgsConstructor;

/**
 * Serviço responsável pela lógica de negócio das solicitações de software.
 * Gerencia operações CRUD e regras específicas para professores e administradores.
 */
@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;

    /**
     * Busca todas as solicitações do sistema.
     * Método utilizado principalmente por administradores para visualização geral.
     * 
     * @return Lista de todas as solicitações convertidas para DTO
     */
    public List<SolicitacaoDTO> getAll() {
        return solicitacaoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca apenas as solicitações do professor autenticado.
     * Utiliza o email do usuário logado para filtrar as solicitações.
     * 
     * @param authentication Contexto de autenticação do usuário logado
     * @return Lista das solicitações do professor atual
     */
    public List<SolicitacaoDTO> getMinhasSolicitacoes(Authentication authentication) {
        // Extrai o email do usuário autenticado
        String email = authentication.getName();
        return solicitacaoRepository.findByProfessorEmail(email).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova solicitação de software.
     * Define automaticamente o status como PENDENTE e associa ao professor logado.
     * 
     * @param solicitacaoDTO Dados da solicitação a ser criada
     * @param authentication Contexto de autenticação para identificar o solicitante
     * @return Solicitação criada convertida para DTO
     */
    public SolicitacaoDTO create(SolicitacaoDTO solicitacaoDTO, Authentication authentication) {
        Solicitacao solicitacao = new Solicitacao();
        
        // Mapeia os dados básicos da solicitação
        solicitacao.setSoftwareName(solicitacaoDTO.getSoftwareName());
        solicitacao.setSoftwareVersion(solicitacaoDTO.getSoftwareVersion());
        solicitacao.setLabId(solicitacaoDTO.getLabId());
        solicitacao.setRequestDate(solicitacaoDTO.getRequestDate());
        
        // Define o status inicial como PENDENTE (regra de negócio)
        solicitacao.setStatus("PENDENTE");
        
        // Associa a solicitação ao professor autenticado
        solicitacao.setProfessorEmail(authentication.getName());

        return convertToDTO(solicitacaoRepository.save(solicitacao));
    }

    /**
     * Atualiza o status de uma solicitação existente.
     * Método utilizado principalmente por administradores para aprovar/rejeitar solicitações.
     * 
     * @param id ID da solicitação a ser atualizada
     * @param solicitacaoDTO Dados com o novo status
     * @return Solicitação atualizada convertida para DTO
     * @throws RuntimeException se a solicitação não for encontrada
     */
    public SolicitacaoDTO update(Long id, SolicitacaoDTO solicitacaoDTO) {
        // Busca a solicitação existente ou lança exceção se não encontrada
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        // Atualiza apenas o status (por questões de segurança, outros campos não são alteráveis)
        solicitacao.setStatus(solicitacaoDTO.getStatus());
        
        return convertToDTO(solicitacaoRepository.save(solicitacao));
    }

    /**
     * Remove uma solicitação do sistema.
     * Operação irreversível - considerar implementar soft delete em versões futuras.
     * 
     * @param id ID da solicitação a ser removida
     */
    public void delete(Long id) {
        // TODO: Considerar implementar verificação de permissões antes da exclusão
        solicitacaoRepository.deleteById(id);
    }

    /**
     * Converte uma entidade Solicitacao para DTO.
     * Método privado para manter a separação entre camadas de dados e apresentação.
     * 
     * @param solicitacao Entidade a ser convertida
     * @return DTO correspondente à entidade
     */
    private SolicitacaoDTO convertToDTO(Solicitacao solicitacao) {
        return new SolicitacaoDTO(
                solicitacao.getId(),
                solicitacao.getSoftwareName(),
                solicitacao.getSoftwareVersion(),
                solicitacao.getLabId(),
                solicitacao.getRequestDate(),
                solicitacao.getStatus()
        );
    }
}