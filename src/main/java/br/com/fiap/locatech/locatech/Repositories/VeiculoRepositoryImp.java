package br.com.fiap.locatech.locatech.Repositories;

import br.com.fiap.locatech.locatech.entities.Veiculo;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VeiculoRepositoryImp implements VeiculoRepository {

    /**
     * Cliente JDBC utilizado para executar operações no banco de dados.
     */
    private final JdbcClient jdbcClient;

    /**
     * Construtor da classe VeiculoRepositoryImp.
     *
     * @param jdbcClient Cliente JDBC para acessar o banco de dados
     */
    public VeiculoRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Busca um veículo pelo seu ID no banco de dados.
     *
     * @param id O ID do veículo a ser buscado
     * @return Um Optional contendo o veículo se encontrado, ou vazio se não existir
     */
    @Override
    public Optional<Veiculo> findById(Long id) {
        return this.jdbcClient.sql("SELECT * FROM veiculos WHERE id = :id")
                .param("id", id)
                .query(Veiculo.class)
                .optional();
    }

    /**
     * Busca todos os veículos no banco de dados com paginação.
     *
     * @param size   Quantidade de registros por página
     * @param offset Deslocamento inicial da consulta
     * @return Uma lista de veículos encontrados
     */
    @Override
    public List<Veiculo> findAll(int size, int offset) {
        return this.jdbcClient
                .sql("SELECT * FROM veiculos LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(Veiculo.class)
                .list();

    }

    /**
     * Salva um novo veículo no banco de dados.
     *
     * @param veiculo O veículo a ser salvo
     * @return O número de registros afetados pela operação
     */
    @Override
    public Integer save(Veiculo veiculo) {
        return this.jdbcClient
                .sql("INSERT INTO veiculos (marca, modelo, placa, ano, cor, valor_diaria) VALUES (:marca, :modelo, :placa, :ano, :cor, :valor_diaria)")
                .param("marca", veiculo.getMarca())
                .param("modelo", veiculo.getModelo())
                .param("placa", veiculo.getPlaca())
                .param("ano", veiculo.getAno())
                .param("cor", veiculo.getCor())
                .param("valor_diaria", veiculo.getValorDiaria())
                .update();
    }

    /**
     * Atualiza um veículo existente no banco de dados.
     *
     * @param veiculo O veículo com os dados atualizados
     * @param id      O ID do veículo a ser atualizado
     * @return O número de registros afetados pela operação
     */
    @Override
    public Integer update(Veiculo veiculo, Long id) {
        return this.jdbcClient
                .sql("UPDATE veiculos SET marca = :marca, modelo = :modelo, placa = :placa, ano = :ano, cor = :cor, valor_diaria = :valor_diaria " +
                        "WHERE id = :id")
                .param("id", id)
                .param("marca", veiculo.getMarca())
                .param("modelo", veiculo.getModelo())
                .param("placa", veiculo.getPlaca())
                .param("ano", veiculo.getAno())
                .param("cor", veiculo.getCor())
                .param("valor_diaria", veiculo.getValorDiaria())
                .update();
    }

    /**
     * Remove um veículo do banco de dados.
     *
     * @param id O ID do veículo a ser removido
     * @return O número de registros afetados pela operação
     */
    @Override
    public Integer delete(Long id) {
        return this.jdbcClient
                .sql("DELETE FROM veiculos WHERE id = :id")
                .param("id", id)
                .update();
    }
}
