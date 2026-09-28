package schedul;

import util.JDBCConnectionUtil;
public class FillWeatherTablesTask implements Runnable{

	@Override
	public void run() {
	   
          JDBCConnectionUtil util = new JDBCConnectionUtil();
          util.fillWeatherTables();
       
         
	}

}
