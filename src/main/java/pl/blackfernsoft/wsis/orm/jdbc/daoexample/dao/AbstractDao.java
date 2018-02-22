package pl.blackfernsoft.wsis.orm.jdbc.daoexample.dao;

import pl.blackfernsoft.wsis.orm.jdbc.daoexample.entity.Entity;

import java.sql.SQLException;
import java.util.List;


public abstract class AbstractDao<E extends Entity> {

    // Tworzy lub aktualizuje obiekt w bazie danych.
    public abstract E save(E entity) throws SQLException;

    // Zwraca listę wszystkich obiektów danego typu w bazie.
    public abstract List<E> findAll() throws SQLException;

    // Zwraca obiekt o zadanym id.
    public abstract E findById(Long id) throws SQLException;

    // Usuwa obiekt z bazy danych.
    public abstract void delete(E entity) throws SQLException;

}
