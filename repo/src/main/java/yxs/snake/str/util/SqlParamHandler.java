package yxs.snake.str.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *Title:SqlParamHandler.java
 *Description:处理数据库查询sql中的非法参数
 *
 *Company:sinosoft
 *@author lizy
 *@email: lizhiyong@sinosoft.com.cn
 *@date 2012-6-26
 *@version 1.0
 */

public class SqlParamHandler {

	/**
	 * 处理sql查询中的参数
	 * @param condition 查询参数
	 */
	public Map<String,Object> handleSqlParamItems(Map<String,Object> condition){
		Map<String,Object> ret = new HashMap<String,Object>();
		for(Entry<String,Object> entry : condition.entrySet() ){
			if((entry.getKey() != null && entry.getValue() != null) && (!"".equals(entry.getKey()) &&  !"".equals(entry.getValue()))){//查询参数不为空
				ret.put(entry.getKey(), handleSqlParamItem(entry.getValue().toString()));
			}/*else{
				ret.put(entry.getKey(), entry.getValue());
			}*/
		}
		return ret;
	}
	
	/**
	 * 把‘%’,''','_'替换为‘\%’,'\'','\_'。sql查询中like后面需要用escape将'\'作为转义字符
	 * @param param 需要处理的参数
	 * @return 处理过的查询参数
	 */
	public String handleSqlParamItem(String param){
		//去掉字符串两边的空字符串
		param = param.trim();
		//处理之后的字符串
		StringBuffer sb = new StringBuffer();
		if(param != null){
			//处理'%' ,'_' , ''',转换为'\%' ,'\_' , '\''
			Pattern p = Pattern.compile("%|_|'");
			Matcher matcher = p.matcher(param.trim());
			//匹配个数标记
			int index = 0 ;
			while(matcher.find()){
				//将当前匹配子串替换为指定字符串，
				//并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个 StringBuffer 对象里
				matcher.appendReplacement(sb, "\\\\" + matcher.group(index));
			}
			//将最后一次匹配后剩余的字符串添加到sb中
			matcher.appendTail(sb);
		}
		return sb.toString();
	}
	
}
