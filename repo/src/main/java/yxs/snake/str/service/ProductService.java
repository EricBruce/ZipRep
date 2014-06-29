package yxs.snake.str.service;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yxs.snake.str.dao.ProductDao;
import yxs.snake.str.util.Contants;
import yxs.snake.str.util.SqlParamHandler;
import yxs.snake.str.util.UUIDGenerator;

/**宝贝业务类
 * @author Eric
 *
 */
@Service
@Transactional
public class ProductService {
	@Resource
	ProductDao productDao;
	@Resource
	SqlParamHandler sqlParamHandler;
	
	/**获取宝贝列表
	 * @param list
	 * @return
	 */
	public List<Map<String,Object>> getProductList(Map<String,Object> condition)throws Exception {
		if (condition != null) 
			condition = sqlParamHandler.handleSqlParamItems(condition);
		List<Map<String,Object>> list = productDao.queryProductList(condition);
		return list;
	}
	
	/**获取宝贝总数
	 * @param condition
	 * @return
	 */
	public Long getProductTotal(Map<String,Object> condition) throws Exception {
		if (condition != null) 
			condition = sqlParamHandler.handleSqlParamItems(condition);
		Long count = productDao.queryProductTotal(condition);
		return count;
	}
	
	/**获取颜色列表
	 * @return
	 */
	public List<Map<String,Object>> getColorList(){
		List<Map<String,Object>> list = productDao.getColorList();
		return list;
	}
	
	/**新增宝贝
	 * @param condition
	 * @return
	 */
	public int addProduct(Map<String,Object> condition) throws Exception {
		int ret = 1;
		//宝贝基本信息
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//当前时间
		String create_at = df.format(new Date());
		condition.put("create_at", create_at);
		String product_id = UUIDGenerator.getUUID();
		condition.put("product_id", product_id);
		ret &= productDao.addProduct(condition);
		//宝贝规格
		String spec = (String)condition.get("spec");
		String[] spS = spec.split("\\|");
		for (int i = 0; i < spS.length; i++) {
			Map<String,Object> sp = new HashMap<String,Object>();
			sp.put("product_id", product_id);
			String[] spStr = spS[i].split("_");
			//未漏数
			if (spStr.length == 4) {
				sp.put("color_id", spStr[0]);
				sp.put("color_name", spStr[1]);
				sp.put("spec_price", spStr[2 ]);
				sp.put("spec_amount", spStr[3 ]);
				String spec_id = UUIDGenerator.getUUID();
				sp.put("spec_id", spec_id);
				ret &= productDao.addProductSpec(sp);
			}
		}
		return ret;
	}
	
	/**编辑宝贝
	 * @param condition
	 * @return
	 */
	public int editProduct(String productID, Map<String,Object> condition) throws Exception {
		int ret = 1;
		//宝贝基本信息
		ret &= productDao.editProduct(productID, condition);
		//宝贝规格
		//删除宝贝之前的规格
		productDao.delProductSpec(productID);
		//新增新规格
		String spec = (String)condition.get("spec");
		String[] spS = spec.split("\\|");
		for (int i = 0; i < spS.length; i++) {
			Map<String,Object> sp = new HashMap<String,Object>();
			sp.put("product_id", productID);
			String[] spStr = spS[i].split("_");
			//未漏数
			if (spStr.length == 4) {
				sp.put("color_id", spStr[0]);
				sp.put("color_name", spStr[1]);
				sp.put("spec_price", spStr[2 ]);
				sp.put("spec_amount", spStr[3 ]);
				String spec_id = UUIDGenerator.getUUID();
				sp.put("spec_id", spec_id);
				ret &= productDao.addProductSpec(sp);
			}
		}
		return ret;
	}
	
	/**获取宝贝基本信息
	 * @param productID
	 * @return
	 */
	public Map<String,Object> getProductByID(String productID) {
		Map<String,Object> product = productDao.getProductByID(productID);
		return product;
	}
	
	/**获取宝贝规格列表
	 * @param productID
	 * @return
	 */
	public List<Map<String,Object>> getProductSpecList(String productID) {
		List<Map<String,Object>> list = productDao.getProductSpecList(productID);
		return list;
	}
	
	/**获取宝贝规格
	 * @param productID
	 * @return
	 */
	public List<Map<String,Object>> getProductSpec(String productID) {
		List<Map<String,Object>> list = productDao.getProductSpec(productID);
		return list;
	}
	
	/**获取操作类型
	 * @param flag
	 * @return
	 */
	public List<Map<String,Object>> getOperType(String flag) {
		List<Map<String,Object>> list = productDao.getOperType(flag);
		return list;
	}
	
