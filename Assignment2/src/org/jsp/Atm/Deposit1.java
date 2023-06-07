package org.jsp.Atm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Deposit1")
public class Deposit1 extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String amount=req.getParameter("amount");
		int amount1 = Integer.parseInt(amount);
		
		String amount2 = req.getParameter("amount1");
		int amount3 = Integer.parseInt(amount2);
		PrintWriter writer = resp.getWriter();
		if (amount1==amount3 && amount1>0) 
		{
		HttpSession session = req.getSession();
		String mobile =(String) session.getAttribute("mobile");
		String pin =(String) session.getAttribute("pin");
		int pin1 = Integer.parseInt(pin);
		 String url ="jdbc:mysql://localhost:3306/tech40?user=root&password=12345";
		 ////sno, name, mobileno, pin, amount
		 String select = "select * from atm where mobileno=? and pin=?";
		 String deposit = "update atm set amount=? where mobileno=?";
		 
			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps1 = connection.prepareStatement(select);
				ps1.setString(1, mobile);
				ps1.setInt(2, pin1);
				ResultSet rs = ps1.executeQuery();
				rs.next();
				int actAmount = rs.getInt(5);
				actAmount = actAmount+amount1;
				PreparedStatement ps = connection.prepareStatement(deposit);
				ps.setInt(1, actAmount);
				ps.setString(2, mobile);
				int num = ps.executeUpdate();
				if (num!=0) {
					RequestDispatcher dispatcher = req.getRequestDispatcher("Welcome.html");
					dispatcher.include(req,resp);
					writer.println("<center><h1>Amount is Depositrd</h1></center>");
				} else {
					RequestDispatcher dispatcher = req.getRequestDispatcher("Deposite.html");
					dispatcher.include(req,resp);
					writer.println("<center><h1>Amount is not deposited \n please ente valid information</h1></center>");
				}
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("Deposite.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1>Please enter correct amount</h1></center>");
		}
	}
}
