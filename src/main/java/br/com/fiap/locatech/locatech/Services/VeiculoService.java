package br.com.fiap.locatech.locatech.Services;

import br.com.fiap.locatech.locatech.Repositories.VeiculoRepository;
import br.com.fiap.locatech.locatech.entities.Veiculo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public List<Veiculo> findAllVeiculos(int page, int size) {
        int offSet = (page - 1) * size;
        return this.veiculoRepository.findAll(size, offSet);
    }

    public Optional<Veiculo> findVeiculoById(Long id) {
        return this.veiculoRepository.findById(id);
    }

    public void saveVeiculo(Veiculo veiculo) {
        var save = this.veiculoRepository.save(veiculo);
        Assert.state(save == 1, "Erro ao salvar veiculo" + veiculo.getModelo());
    }

    public void updateVeiculo(Veiculo veiculo, Long id) {
        var update = this.veiculoRepository.update(veiculo, id);
        if (update == 0) {
            throw new RuntimeException("Veículo não encontrado");
        }
    }

    public void delete(Long id) {
        var delete = this.veiculoRepository.delete(id);
        if (delete == 0) {
            throw new RuntimeException("Veículo não encontrado");
        }
    }
}
