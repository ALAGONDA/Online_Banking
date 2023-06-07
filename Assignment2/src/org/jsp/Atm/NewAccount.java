package org.jsp.Atm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/NewAccount")
public class NewAccount extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//sno, name, mobileno, pin, amount
		String name = request.getParameter("name");
		String mobilenumber = request.getParameter("mobileno");
		String pin = request.getParameter("pin");
		String cnfpin = request.getParameter("cnfpin");
		int newPin = Integer.parseInt(pin);
		int cnfPin = Integer.parseInt(cnfpin);
		PrintWriter writer = response.getWriter();
		if (newPin==cnfPin) 
		{
			String url ="jdbc:mysql://localhost:3306/tech40?user=root&password=12345";
			String insert = "insert into atm (name, mobileno, pin) values(?,?,?)";
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps= connection.prepareStatement(insert);
				
				ps.setString(1, name);
				ps.setString(2, mobilenumber);
				ps.setInt(3, newPin);
				
				int num1 = ps.executeUpdate();
				if (num1!=0) {
					RequestDispatcher dispatcher= request.getRequestDispatcher("Welcome.html");
					dispatcher.include(request,response);
					writer.println("<center><h1>Login sucessfully</h1></center>");
				} else {
					RequestDispatcher dispatcher= request.getRequestDispatcher("Welcome.html");				
					dispatcher.include(request,response);
					writer.println("<center><h1>Invalid Input</h1></center>");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			RequestDispatcher dispatcher= request.getRequestDispatcher("NewAccount.html");
			dispatcher.include(request,response);
			writer.println("<center><h1>BOTH PINS ARE MISS-MATCHED</h1></center>");
		}
	}


}
