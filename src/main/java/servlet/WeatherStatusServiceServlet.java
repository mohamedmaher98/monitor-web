package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import schedul.WeatherPollerTimerManager;

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
				WeatherPollerTimerManager.start();
				writer.print("the service started successfully");
			}
		} else if ("isRunning".equals(action))

		{

			if (WeatherPollerTimerManager.isRunning())
				writer.print("the service now is running");
			else
				writer.print("the service is not running");
		} else if ("setTime".equals(action)) {
			int seconds = 0;
			boolean flag = true;
			try {
				seconds = Integer.valueOf(request.getParameter("seconds"));
			} catch (Exception e) {
				writer.print("Invalid Input For Seconds");
				flag = false;
			}
			if (seconds < 30 && flag) {
				writer.print("Seconds Cannot be less than 30 sec");
				flag = false;
			}
			if (flag) {
				WeatherPollerTimerManager.start(seconds);
				writer.print("the service started successfully");
			}
		} else {
			writer.print("not valid param");
		}

	}

}
