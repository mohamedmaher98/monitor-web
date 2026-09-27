package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import classes.TempViewPage;
import schedul.WeatherPollerTimerManager;
import util.JDBCConnectionUtil;
import util.LogLevel;
import util.LogUtil;

public class TempViewPageServlet extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        doGet(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        JDBCConnectionUtil util = new JDBCConnectionUtil();
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer;
        try
        {
            resp.setContentType("application/json");
            writer = resp.getWriter();
        } catch (IOException e)
        {
            LogUtil.log(LogLevel.ERROR, e.getMessage(), TempViewPageServlet.class);
            return;
        }
        TempViewPage temp = null;

        try
        {
             temp = util.fillTempViewPage(WeatherPollerTimerManager.ServiceStatus());
           
        } catch (SQLException e)
        {
            LogUtil.log(LogLevel.ERROR, e.getMessage(), TempViewPageServlet.class);
            resp.setStatus(500);
            writer.print("the data base have an error");
            return;
        }
        JsonNode json = null;
        json = mapper.valueToTree(temp);
        writer.print(json);
    }
}
