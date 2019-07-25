package org.katale.repository.generic;

import java.util.List;

public interface GenericRepository<T> {

    List<T> getAll();
    T getByID(long id);
    T getByName(String name);
    T save(T model);
    T update(T Model);
    void delete(long id);
    void delete(String param);
    void delete(T t);
    Boolean exists(T model);

}
