package com.lanchonete.repository;

import java.util.List;

import com.lanchonete.model.Vendedor;

public interface IVendedorRepository {

    void salvar(Vendedor vendedor);

    Vendedor buscarPorCodigo(int codigo);

    List<Vendedor> listarTodos();
}
