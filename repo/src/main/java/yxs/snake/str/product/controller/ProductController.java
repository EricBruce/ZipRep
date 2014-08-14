package yxs.snake.str.product.controller;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import yxs.snake.str.product.service.ProductService;
import yxs.snake.str.util.UUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 宝贝控制器
 *
 * @author Eric
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Resource
    ProductService productService;
    private Logger log = LoggerFactory.getLogger(ProductController.class);

    /**
     * 获取 宝贝列表
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/list.do")
    public ModelAndView listProduct(@RequestParam Map<String, Object> condition, HttpServletRequest request) {
        try {
            Map<String, Object> ro = new HashMap<String, Object>();
            String page = (String) condition.get("page");
            String rows = (String) condition.get("rows");
            //当前页
            int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
            //每页显示条数
            int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
            //每页的开始记录  第一页为1  第二页为number +1
            int start = (intPage - 1) * number;
            condition.put("offset", start);
            condition.put("limit", number);
            List<Map<String, Object>> list = productService.getProductList(condition);
            Long total = productService.getProductTotal(condition);
            request.setAttribute("list", list);
            request.setAttribute("conn", condition);
            /*ro.put("rows", list);
			ro.put("total", total);*/
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ModelAndView("main/pro_list");
    }

    /**
     * 跳转新建宝贝
     *
     * @return
     */
    @RequestMapping(value = "/pronew.do")
    public ModelAndView toPronew(HttpServletRequest request) {
        List<Map<String, Object>> list = productService.getColorList();
        request.setAttribute("list", list);
        return new ModelAndView("main/pro_new");
    }

    /**
     * 跳转编辑宝贝
     *
     * @return
     */
    @RequestMapping(value = "/proedit.do")
    public ModelAndView toProedit(@RequestParam("productID") String productID, HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
            //颜色列表
            List<Map<String, Object>> list = productService.getColorList();
            //获取宝贝基本信息
            Map<String, Object> product = productService.getProductByID(productID);
            //获取宝贝规格信息
            List<Map<String, Object>> proSpecs = productService.getProductSpecList(productID);
            jsonGenerator.writeObject(proSpecs);
            String specs = objectMapper.writeValueAsString(proSpecs);
            request.setAttribute("list", list);
            request.setAttribute("product", product);
            request.setAttribute("proSpecs", specs);
            return new ModelAndView("main/pro_edit");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ModelAndView("error/error_500");
        }
    }

    /**
     * 新增宝贝
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/add.do")
    public ModelAndView addProduct(@RequestParam Map<String, Object> condition, HttpSession session) {
        String view = "";
        try {
            condition.put("create_by", session.getAttribute("userName"));
            int ret = productService.addProduct(condition);
            if (ret == 1) {
                view = "/repo/product/list.do";
            } else {
                view = "/repo/main/pro_new_fail";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            view = "/repo/error/error_500";
        }
        return new ModelAndView(new RedirectView(view));
    }

    /**
     * 新增宝贝
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/edit.do")
    public ModelAndView editProduct(@RequestParam Map<String, Object> condition) {
        String view = "";
        try {
            String productID = (String) condition.get("product_id");
            int ret = productService.editProduct(productID, condition);
            if (ret == 1) {
                view = "/repo/product/list.do";
            } else {
                view = "/repo/main/pro_new_fail";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            view = "/repo/error/error_500";
        }
        return new ModelAndView(new RedirectView(view));
    }

    /**
     * 宝贝图片上传
     *
     * @return
     */
    @RequestMapping(value = "/uploadImg.do", method = RequestMethod.POST)
    @ResponseBody
    public String uploadProductImg(@RequestParam("proImg") CommonsMultipartFile file, HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
            Map<String, Object> m = new HashMap<String, Object>();
            //判断文件是否为空
            if (!file.isEmpty()) {
                //存储目录
                String path = request.getSession().getServletContext().getRealPath("/upload");
                //文件名
                String fileName = file.getOriginalFilename();
                //文件名后缀
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = UUIDGenerator.getUUID() + suffix;
                //
                String filePath = path + File.separator + newFileName;
                //要保存的新文件
                File saveFile = new File(filePath);
                if (!saveFile.isDirectory()) {
                    saveFile.mkdirs();
                }
                try {
                    file.transferTo(saveFile);
                    m.put("code", 1);
                    m.put("filename", "upload/" + newFileName);
                    jsonGenerator.writeObject(m);
                    String json = objectMapper.writeValueAsString(m);
                    return json;
                } catch (Exception e) {
                    log.error(e.getMessage());
                    m.put("code", -1);
                    jsonGenerator.writeObject(m);
                    String json = objectMapper.writeValueAsString(m);
                    return json;
                }
            } else {
                m.put("code", -1);
                jsonGenerator.writeObject(m);
                String json = objectMapper.writeValueAsString(m);
                return json;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    //获取宝贝规格
    @RequestMapping(value = "/specList.do")
    @ResponseBody
    public List<Map<String, Object>> getProductSpec(@RequestParam Map<String, Object> condition) {
        List<Map<String, Object>> list = null;
        if (condition.containsKey("productID")) {
            String productID = (String) condition.get("productID");
            list = productService.getProductSpec(productID);
        }
        return list;
    }

    /**
     * 完成一次入库操作
     *
     * @param list
     * @return
     */
    @RequestMapping(value = "/proInOper")
    @ResponseBody
    public Map<String, Object> productInOper(@RequestBody String json, HttpSession session) {
        Map<String, Object> rsp = new HashMap<String, Object>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = URLDecoder.decode(json, "utf-8");
            List<Map<String, Object>> list = objectMapper.readValue(json, List.class);
            String userName = (String) session.getAttribute("userName");
            int ret = productService.productOper(userName, list);
            rsp.put("status", ret);
        } catch (Exception e) {
            log.error(e.getMessage());
            rsp.put("status", -1);
        }
        return rsp;
    }

    /**
     * 完成一次出库操作
     *
     * @param list
     * @return
     */
    @RequestMapping(value = "/proOutOper")
    @ResponseBody
    public Map<String, Object> productOutOper(@RequestBody String json, HttpSession session) {
        Map<String, Object> rsp = new HashMap<String, Object>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = URLDecoder.decode(json, "utf-8");
            List<Map<String, Object>> list = objectMapper.readValue(json, List.class);
            String userName = (String) session.getAttribute("userName");
            int ret = productService.productOper(userName, list);
            rsp.put("status", ret);
        } catch (Exception e) {
            log.error(e.getMessage());
            rsp.put("status", -1);
        }
        return rsp;
    }

    ///从字典表中获取操作类型

    /**
     * @param flag +/-
     * @return
     */
    @RequestMapping(value = "/operType.do")
    @ResponseBody
    public List<Map<String, Object>> getOperType(@RequestParam("flag") String flag) {
        List<Map<String, Object>> list = productService.getOperType(flag);
        return list;
    }

    /**
     * 所有库存及销量情况（趋势图）
     *
     * @param condition
     * @param request
     * @return
     */
    @RequestMapping(value = "/trend.do")
    public
    @ResponseBody
    Map<String, Object> getAlllSelllAmountList(@RequestParam Map<String, Object> condition, HttpServletRequest request) {
        String page = (String) condition.get("page");
        String rows = (String) condition.get("rows");
        //当前页
        int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
        //每页显示条数
        int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
        //每页的开始记录  第一页为1  第二页为number +1
        int start = (intPage - 1) * number;
        condition.put("offset", start);
        condition.put("limit", number);
        List<Map<String, Object>> list = productService.getAllSellProductAmount(condition);
        int count = productService.getAllSellProductAmountCount(condition);
        Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map
        jsonMap.put("total", count);//total键 存放总记录数，必须的  
        jsonMap.put("rows", list);//rows键 存放每页记录 list  
//        result = JSONObject.fromObject(jsonMap);//格式化result   一定要是JSONObject 
//		request.setAttribute("json", jsonMap);
        return jsonMap;
    }

    /**
     * 获取具体某宝贝的趋势图(不同颜色)
     *
     * @param productID
     * @return
     */
    @RequestMapping(value = "/{productID}/trend.do")
    @ResponseBody
    public Map<String, Object> getProductsellAmount(@PathVariable(value = "productID") String productID, HttpServletRequest request) {
        List<Map<String, Object>> list = productService.getProductsellAmount(productID);
        int count = productService.getProductsellAmountCount(productID);
        Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map
        jsonMap.put("total", count);//total键 存放总记录数，必须的  
        jsonMap.put("rows", list);//rows键 存放每页记录 list  
//        result = JSONObject.fromObject(jsonMap);//格式化result   一定要是JSONObject 
//		request.setAttribute("json", jsonMap);
        return jsonMap;
//		return new ModelAndView("/repo/main/pro_trend");
    }

    /**
     * 获取具体宝贝的销售记录
     *
     * @param productID
     * @return
     */
    @RequestMapping(value = "/selrec.do")
    public Map<String, Object> getProductsellRec(@RequestParam Map<String, Object> condition, HttpServletRequest request) {
        String page = (String) condition.get("page");
        String rows = (String) condition.get("rows");
        //当前页
        int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
        //每页显示条数
        int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
        //每页的开始记录  第一页为1  第二页为number +1
        int start = (intPage - 1) * number;
        condition.put("offset", start);
        condition.put("limit", number);
        List<Map<String, Object>> list = productService.getProductsellRec(condition);
        int count = productService.getProductsellRecCount(condition);
        Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map
        jsonMap.put("total", count);//total键 存放总记录数，必须的
        jsonMap.put("rows", list);//rows键 存放每页记录 list
//        result = JSONObject.fromObject(jsonMap);//格式化result   一定要是JSONObject 
//		request.setAttribute("json", jsonMap);
//		return new ModelAndView("/repo/main/pro_sell_trend");
        int a = 10;
        return jsonMap;
    }

    /**
     * 获取具体宝贝的分类统计数据
     *
     * @param productID
     * @return
     */
    @RequestMapping(value = "/seltrend.do")
    public
    @ResponseBody
    List<Map<String, Object>> getProductsellTrend(@RequestParam Map<String, Object> condition, HttpServletRequest request) {
        List<Map<String, Object>> list = productService.getProductsellTrend(condition);
        return list;
    }
}
