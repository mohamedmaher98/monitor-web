package uitl;

import java.io.PrintWriter;

import schedul.WeatherPollerTimerManager;

public class AppConfigUtil {

	public static void handlePollerTimeFromUrl(String s, PrintWriter writer) {
		int seconds = 0;

		try {
			seconds = Integer.valueOf(s);
			if (seconds < 30) {
				writer.print("Seconds Cannot be less than 30 sec");
				return;
			}
		} catch (Exception e) {
			writer.print("Invalid Input For Seconds");
			return;

		}
		WeatherPollerTimerManager.start(seconds);
		writer.print("the service started successfully");

	}

	public static void handlePollerTimeFromDB(String s, PrintWriter writer) {
		int seconds = 0;

		try {
			seconds = Integer.valueOf(s);
			if (seconds <= 30) {
				seconds = 30;
				System.out.println("this value is the default value because the value from the db is less than 30");
			}
			WeatherPollerTimerManager.start(seconds);
			writer.print("the service started successfully");
		} catch (Exception e) {
			WeatherPollerTimerManager.start(30);
			writer.print("the service started successfully");
			System.out.println(e.getMessage() + "the value from the db is: " + s
					+ "the service is running now with deafult time poll ");

		}

	}
}
