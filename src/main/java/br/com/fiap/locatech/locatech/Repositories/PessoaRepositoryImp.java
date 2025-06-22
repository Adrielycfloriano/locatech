package br.com.fiap.locatech.locatech.Repositories;

import br.com.fiap.locatech.locatech.entities.Pessoa;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PessoaRepositoryImp implements PessoaRepository {

    /**
     * Cliente JDBC utilizado para executar operações no banco de dados.
     */
    private final JdbcClient jdbcClient;

    /**
     * Construtor da classe PessoaRepositoryImp.
     *
     * @param jdbcClient Cliente JDBC para acessar o banco de dados
     */
    public PessoaRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Busca uma pessoa pelo seu ID no banco de dados.
     *
     * @param id O ID do pessoa a ser buscado
     * @return Um Optional contendo a pessoa se encontrado, ou vazio se não existir
     */
    @Override
    public Optional<Pessoa> findById(Long id) {
        return this.jdbcClient.sql("SELECT * FROM pessoas WHERE id = :id")
                .param("id", id)
                .query(Pessoa.class)
                .optional();
    }

    /**
     * Busca todas as pessoas no banco de dados com paginação.
     *
     * @param size   Quantidade de registros por página
     * @param offset Deslocamento inicial da consulta
     * @return Uma lista de veículos encontrados
     */
    @Override
    public List<Pessoa> findAll(int size, int offset) {
        return this.jdbcClient
                .sql("SELECT * FROM pessoas LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(Pessoa.class)
                .list();

    }

    /**
     * Salva uma nova pessoa no banco de dados.
     *
     * @param pessoa a pessoa a ser salvo
     * @return O número de registros afetados pela operação
     */
    @Override
    public Integer save(Pessoa pessoa) {
        return this.jdbcClient
                .sql("INSERT INTO pessoas (nome, cpf, telefone, email) VALUES (:nome, :cpf, :telefone, :email)")
                .param("nome", pessoa.getNome())
                .param("cpf", pessoa.getCpf())
                .param("telefone", pessoa.getTelefone())
                .param("email", pessoa.getEmail())
                .update();
    }

    /**
     * Atualiza uma pessoa existente no banco de dados.
     *
     * @param pessoa A pessoa com os dados atualizados
     * @param id     O ID da pessoa a ser atualizado
     * @return O número de registros afetados pela operação
     */
    @Override
    public Integer update(Pessoa pessoa, Long id) {
        return this.jdbcClient
                .sql("UPDATE pessoas SET nome = :nome, cpf = :cpf, telefone = :telefone, email = :email WHERE id = :id")
                .param("id", id)
                .param("nome", pessoa.getNome())
                .param("cpf", pessoa.getCpf())
                .param("telefone", pessoa.getTelefone())
                .param("email", pessoa.getEmail())
                .update();
    }

    /**
     * Remove uma pessoa do banco de dados.
     *
     * @param id O ID da pessoa a ser removido
     * @return O número de registros afetados pela operação
     */
    @Override
    public Integer delete(Long id) {
        return this.jdbcClient
                .sql("DELETE FROM pessoas WHERE id = :id")
                .param("id", id)
                .update();
    }
}
