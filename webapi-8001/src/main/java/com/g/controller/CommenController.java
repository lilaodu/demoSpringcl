package com.g.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.DataReturn;
import com.utils.MD5Util;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class CommenController {
    private static final Logger logger = LoggerFactory.getLogger(CommenController.class);
    @Value("${webapi.client_secret_pd}")
   private   String client_secret_pd;


    @RequestMapping("placeOrder")
    @ResponseBody
    public  String placeOrder ()throws  UnsupportedEncodingException{
        Map<String,Object> map = new HashMap<>();
        String url = "http://pd.lion-mall.com/?r=/index/api-order";
        String type = "alipay";//alipay 支付方式
        Integer total = 1200;//支付额度
        String api_order_sn = "201911181234567";
        String notify_url = "http://bobo.mall.com";
        String client_id = "1234567";
        String timestamp =new Date().getTime()/1000+"";//时间戳
        map.put("type",type);
        map.put("total",total);
        map.put("api_order_sn",api_order_sn);
        map.put("notify_url",notify_url);
        map.put("client_id",client_id);
        map.put("timestamp",timestamp);
        String sign = MD5Util.generateSign(map,client_secret_pd);
        map.put("sign",sign);

        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        logger.info("下单入参--------"+jsonObject.toString());
        HttpResponse response = HttpRequest.post(url).body(jsonObject.toString()).charset("utf-8")
                //.contentType("application/x-www-form-urlencoded").connectionTimeout(2000)
                .contentType("application/json").connectionTimeout(2000)
                .timeout(5000).send();
        String result = new String(response.bodyBytes(), "utf-8");
        logger.info("下单出参--------"+result);
        return result;

    }


    @PostMapping(value = "payState")
    public DataReturn payState(HttpServletRequest request) {
        logger.info("支付回调------------------------");
        DataReturn dr = new DataReturn();

        // 解密&验证必传参数完整性
        String[] params = { "callbacks","type","total","api_order_sn","order_sn","sign" };
        DataReturn dataDR = getBizData(request, params);
        String data = "";
        if ("0".equals(dataDR.getCode().toString())) {
            data = dataDR.getResponse().toString();
        } else {
            logger.info("【payState】验证数据失败：" + JSONObject.toJSONString(dataDR));
            dr.setCode(-2);
            dr.setMsg(dataDR.getMsg());
            return dr;
        }
        logger.info("【payState】获取数据成功：" + data);

        return dr;
    }


    /**
     * 对传递的数据进行校验
     *
     * @param request
     * @param params
     * @return
     */
    public  DataReturn getBizData(HttpServletRequest request, String[] params) {
        DataReturn dr = new DataReturn();
        try {
            String body = HttpHelper.getBodyString(request);
            if (StringUtils.isBlank(body)) {
                logger.info("获取data数据，body不存在数据");
                dr.setCode(1);
                dr.setMsg("参数异常");
                return dr;
            }
            logger.info("获取data数据， url={}", request.getRequestURI());
            Map<String, Object> pMap = JSONObject.parseObject(body, Map.class);
           // String dataStr = pMap.get("request").toString();
            //md5验签
           String signMd5 = MD5Util.generateSign(pMap,client_secret_pd);
           if (!signMd5.equals(pMap.get("sign"))){
               logger.info("sign不相同，signMd5={},signorgin={}", signMd5,pMap.get("sign"));
               dr.setCode(1);
               dr.setMsg("sign异常");
               return dr;
           }

            if (params != null) {
                JSONObject jb = new JSONObject();
                jb.putAll(pMap);
                if (jb == null) {
                    logger.info("获取data数据，data数据转化失败");
                    dr.setCode(1);
                    dr.setMsg("参数异常");
                    return dr;
                } else {
                    for (int i = 0; i < params.length; i++) {
                        if (StringUtils.isBlank("" + jb.get(params[i])) || "null".equals("" + jb.get(params[i]))) {
                            logger.info("获取data数据，验证必传值无数据，[" + params[i] + "]");
                            dr.setCode(1);
                            dr.setMsg("参数异常");
                            return dr;
                        }
                    }
                    dr.setCode(0);
                    dr.setMsg("数据效验成功");
                    dr.setResponse(jb);
                    return dr;
                }
            }
        } catch (Exception e) {
            logger.info("获取data异常！！！ ");
            e.printStackTrace();
            dr.setCode(1);
            dr.setMsg("获取数据异常");
            return dr;
        }
        logger.info("获取data数据，验证必传值无数据");
        dr.setCode(1);
        dr.setMsg("参数异常");
        return dr;
    }

}
