package com.erikat.examenprimeraevaad_erikat.DAO;

import java.util.List;

public interface DAO<T, V> {
    public boolean insertar(T obj);
    public boolean editar(T obj);
    public boolean eliminar(T obj);
    public T buscar(V valor);
    public List<T> listar();
    /*
    INTERFAZ DAO:
    Contiene todos los métodos CRUD y sirve para cualquier clase DAO que se cree
    T hace referencia al tipo de objeto que utiliza
    V hace referencia al tipo de valor que tiene el identificador de la tabla
     */
}
