package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import classes.Server;
import util.JDBCConnectionUtil;

public class MonitorServiceServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		JDBCConnectionUtil connectionUtil = new JDBCConnectionUtil();
		List<Server> servers = new ArrayList<Server>();
		String jsonServer = "";

		try {
			servers = connectionUtil.loadServers();
			ObjectMapper mapper = new ObjectMapper();

			try {
				jsonServer = mapper.writeValueAsString(servers);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} catch (SQLException e) {
			System.out.println("faild: " + e.getMessage());
			response.setStatus(500);
		}
		PrintWriter writer;
		try {
			response.setContentType("application/json");
			writer = response.getWriter();
			writer.print(jsonServer);
		} catch (IOException e) {
			System.out.println("error : " + e.getMessage());
		}

	}

}
