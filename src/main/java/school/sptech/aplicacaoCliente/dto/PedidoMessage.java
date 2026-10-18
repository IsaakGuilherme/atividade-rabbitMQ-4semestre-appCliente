package school.sptech.aplicacaoCliente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public record PedidoMessage(
        @NotNull Long id,
        @NotBlank String cliente,
        @NotBlank String produto,
        @NotNull @Positive Integer quantidade
) implements Serializable { }
