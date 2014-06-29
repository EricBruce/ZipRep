package yxs.snake.str.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import yxs.snake.str.util.Contants;
import yxs.snake.str.util.SqlParser;
import yxs.snake.str.util.UUIDGenerator;

/**宝贝Dao
 * @author Eric
 *
 */
@Repository
public class ProductDao {
	@Resource
	JdbcTemplate jdbcTemplate;
	private static Logger log = LoggerFactory.getLogger(ProductDao.class);
	
	/**
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryProductList(Map<String,Object> condition) throws Exception {
		//统计当前一个月的数量
		Calendar cur = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String endTime = df.format(cur.getTime());
		condition.put("endTime", endTime);
		cur.add(Calendar.MONTH, -1);
		String startTime = df.format(cur.getTime());
		condition.put("startTime", startTime);
		//
		String sql = "select t.*,u1.colorname,u1.all_amount,u2.sell_amount,u2.operation_at from t_product t "
				+ "left outer join "
				+ "(select t1.product_id," +
				"group_concat(t2.color_name separator '/') as colorname,sum(t2.spec_amount) as all_amount " +
				"from t_product t1, t_product_spec t2 where t2.product_id=t1.product_id group by t1.product_id)u1 "
				+ "on t.product_id=u1.product_id "
				+ "left outer join " +
				"(select t3.product_id,t3.operation_at,sum(t3.rec_amount)as sell_amount from t_product_rec t3 " +
				"where t3.operation_type='02' and t3.operation_at>='{startTime}' and t3.operation_at<'{endTime}' " +
				"group by t3.product_id )u2 " +
				"on t.product_id=u2.product_id " +
				" where 1=1 [,u1.product_no='{productNo}'][,u1.product_name='{productName}']" +
				"ORDER BY u2.sell_amount desc, u2.operation_at desc";
		//添加分页
		sql = SqlParser.addPagingInfoMySQL(sql);
		//解析参数
		sql = SqlParser.parse(sql, condition);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * @param condition
	 * @return
	 */
	public Long queryProductTotal(Map<String,Object> condition) throws Exception {
		//统计当前一个月的数量
		Calendar cur = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String endTime = df.format(cur.getTime());
		condition.put("endTime", endTime);
		cur.add(Calendar.MONTH, -1);
		String startTime = df.format(cur.getTime());
		condition.put("startTime", startTime);
		//
		String sql = "select count(*) from t_product t "
				+ "left outer join "
				+ "(select t1.product_id," +
				"group_concat(t2.color_name separator '/') as colorname,sum(t2.spec_amount) as all_amount " +
				"from t_product t1, t_product_spec t2 where t2.product_id=t1.product_id group by t1.product_id)u1 "
				+ "on t.product_id=u1.product_id "
				+ "left outer join " +
				"(select t3.product_id,t3.operation_at,sum(t3.rec_amount)as sell_amount from t_product_rec t3 " +
				"where t3.operation_type='02' and t3.operation_at>='{startTime}' and t3.operation_at<'{endTime}' " +
				"group by t3.product_id )u2 " +
				"on t.product_id=u2.product_id " +
				" where 1=1 [,u1.product_no='{productNo}'][,u1.product_name='{productName}']" +
				"ORDER BY u2.sell_amount desc, u2.operation_at desc";
		//解析参数
		sql = SqlParser.parse(sql, condition);
		Long count = jdbcTemplate.queryForLong(sql);
		return count;
	}
	
	/**获取颜色列表
	 * @return
	 */
	public List<Map<String,Object>> getColorList() {
		String sql = "select * from t_color";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**添加宝贝基本信息
	 * @param condition
	 * @return
	 */
	public int addProduct(Map<String,Object> condition) {
		String sql = "insert into t_product(product_id,product_no,product_price,product_name,product_alarm,product_img,create_by,create_at)"
				+ "values(?,?,?,?,?,?,?,?)";
		int ret = jdbcTemplate.update(sql, new Object[]{
				condition.get("product_id"),
				condition.get("product_no"),
				condition.get("product_price"),
				condition.get("product_name"),
				condition.get("product_alarm"),
				condition.get("product_img"),
				condition.get("create_by"),
				condition.get("create_at")
		});
		return ret;
	}
	
	/**编辑宝贝信息
	 * @param productID
	 * @param condition
	 * @return
	 */
	public int editProduct(String productID, Map<String,Object> condition) {
		String sql = "update t_product set product_no=?,product_name=?,product_price=?,product_alarm=?,product_img=? where product_id=?";
		int ret = jdbcTemplate.update(sql, new Object[]{
			condition.get("product_no"),
			condition.get("product_name"),
			condition.get("product_price"),
			condition.get("product_alarm"),
			condition.get("product_img"),
			productID
		});
		return ret;
	}
	
	/**删除宝贝规格
	 * @param productID
	 * @return
	 */
	public int delProductSpec(String productID) {
		String sql = "delete from t_product_spec where product_id=?";
		int ret = jdbcTemplate.update(sql, productID);
		return ret;
	}
	
	/**添加宝贝规格
	 * @param sp
	 * @return
	 */
	public int addProductSpec(Map<String,Object> sp) {
		String sql = "insert into t_product_spec(spec_id,color_id,color_name,spec_price,spec_amount,product_id) values (?,?,?,?,?,?)";
		int ret = jdbcTemplate.update(sql, new Object[]{
				sp.get("spec_id"),
				sp.get("color_id"),
				sp.get("color_name"),
				sp.get("spec_price"),
				sp.get("spec_amount"),
				sp.get("product_id")
		});
		return ret;
	}
	
	/**获取宝贝基本信息
	 * @param productID
	 * @return
	 */
	public Map<String,Object> getProductByID(String productID) {
		String sql = "select * from t_product where product_id=?";
		Map<String,Object> product = jdbcTemplate.queryForMap(sql, productID);
		return product;
	}
	
	/**获取宝贝规格列表
	 * @param productID
	 * @return
	 */
	public List<Map<String,Object>> getProductSpecList(String productID) {
		String sql = "select * from t_product_spec where product_id=?";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, productID);
		return list;
	}
	
