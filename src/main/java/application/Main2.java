package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {

        System.out.println("--- Test 1: department findById ---");
        DepartmentDao dep = DaoFactory.createDepartment();
        Department dp = dep.findById(9);
        System.out.println(dp);

        System.out.println("--- Test 2: department insert ---");
        Department depart = new Department(null, "Design");
        dep.insert(depart);
        System.out.println("Done");

        System.out.println("--- Test 3: department update ---");
        dp.setName("HR");
        dep.update(dp);
        System.out.println("Done");

        System.out.println("--- Test 4: department deleteById ---");
        dp.setId(9);
        dep.deleteById(dp.getId());
        System.out.println("Done");

        System.out.println("--- Test 5: department findAll ---");
        List<Department> listD = dep.findAll();
        listD.forEach(System.out::println);


    }
}
