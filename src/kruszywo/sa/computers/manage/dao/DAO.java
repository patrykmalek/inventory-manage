package kruszywo.sa.computers.manage.dao;

import java.util.List;

public interface DAO<T> {
    
    T get(int id);
    
    T get(String name);
    
    List<T> getAll();
    
    void insert(T t);
    
    void update(T t);
    
    void delete(T t);
}