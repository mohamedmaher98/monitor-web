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

public class WeatherStatusServiceServlet extends HttpServlet
{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter writer = null;

        try
        {
            writer = response.getWriter();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        String action = request.getParameter("action");
        if ("stop".equals(action))
        {
            WeatherPollerTimerManager.stop(writer);
        }

        else if ("start".equals(action))
        {
            WeatherPollerTimerManager.start(writer);
        } else if ("isRunning".equals(action))

        {

            writer.print(WeatherPollerTimerManager.ServiceStatus());
        } else if ("setTime".equals(action))
        {
            String sec = request.getParameter("seconds");
            AppConfigUtil.handlePollerTimeFromUrl(sec, writer);
        } else
        {
            writer.print("not valid param");
        }

    }

}
