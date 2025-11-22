package com.lanchonete.repository;

import java.util.List;

import com.lanchonete.model.Vendedor;

public interface IVendedorRepository {
    void salvar(Vendedor vendedor);
    void remover(Vendedor vendedor);
    Vendedor buscarPorCodigo(double codigo);
    List<Vendedor> listarTodos();
}
