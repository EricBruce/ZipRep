package yxs.snake.str.util;

import java.util.Map;

public class SqlParser {
	
	public static String parse(String sql, Map<String, Object> paramsMap) throws Exception {
		if(sql == null || "".equals(sql)) {
			return sql;
		}
		//获得变量的起始标记位置
		int start = sql.indexOf("{");
		if(start == -1) {
			return sql;
		}
		int end = sql.indexOf("}");
		//获得变量
		String variable = sql.substring(start + 1, end);
		//获得变量的值
		Object value = null;
		value = paramsMap.get(variable);
		
		//获得变量的性质
		int start2 = sql.indexOf("[");
		int end2 = sql.indexOf("]");
		
		if(value == null) {
			if(start2 != -1 && start2 < start) {
				sql = sql.substring(0, start2) + sql.substring(end2 + 1);
			}else {
				throw new Exception("In parameter map(" + paramsMap.toString() + "),there is not a value for variable '" + variable + "'");
			}
		}else {
			sql = sql.substring(0, start) + value + sql.substring(end + 1);
			if(start2 != -1 && start2 < start) {
				sql = sql.replaceFirst("\\[", "");
				sql = sql.replaceFirst("\\]", "");
			}
		}
		return parse(sql, paramsMap);
	}
	
	
	public static String addPagingInfoMySQL(String sql) {
		return sql + " limit {limit} offset {offset}";
	}
	
	public static String addPagingInfo(String sql) {
		return "select * from (" + sql + ") where rn between {offset} and {offset} + {limit} - 1";
	}
}
