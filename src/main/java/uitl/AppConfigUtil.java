package uitl;

import java.io.PrintWriter;

import util.LogLevel;
import util.LogUtil;

import schedul.WeatherPollerTimerManager;

public class AppConfigUtil {

	public static void handlePollerTimeFromUrl(String s, PrintWriter writer) {
		int seconds = 0;

		try {
			seconds = Integer.valueOf(s);
			if (seconds < 30) {
				writer.print("Seconds Cannot be less than 30 sec");
				LogUtil.log(LogLevel.ERROR, "Seconds Cannot be less than 30 sec", AppConfigUtil.class);
				return;
			}
		} catch (Exception e) {
			writer.print("Invalid Input For Seconds");
			LogUtil.log(LogLevel.ERROR, "Invalid Input For Seconds", AppConfigUtil.class);
			return;

		}
		WeatherPollerTimerManager.start(seconds);
		writer.print("the service started successfully");
		LogUtil.log(LogLevel.INFO, "the service started successfully", AppConfigUtil.class);

	}

	public static void handlePollerTimeFromDB(String s, PrintWriter writer) {
		int seconds = 0;

		try {
			seconds = Integer.valueOf(s);
			if (seconds <= 30) {
				seconds = 30;
				LogUtil.log(LogLevel.ERROR,"this value is the default value because the value from the db is less than 30", AppConfigUtil.class);

			}
			WeatherPollerTimerManager.start(seconds);
			writer.print("the service started successfully");
			LogUtil.log(LogLevel.INFO, "the service started successfully", AppConfigUtil.class);
		} catch (Exception e) {
			WeatherPollerTimerManager.start(30);
			writer.print("the service started successfully");
			LogUtil.log(LogLevel.ERROR, e.getMessage() + "the value from the db is: " + s
					+ "the service is running now with deafult time poll ", AppConfigUtil.class);

		}

	}
}
