package model.dao.impl;

import db.DB;
import model.dao.DepartmentDao;
import model.entities.Department;
import ranslm_ware.com.db.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("INSERT INTO department" +
                    "(Name) VALUES " +
                    "(?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Department obj) {

        try (PreparedStatement st = conn.prepareStatement("UPDATE department\n" +
                "SET Name = ? " + "WHERE Id = ?")) {

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("The department doesn't exist");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        try (PreparedStatement st = conn.prepareStatement("DELETE FROM department"
                + " WHERE Id = ?")) {

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("The department doesn't exist");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public Department findById(Integer id) {
        Department dep = null;

        try (PreparedStatement st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?")) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {


                if (rs.next()) {
                    dep = new Department(rs.getInt("Id"), rs.getString("Name"));
                }
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
        return dep;
    }

    @Override
    public List<Department> findAll() {
        List<Department> deps = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM department")) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Department dp = new Department(rs.getInt("Id"), rs.getString("Name"));
                    deps.add(dp);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return deps;
    }
}
