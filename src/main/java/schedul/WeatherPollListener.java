package schedul;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import uitl.AppConfigUtil;
import util.JDBCConnectionUtil;
import util.LogLevel;
import util.LogUtil;

@WebListener
public class WeatherPollListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

	    String intervalPoll=null;
        try
        {
            intervalPoll = JDBCConnectionUtil.getIntervalPoll();
            AppConfigUtil.handlePollerTimeFromDB(intervalPoll);
        } catch (NumberFormatException e)
        {
            LogUtil.log(LogLevel.ERROR, e.getMessage(), getClass());
        } catch (SQLException e)
        {
            LogUtil.log(LogLevel.ERROR, e.getMessage(), getClass());
        }
        
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	    
		WeatherPollerTimerManager.stop();

	}

}