	/**完成一次操作
	 * @param userName
	 * @param list
	 * @return
	 */
	public int productOper(String userName, List<Map<String,Object>> list) {
		int ret = 1;
		//当前时间
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String operation_at = df.format(new Date());
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> m = list.get(i);
			m.put("operation_by", userName);
			m.put("operation_at", operation_at);
			//添加宝贝操作记录
			ret &= productDao.addProductRec(m);
			//更新宝贝规格记录
			String specID = (String)m.get("spec_id");
			String operationType = (String)m.get("operation_type");
			int recAmount = Integer.parseInt((String)m.get("rec_amount"));
			ret &= productDao.updateProductSpec(specID, operationType, recAmount);
		}
		return ret;
	}
	
	/**获取所有宝贝库存销量列表
	 * @param condition
	 * @return
	 */
	public List<Map<String,Object>> getAllSellProductAmount(Map<String,Object> condition) {
		if (condition != null) {
			condition =sqlParamHandler.handleSqlParamItems(condition);
		}
		List<Map<String,Object>> list = productDao.getAllSellProductAmount(condition);
		return list;
	}
	
	/**获取所有宝贝库存销量记录数
	 * @param condition
	 * @return
	 */
	public int getAllSellProductAmountCount(Map<String,Object> condition) {
		if (condition != null) {
			condition =sqlParamHandler.handleSqlParamItems(condition);
		}
		int count = productDao.getAllSellProductAmountCount(condition);
		return count;
	}
	
	/**获取所有宝贝库存销量列表
	 * @param condition
	 * @return
	 */
	public List<Map<String,Object>> getProductsellAmount(String productID) {
		List<Map<String,Object>> list = productDao.getAllSellProductAmount(productID);
		return list;
	}
	
	/**获取所有宝贝库存销量记录数
	 * @param condition
	 * @return
	 */
	public int getProductsellAmountCount(String productID) {
		int count = productDao.getAllSellProductAmountCount(productID);
		return count;
	}
	
	/**获取所有宝贝销售记录列表
	 * @param condition
	 * @return
	 */
	public List<Map<String,Object>> getProductsellRec(Map<String,Object> condition) {
		List<Map<String,Object>> list = productDao.getProductsellRec(condition);
		return list;
	}
	
	/**获取所有宝贝销售记录记录数
	 * @param condition
	 * @return
	 */
	public int getProductsellRecCount(Map<String,Object> condition) {
		int count = productDao.getProductsellRecCount(condition);
		return count;
	}
	
	/**获取分类统计
	 * @param condition
	 * @return
	 */
	public List<Map<String,Object>> getProductsellTrend(Map<String,Object> condition) {
		List<Map<String,Object>> list = new ArrayList<>();
		String  catType = (String)condition.get("catType");
		String sql = "";
		//周
		if (Contants.STATTYPE_WEEK.equals(catType)) {
			List<Map<String,Object>> weekList = new ArrayList<>();
			String[] a = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
			for (int i = 0; i < 7; i++) {
				Map<String,Object> m = new HashMap<>();
				m.put("opt_time", a[i]);
				m.put("sell_amount", 0);
				weekList .add(m);
			}
			sql = "select sum(rec_amount)as sell_amount,WEEKDAY(operation_at)as week_day,WEEKOFYEAR(CURDATE())as week_of_year "
					+ "from t_product_rec where week(operation_at)=WEEKOFYEAR(CURDATE()) group by WEEKDAY(operation_at) order by operation_at desc";
			list = productDao.getProductsellTrend(sql);
			for (int i = 0; i<list.size(); i++ ){
				Map<String,Object> rec = list.get(i);
				int index = (int)rec.get("week_day");
				weekList.get(index).put("sell_amount", rec.get("sell_amount"));
			}
			return weekList;
		//月
		} else if (Contants.STATTYP_MONTH.equals(catType)) {
			List<Map<String,Object>> monthList = new ArrayList<>();
			int days = Contants.getMonthDays();
			for (int i = 1; i <= days; i++) {
				Map<String,Object> m = new HashMap<>();
				m.put("opt_time",  i+"号");
				m.put("sell_amount", 0);
				monthList.add(m);
			}
			sql = "select sum(rec_amount)as sell_amount,DAY(operation_at)as day_of_month,month(CURDATE())as month_of_year "
					+ "from t_product_rec where month(operation_at)=month(CURDATE()) group by day(operation_at) order by operation_at desc";
			list = productDao.getProductsellTrend(sql);
			for (int i = 0; i<list.size(); i++ ){
				Map<String,Object> rec = list.get(i);
				int index = (int)rec.get("day_of_month");
				monthList.get(index-1).put("sell_amount", rec.get("sell_amount"));
			}
			return monthList;
		//年(默认)
		} else /*if (Contants.STATTYPE_YEAR.equals(catType)) */{
			String[] a = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
			List<Map<String,Object>> yearList = new ArrayList<>();
			for (int i = 0; i < 12; i++) {
				Map<String,Object> m = new HashMap<>();
				m.put("opt_time", a[i]);
				m.put("sell_amount", 0);
				yearList.add(m);
			}
			sql = "select sum(rec_amount)as sell_amount,month(operation_at)as month_of_year,year(CURDATE())as year_of_year "
					+ "from t_product_rec where year(operation_at)=year(CURDATE()) group by month(operation_at) order by operation_at desc";
			list = productDao.getProductsellTrend(sql);
			for (int j = 0 ; j < list.size(); j++) {
				Map<String,Object> rec = list.get(j);
				int index = (int)rec.get("month_of_year");
				yearList.get(index-1).put("sell_amount", rec.get("sell_amount"));
			}
			return yearList;
		}
	}
	
	
}
