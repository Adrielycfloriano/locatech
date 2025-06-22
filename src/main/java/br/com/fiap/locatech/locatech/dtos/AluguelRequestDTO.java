package br.com.fiap.locatech.locatech.dtos;

import java.time.LocalDate;

public record AluguelRequestDTO(
        long pessoaId,
        long veiculoId,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
