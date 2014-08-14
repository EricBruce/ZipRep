package yxs.snake.str.product.service;

import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-14.
 */
public interface ProductService {

    /**
     * 获取宝贝列表
     *
     * @param condition
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getProductList(Map<String, Object> condition) throws Exception;

    /**
     * 获取宝贝总数
     *
     * @param condition
     * @return
     */
    public Long getProductTotal(Map<String, Object> condition) throws Exception;

    /**
     * 获取颜色列表
     *
     * @return
     */
    public List<Map<String, Object>> getColorList();

    /**
     * 新增宝贝
     *
     * @param condition
     * @return
     */
    public int addProduct(Map<String, Object> condition) throws Exception;

    /**
     * 编辑宝贝
     *
     * @param condition
     * @return
     */
    public int editProduct(String productID, Map<String, Object> condition) throws Exception;

    /**
     * 获取宝贝基本信息
     *
     * @param productID
     * @return
     */
    public Map<String, Object> getProductByID(String productID);

    /**
     * 获取宝贝规格列表
     *
     * @param productID
     * @return
     */
    public List<Map<String, Object>> getProductSpecList(String productID);

    /**
     * 获取宝贝规格
     *
     * @param productID
     * @return
     */
    public List<Map<String, Object>> getProductSpec(String productID);

    /**
     * 获取操作类型
     *
     * @param flag
     * @return
     */
    public List<Map<String, Object>> getOperType(String flag);


    /**
     * 完成一次操作
     *
     * @param userName
     * @param list
     * @return
     */
    public int productOper(String userName, List<Map<String, Object>> list);

    /**
     * 获取所有宝贝库存销量列表
     *
     * @param condition
     * @return
     */
    public List<Map<String, Object>> getAllSellProductAmount(Map<String, Object> condition);

    /**
     * 获取所有宝贝库存销量记录数
     *
     * @param condition
     * @return
     */
    public int getAllSellProductAmountCount(Map<String, Object> condition);

    /**
     * 获取所有宝贝库存销量列表
     *
     * @param productID
     * @return
     */
    public List<Map<String, Object>> getProductsellAmount(String productID);

    /**
     * 获取所有宝贝库存销量记录数
     *
     * @param productID
     * @return
     */
    public int getProductsellAmountCount(String productID);

    /**
     * 获取所有宝贝销售记录列表
     *
     * @param condition
     * @return
     */
    public List<Map<String, Object>> getProductsellRec(Map<String, Object> condition);

    /**
     * 获取所有宝贝销售记录记录数
     *
     * @param condition
     * @return
     */
    public int getProductsellRecCount(Map<String, Object> condition);

    /**
     * 获取分类统计
     *
     * @param condition
     * @return
     */
    public List<Map<String, Object>> getProductsellTrend(Map<String, Object> condition);


}
