package schedul;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.FontUIResource;

import uitl.AppConfigUtil;
import util.JDBCConnectionUtil;

public class WeatherPollerTimerManager {

	private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private static ScheduledFuture<?> future;

	public static void start(PrintWriter writer) throws NumberFormatException, SQLException {
		if (isDone()) {
			AppConfigUtil.handlePollerTimeFromDB( JDBCConnectionUtil.getIntervalPoll(), writer);
		}

	}

	public static void stop() {
		if (!isDone()) {
			future.cancel(true);
			future = null;
		}

	}

	public static void shutDown() {
		executor.shutdown();
	}

	public static boolean isRunning() {
		return !isDone();
	}

	private static boolean isDone() {
		return future == null || future.isDone();
	}

	public static void start(int time) {
		stop();
		future = executor.scheduleWithFixedDelay(new ScheduledWeatherPoll(), 0, time, TimeUnit.SECONDS);
	}
}