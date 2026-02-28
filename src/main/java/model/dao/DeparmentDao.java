package model.dao;

import model.entities.Department;

import javax.swing.*;
import java.util.List;

public interface DeparmentDao {

    void insert(Department obj);
    void update (Department obj);
    void deleteById (Integer id);
    Department findById (Integer id);
    List<Department> findAll();

}
