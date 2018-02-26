package com.gc.controller;

// Step # 1
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 * author: Antonella Solomon
 *
 */

@Controller
public class HomeController {

	@RequestMapping("/welcome")
	// this method is going to add data to the DB
	public ModelAndView helloWorld() throws ClassNotFoundException, SQLException {

		Connection con = getDBConnection();

		// Step 4: Create Statement
		String preparedSql = "insert into customers(CustomerID, CompanyName, ContactName)" + "values(?,?,?)";
		PreparedStatement ps = con.prepareStatement(preparedSql);
		// I am using the set string methods to add values for the ??? in the
		// PreparedStatement
		ps.setString(1, "HHHV"); // dummy data with hardcoded values (will throw an error if you run again because it's a primary key)
		ps.setString(2, "Grand Circus");
		ps.setString(3, "Antonella");
		// Step 5: execute Statement
		ps.execute(); // this is pushing the data to the DB

		// Optional step, but you should always do this
		con.close();

		return new ModelAndView("welcome", "message", "");
	}

	private Connection getDBConnection() throws ClassNotFoundException, SQLException {
		// prep for step # 3
		String url = "jdbc:mysql://localhost:3306/northwind";
		String userName = "javajan18";
		String password = "admin";

		// Step #2: Load and Register Driver
		Class.forName("com.mysql.jdbc.Driver");

		// Step #3: Create Connection
		Connection con = DriverManager.getConnection(url, userName, password);
		return con;
	}

	@RequestMapping("/welcome2")
	public ModelAndView listAllCustomers() throws ClassNotFoundException, SQLException {
		Connection con = getDBConnection();
		String query = "select * from customers";
		// Step 4: create statement
		PreparedStatement st = con.prepareStatement(query);

		// Step 5: retrieve results (optional)
		ResultSet rs = st.executeQuery();

		ArrayList<String> list = new ArrayList<>();

		// Step 6: process results (optional)
		while (rs.next()) {
			String custID = rs.getString(1);
			String compName = rs.getString(2);
			String contactName = rs.getString(3);
			list.add(custID + " " + compName + " " + contactName);

		}
		return new ModelAndView("welcome", "message", list);
	}

	@RequestMapping("update")
	public ModelAndView updateCustomer(@RequestParam("custID") String custID, @RequestParam("compName") String compName,
			@RequestParam("contName") String contName) throws ClassNotFoundException, SQLException {
		
		Connection con = getDBConnection();
		String sql = "update customers set CustomerID = ?, CompanyName=?, ContactName=? where CustomerID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, custID);
		ps.setString(2, compName);
		ps.setString(3, contName);
		ps.setString(4, custID);
		ps.executeUpdate();
		con.close();
		
		return new ModelAndView("updatesuccess", "success", contName);
	}
	
	@RequestMapping("delete")
	public ModelAndView deleteCustomer() throws ClassNotFoundException, SQLException {
		Connection con = getDBConnection();
		String sql = "delete from customers where ContactName = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "Antonella"); // this value is hardcoded, so once it's deleted if you run it again it will throw an error
		ps.executeUpdate();
		con.close();
		
		return new ModelAndView("updatesuccess","success","Antonella has been deleted");
	}
}
