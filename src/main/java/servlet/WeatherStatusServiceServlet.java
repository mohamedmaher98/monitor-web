package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import schedul.WeatherPollerTimerManager;
import uitl.AppConfigUtil;

public class WeatherStatusServiceServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = null;

		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		String action = request.getParameter("action");
		if ("stop".equals(action)) {
			if (!WeatherPollerTimerManager.isRunning())
				writer.print("the service is already stoped");
			else {

				WeatherPollerTimerManager.stop();
				writer.print("the service stopped");
			}
		} else if ("start".equals(action)) {
			if (WeatherPollerTimerManager.isRunning()) {
				writer.print("the service is already running");
			} else {
				try {
					WeatherPollerTimerManager.start(writer);
				} catch (NumberFormatException e) {
					System.out.println(e.getMessage());
					writer.print("invlaed input");
					e.printStackTrace();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					writer.print("Data Base Error");
					e.printStackTrace();
				}

			}
		} else if ("isRunning".equals(action))

		{

			if (WeatherPollerTimerManager.isRunning())
				writer.print("the service now is running");
			else
				writer.print("the service is not running");
		} else if ("setTime".equals(action)) {
			String sec = request.getParameter("seconds");
			AppConfigUtil.handlePollerTimeFromUrl(sec, writer);
		} else {
			writer.print("not valid param");
		}

	}

}
