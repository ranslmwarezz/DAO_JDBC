package model.dao.impl;

import db.DB;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import ranslm_ware.com.db.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC (Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement pt = null;
        ResultSet rs = null;

        try {
            pt = conn.prepareCall("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE seller.Id = ?");
            pt.setInt(1, id);
            rs = pt.executeQuery();
            if(rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, dep);
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(pt);
            DB.closeResultSet(rs);

        }

    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public List<Seller> findByDepartment(Department dep) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Seller> sellers = new ArrayList<>();

        try{
            ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE DepartmentId = ?\n" +
                    "ORDER BY Name");

            ps.setInt(1, dep.getId());
            rs = ps.executeQuery();
            Map<Integer, Department> map = new HashMap<>();

             while(rs.next()) {
               Department department = map.get(rs.getInt("DepartmentId"));
               if(department == null){
               department = instantiateDepartment(rs);
               map.put(rs.getInt("DepartmentId"), department);
               }
               Seller sl = instantiateSeller(rs, department);
               sellers.add(sl);
             }
                 return sellers;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
            }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

    }
}
