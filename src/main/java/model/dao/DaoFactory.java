package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static Department createDepartment(){
        return new Department();
    }

}
