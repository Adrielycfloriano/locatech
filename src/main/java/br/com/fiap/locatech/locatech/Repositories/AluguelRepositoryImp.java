package br.com.fiap.locatech.locatech.Repositories;

import br.com.fiap.locatech.locatech.entities.Aluguel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AluguelRepositoryImp implements AluguelRepository {

    /**
     * Cliente JDBC utilizado para executar operações no banco de dados.
     */
    private final JdbcClient jdbcClient;

    /**
     * Construtor da classe AluguelRepositoryImp.
     *
     * @param jdbcClient Cliente JDBC para acessar o banco de dados
     */
    public AluguelRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Busca um aluguel pelo seu identificador único.
     *
     * @param id Identificador único do aluguel
     * @return Optional contendo o aluguel se encontrado, ou vazio caso contrário
     */
    @Override
    public Optional<Aluguel> findById(Long id) {
        return this.jdbcClient
                .sql("SELECT a.id, a.pessoa_id, a.veiculo_id, a.data_inicio, a.data_fim, a.valor_total, " +
                        "p.nome AS pessoa_nome, p.cpf AS pessoa_cpf, " +
                        "v.modelo AS veiculo_modelo, v.placa AS veiculo_placa " +
                        "FROM alugueis a " +
                        "INNER JOIN pessoas p ON a.pessoa_id = p.id " +
                        "INNER JOIN veiculos v ON a.veiculo_id = v.id " +
                        "WHERE a.id = :id")
                .param("id", id)
                .query(Aluguel.class)
                .optional();
    }

    /**
     * Retorna uma lista paginada de aluguéis.
     *
     * @param size   Quantidade de registros por página
     * @param offset Posição inicial da consulta
     * @return Lista de aluguéis encontrados
     */
    @Override
    public List<Aluguel> findAll(int size, int offset) {
        return this.jdbcClient
                .sql("SELECT a.id, a.pessoa_id, a.veiculo_id, a.data_inicio, a.data_fim, a.valor_total, " +
                        "p.nome AS pessoa_nome, p.cpf AS pessoa_cpf, " +
                        "v.modelo AS veiculo_modelo, v.placa AS veiculo_placa " +
                        "FROM alugueis a " +
                        "INNER JOIN pessoas p ON a.pessoa_id = p.id " +
                        "INNER JOIN veiculos v ON a.veiculo_id = v.id " +
                        "LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(Aluguel.class)
                .list();
    }

    /**
     * Salva um novo aluguel no banco de dados.
     *
     * @param aluguel Objeto aluguel a ser salvo
     * @return Número de registros afetados pela operação
     */
    @Override
    public Integer save(Aluguel aluguel) {
        return this.jdbcClient
                .sql("INSERT INTO alugueis (pessoa_id, veiculo_id, data_inicio, data_fim, valor_total) " +
                        "VALUES (:pessoa_id, :veiculo_id, :data_inicio, :data_fim, :valor_total)")
                .param("pessoa_id", aluguel.getPessoaId())
                .param("veiculo_id", aluguel.getVeiculoId())
                .param("data_inicio", aluguel.getDataInicio())
                .param("data_fim", aluguel.getDataFim())
                .param("valor_total", aluguel.getValorTotal())
                .update();
    }

    /**
     * Atualiza um aluguel existente no banco de dados.
     *
     * @param aluguel Objeto aluguel com os dados atualizados
     * @param id      Identificador único do aluguel a ser atualizado
     * @return Número de registros afetados pela operação
     */
    @Override
    public Integer update(Aluguel aluguel, Long id) {
        return this.jdbcClient
                .sql("UPDATE alugueis " +
                        "SET pessoa_id = :pessoa_id, veiculo_id = :veiculo_id, data_inicio = :data_inicio, data_fim = :data_fim, valor_total = :valor_total  " +
                        "WHERE id = :id")
                .param("id", id)
                .param("pessoa_id", aluguel.getPessoaId())
                .param("veiculo_id", aluguel.getVeiculoId())
                .param("data_inicio", aluguel.getDataInicio())
                .param("data_fim", aluguel.getDataFim())
                .param("valor_total", aluguel.getValorTotal())
                .update();
    }

    /**
     * Remove um aluguel do banco de dados.
     *
     * @param id Identificador único do aluguel a ser removido
     * @return Número de registros afetados pela operação
     */
    @Override
    public Integer delete(Long id) {
        return this.jdbcClient
                .sql("DELETE FROM alugueis WHERE id = :id")
                .param("id", id)
                .update();
    }
}
