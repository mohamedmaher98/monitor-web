package schedul;

import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import util.LogLevel;
import util.LogUtil;

public class WeatherPollerTimerManager
{

    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> future = null;
    private static FillWeatherTablesTask fillWeatherTablesTask = new FillWeatherTablesTask();
    private static boolean not_reported = true;


    public static void start(PrintWriter writer)
    {
        if (future == null || future.isDone())
        {
            future = executor.scheduleWithFixedDelay(fillWeatherTablesTask, 0, 60, TimeUnit.SECONDS);
            writer.print("the service stared successfully");

        } else
        {
            writer.print("The service already starred");
        }
    }

    public static void start(int seconds)
    {
        stop();
        future = executor.scheduleWithFixedDelay(fillWeatherTablesTask, 0, seconds, TimeUnit.SECONDS);
    }

    public static void stop()
    {
        if (future != null)
        {
            future.cancel(true);
            future = null;
        }
    }

    public static void stop(PrintWriter writer)
    {
        if (future != null)
        {
            future.cancel(true);
            future = null;
            writer.print("The service Stopped Successfully");
        } else
        {
            writer.print("The service already Stopped");
        }

    }


    public static String ServiceStatus()
    {

        if (future == null)
        {
            not_reported = true;
            return "STOPPED";

        } else if (!future.isDone())
        {
            not_reported = true;
            return "RUNNING";
        } else
        {
            if (not_reported)
            {
                LogUtil.log(LogLevel.ERROR, "the service is crashed", WeatherPollerTimerManager.class);
                not_reported = false;
            }
            return "CRASHED";
        }
    }


}
