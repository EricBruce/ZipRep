package yxs.snake.str.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**常量工具类
 * @author Jony
 *
 */
public class Contants {
	//统计类型
	//按周统计
	public static String STATTYPE_WEEK = "1";
	//按月统计
	public static String STATTYP_MONTH = "2";
	//按年统计
	public static String STATTYPE_YEAR = "3";
	
	/**获取当前月天数
	 * @param YYMM
	 * @return
	 * @throws ParseException
	 */
	public   static   int   getMonthDays(){   
	   Calendar cal = Calendar.getInstance();
	   int   days   =   cal.getActualMaximum(Calendar.DAY_OF_MONTH);   
	   return days;
	}
	
	public static void main(String[] args) {
		String[] a = {};
		
	}
}
