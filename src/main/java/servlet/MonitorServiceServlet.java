package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Server;
import util.JDBCConnectionUtil;

public class MonitorServiceServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		JDBCConnectionUtil connectionUtil = new JDBCConnectionUtil();
		List<Server> servers = new ArrayList<Server>();
		try {
			servers = connectionUtil.loadServers();
		} catch (SQLException e) {
			System.out.println("faild: " + e.getMessage());
		}
		PrintWriter writer;
		try {
			 writer = response.getWriter();
			 writer.print("this servlet is working");
		} catch (IOException e) {
			System.out.println("error : " + e.getMessage());
		}

	}

}
