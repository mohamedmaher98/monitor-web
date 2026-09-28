package schedul;

import util.JDBCConnectionUtil;
public class FillWeatherTablesTask implements Runnable{

	@Override
	public void run() {
	   
          JDBCConnectionUtil util = new JDBCConnectionUtil();
          util.fillWeatherTables();
          System.out.println("the tables created sucssfully");
         
	}

}
