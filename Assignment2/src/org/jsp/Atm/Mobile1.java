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
import javax.servlet.http.HttpSession;

@WebServlet("/Mobile1")
public class Mobile1 extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String amount=req.getParameter("amount");
		int amount1 = Integer.parseInt(amount);
		String mobieno = req.getParameter("mobilno");
		
		HttpSession session = req.getSession();
		String mobile1 = (String) req.getAttribute("mobile");
		String pin1 = (String) req.getAttribute("pin");
		
		int pin = Integer.parseInt(pin1);
		String url ="jdbc:mysql://localhost:3306/tech40?user=root&password=12345";
		////sno, name, mobileno, pin, amount
		String select = "select * from atm where mobileno=? and pin=?";
		String select1 = "select * from atm where mobileno=?";
		String update = "update atm set amount=? where mobileno=?";
		
		PrintWriter writer = resp.getWriter();
		
		try 
		{
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mobile1);
			ps.setInt(2, pin);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int actAmount = rs.getInt(5);
				actAmount-=amount1;
				PreparedStatement ps1 = connection.prepareStatement(update);
				ps1.setString(2, mobile1); 
				ps1.setInt(1, actAmount);
				int num1 = ps1.executeUpdate();
				PreparedStatement ps2 = connection.prepareStatement(select1);
				ps2.setString(1, mobieno);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					int actAmount2 = rs2.getInt(5);
					actAmount2+=amount1;
					PreparedStatement ps4 = connection.prepareStatement(update);
					ps4.setInt(1, actAmount2);
					ps4.setString(2, mobieno);
					int num2 = ps4.executeUpdate();
					if (num2!=0) {
						System.out.println("Amount is transfre to : "+mobieno);
					} else {
						System.err.println("Transacation Failed");
					}
				} else {
					System.out.println("Tha number doesnot hava Online transactions");
				}
			}
			else {
				System.out.println("Invalid Input Please enter valid Details");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
