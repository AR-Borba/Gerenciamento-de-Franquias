package com.franquias.Persistence;


import java.io.File;
import java.util.List;

public interface Persistence<T> {

    String DIRECTORY = "Gerenciamento-de-Franquias"  + File.separator + "data";
    void save(List<T> itens);
    List<T> findAll();
}
