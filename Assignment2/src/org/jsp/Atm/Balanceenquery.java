package org.jsp.Atm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Balanceenquery")
public class Balanceenquery extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mobile =req.getParameter("mobile");
		String pin1 = req.getParameter("pin1");	
		int pin =Integer.parseInt(pin1);
		
		 ////sno, name, mobileno, pin, amount
		String url = "jdbc:mysql://localhost:3306/tech40?user=root&password=12345";
		String login = "select * from atm where mobileno=? and pin=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url);
		PreparedStatement ps= connection.prepareStatement(login);
		ps.setString(1, mobile);
		ps.setInt(2, pin);
		ResultSet rs= ps.executeQuery();
		PrintWriter writer = resp.getWriter();
		if (rs.next()) {
			int num=rs.getInt(5);
			RequestDispatcher dispatcher = req.getRequestDispatcher("BalanceEnquery.html");
			dispatcher.include(req, resp);
			writer.println("<center><h>num</h></center>");
			System.out.println(num);
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("BalanceEnquery.html");
			dispatcher.include(req, resp);
			writer.println("<center><h>Invalid Input</h></center>");
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