	/**获取宝贝规格
	 * @param productID
	 * @return
	 */
	public List<Map<String,Object>> getProductSpec(String productID) {
		String sql  = "select t1.product_no,t1.product_id,t2.spec_id,t2.color_id,t2.color_name "
				+ "from t_product t1, t_product_spec t2 where t1.product_id=t2.product_id and t1.product_id=?";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, productID);
		return list;
	}
	
	/**获取操作类型
	 * @param flag
	 * @return
	 */
	public List<Map<String,Object>> getOperType(String flag) {
		String sql = "select * from t_dic where dictype_code='0001' and dic_flag=?";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, flag);
		return list;
	}
	
	/**更新宝贝规格
	 * @param specID
	 * @param operationType
	 * @param recAmount
	 * @return
	 */
	public int updateProductSpec(String specID, String operationType, int recAmount) {
		String sql = "update t_product_spec set spec_amount=spec_amount+? where spec_id=?";
		//出库
		if ("02".equals(operationType)) {
				sql = "update t_product_spec set spec_amount=spec_amount-? where spec_id=?";
		}
		int ret = jdbcTemplate.update(sql, new Object[]{recAmount, specID});
		return ret;
	}
	
	/**添加宝贝操作记录
	 * @param rec
	 * @return
	 */
	public int addProductRec(Map<String,Object> rec) {
		String sql = "insert into t_product_rec values(?,?,?,?,?,?,?,?,?)";
		int ret = jdbcTemplate.update(sql, new Object[]{
			UUIDGenerator.getUUID(),
			rec.get("rec_amount"),
			rec.get("color_id"),
			rec.get("color_name"),
			rec.get("product_id"),
			rec.get("product_no"),
			rec.get("operation_type"),
			rec.get("operation_by"),
			rec.get("operation_at")
		});
		return ret;
	}
	
	/**获取所有宝贝库存销量列表
	 * @param condition
	 * @return
	 */
	public List<Map<String,Object>> getAllSellProductAmount(Map<String,Object> condition) {
		try {
			String sql = "select t.product_id,t.product_name,u1.all_amount,u2.sell_amount from t_product t "
					+ "left OUTER join( "
					+ "select t1.product_id,sum(t1.spec_amount)as all_amount from t_product_spec t1 group by t1.product_id ) u1 "
					+ "on t.product_id=u1.product_id "
					+ "LEFT OUTER JOIN( "
					+ "select t2.product_id,sum(t2.rec_amount)as sell_amount from t_product_rec t2 where t2.operation_type='02' group by t2.product_id) u2 "
					+ "on t.product_id=u2.product_id "
					+ "order by sell_amount desc,all_amount desc";
			//添加分页
			sql = SqlParser.addPagingInfoMySQL(sql);
			//解析参数
			sql = SqlParser.parse(sql, condition);
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	/**获取所有宝贝库存销量记录数
	 * @param condition
	 * @return
	 */
	public int getAllSellProductAmountCount(Map<String,Object> condition) {
		try {
			String sql = "select count(*) from t_product t "
					+ "left OUTER join( "
					+ "select t1.product_id,sum(t1.spec_amount)as all_amount from t_product_spec t1 group by t1.product_id ) u1 "
					+ "on t.product_id=u1.product_id "
					+ "LEFT OUTER JOIN( "
					+ "select t2.product_id,sum(t2.rec_amount)as sell_amount from t_product_rec t2 where t2.operation_type='02' group by t2.product_id) u2 "
					+ "on t.product_id=u2.product_id";
			//解析参数
			sql = SqlParser.parse(sql, condition);
			int count = jdbcTemplate.queryForInt(sql);
			return count;
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}
	
	/**获取具体某宝贝的库存销量列表
	 * @param productID
	 * @return
	 */
	public List<Map<String,Object>> getAllSellProductAmount(String productID) {
		String sql = "select u1.color_name,u2.all_amount,u1.sell_amount from ( "
				+ "select t2.color_id,t2.color_name,sum(t2.rec_amount)as sell_amount from t_product_rec t2 where t2.product_id=? and t2.operation_type='02' "
				+ "group by color_id) u1 "
				+ "LEFT OUTER JOIN( "
				+ "select t1.color_id,t1.color_name,sum(t1.spec_amount)as all_amount from t_product_spec t1 where t1.product_id=? "
				+ "group by color_id) u2 "
				+ "on u1.color_id=u2.color_id "
				+ "union "
				+ "select u2.color_name,u2.all_amount,u1.sell_amount from ( "
				+ "select t2.color_id,t2.color_name,sum(t2.rec_amount)as sell_amount from t_product_rec t2 where t2.product_id=? and t2.operation_type='02' "
				+ "group by color_id) u1 "
				+ "RIGHT OUTER JOIN( "
				+ "select t1.color_id,t1.color_name,sum(t1.spec_amount)as all_amount from t_product_spec t1 where t1.product_id=? "
				+ "group by color_id) u2 "
				+ "on u1.color_id=u2.color_id "
				+ "order by sell_amount desc,all_amount desc";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, new Object[]{productID,productID,productID,productID});
		return list;
	}
	
	/**获取具体某宝贝的库存销量记录数
	 * @param productID
	 * @return
	 */
	public int getAllSellProductAmountCount(String productID) {
		String sql = "select count(*) from ( "
				+ "select u1.color_name,u2.all_amount,u1.sell_amount from ( "
				+ "select t2.color_id,t2.color_name,sum(t2.rec_amount)as sell_amount from t_product_rec t2 where t2.product_id=? and t2.operation_type='02' "
				+ "group by color_id) u1 "
				+ "LEFT OUTER JOIN( "
				+ "select t1.color_id,t1.color_name,sum(t1.spec_amount)as all_amount from t_product_spec t1 where t1.product_id=? "
				+ "group by color_id) u2 "
				+ "on u1.color_id=u2.color_id "
				+ "union "
				+ "select u2.color_name,u2.all_amount,u1.sell_amount from ( "
				+ "select t2.color_id,t2.color_name,sum(t2.rec_amount)as sell_amount from t_product_rec t2 where t2.product_id=? and t2.operation_type='02' "
				+ "group by color_id) u1 "
				+ "RIGHT OUTER JOIN( "
				+ "select t1.color_id,t1.color_name,sum(t1.spec_amount)as all_amount from t_product_spec t1 where t1.product_id=? "
				+ "group by color_id) u2 "
				+ "on u1.color_id=u2.color_id) t";
		int count = jdbcTemplate.queryForInt(sql, new Object[]{productID,productID,productID,productID});
		return count;
	}
	
	/**获取宝贝销量情况
	 * @param productID
	 * @return
	 */
	public List<Map<String,Object>> getProductsellRec(Map<String,Object> condition ) {
		String sql = "select t1.product_name,t1.product_img,DATE_FORMAT(t2.operation_at,'%Y-%m-%d %T')as operation_at,t2.color_name,t2.rec_amount "
				+ "from t_product t1,t_product_rec t2 ";
		Map<String,Object> asertCon = assertProductSellCondition(sql, condition, true);
		sql = (String)asertCon.get("sql");
		List<Object> args = (List<Object>)asertCon.get("args");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		return list;
	}
	
	/**获取宝贝销量情况
	 * @param productID
	 * @return
	 */
	public int getProductsellRecCount(Map<String,Object> condition) {
		String sql = "select count(*) "
				+ "from t_product t1,t_product_rec t2 ";
		Map<String,Object> asertCon = assertProductSellCondition(sql, condition, false);
		sql = (String)asertCon.get("sql");
		List<Object> args = (List<Object>)asertCon.get("args");
		int count = jdbcTemplate.queryForInt(sql, args.toArray());
		return count;
	}
	
	/**获取销量条件设置
	 * @param sql
	 * @param condition
	 * @param page
	 * @return
	 */
	public Map<String,Object> assertProductSellCondition(String sql, Map<String,Object> condition, boolean page) {
		List<Object> args = new ArrayList<Object>();
		//开始时间
		if (condition.containsKey("startTime")) {
			sql += " where t2.operation_at>? ";
			args.add(condition.get("startTime"));
		}
		//结束时间
		if (condition.containsKey("endTime")) {
			if (args.size() < 1) {
				sql += " where ";
			} else  {
				sql += " and ";
			}
			sql += "t2.operation_at<?";
			args.add(condition.get("endTime"));
		}
		//排序方式
		sql += " order by t2.operation_at desc ";
		//需要分页
		if (page) {
			if (condition.containsKey("limit") && condition.containsKey("offset")) {
				sql += " limit ? offset ?";
				args.add(condition.get("limit"));
				args.add(condition.get("offset"));
			}
		}
		Map<String,Object> asertCon = new HashMap<String,Object>();
		asertCon.put("sql", sql);
		asertCon.put("args", args);
		return asertCon;
	}
	
	/**获取分类销售量
	 * @param condition
	 * @return
	 */
	public List<Map<String,Object>> getProductsellTrend(String sql) {
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}
}
