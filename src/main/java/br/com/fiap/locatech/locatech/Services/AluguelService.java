package br.com.fiap.locatech.locatech.Services;

import br.com.fiap.locatech.locatech.Repositories.AluguelRepository;
import br.com.fiap.locatech.locatech.Repositories.PessoaRepository;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.entities.Pessoa;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;

    public AluguelService(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    public List<Aluguel> findAllAlugueis(int page, int size) {
        int offSet = (page - 1) * size;
        return this.aluguelRepository.findAll(size, offSet);
    }

    public Optional<Aluguel> findAluguelById(Long id) {
        return this.aluguelRepository.findById(id);
    }

    public void saveAluguel(Aluguel aluguel) {
        var save = this.aluguelRepository.save(aluguel);
        Assert.state(save == 1, "Erro ao salvar aluguel" + aluguel.getPessoaId());
    }

    public void updateAluguel(Aluguel aluguel, Long id) {
        var update = this.aluguelRepository.update(aluguel, id);
        if (update == 0) {
            throw new RuntimeException("Veículo não encontrado");
        }
    }

    public void delete(Long id) {
        var delete = this.aluguelRepository.delete(id);
        if (delete == 0) {
            throw new RuntimeException("Veículo não encontrado");
        }
    }
}
