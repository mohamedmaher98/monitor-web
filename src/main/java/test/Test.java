package test;

import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;

import classes.TempViewPage;
import schedul.WeatherPollerTimerManager;
import util.JDBCConnectionUtil;

public class Test
{

    public static void main(String[] args)
    {
     
        JDBCConnectionUtil util = new JDBCConnectionUtil();
        TempViewPage fillTempViewPage=null;
        
        try
        {
            fillTempViewPage = util.fillTempViewPage(WeatherPollerTimerManager.ServiceStatus());
        } catch (SQLException e)
        {

            System.out.println(e.getMessage());
        }
        
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.valueToTree(fillTempViewPage));


    }
}
