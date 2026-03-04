package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("--- Test 1: seller findById ---");
        Seller seller = sellerDao.findById(5);
        System.out.println(seller);

        System.out.println("--- Test 2: seller findByDepartment ---");
        Department dp = new Department(3, "");
        List<Seller> sellers = sellerDao.findByDepartment(dp);
        sellers.forEach(System.out::println);

        System.out.println("--- Test 3: seller insert ---");
        Seller seller1 = new Seller(8, "Agent Cooper", "cooper431@gmail.com", new Date(), 5800.0, dp);
        sellerDao.insert(seller1);
        System.out.println("Done");

        System.out.println("--- Test 4: seller update ---");
        seller.setName("Renan");
        sellerDao.update(seller);
        System.out.println("Done");

        System.out.println("--- Test 5: seller deleteById ---");
        seller.setId(16);
        sellerDao.deleteById(seller.getId());
        System.out.println("Done");

        System.out.println("--- Test 6: seller findAll ---");
        List<Seller> findAll = sellerDao.findAll();
        findAll.forEach(System.out::println);

    }
}
