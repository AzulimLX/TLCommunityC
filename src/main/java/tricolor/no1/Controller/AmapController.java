package tricolor.no1.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tricolor.no1.Server.amap.amapServerImpl;
import tricolor.no1.Server.article.articleServer;
import tricolor.no1.Server.place.placeServer;
import tricolor.no1.model.Result;
import tricolor.no1.model.place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/map")
public class AmapController {


       @Autowired
       public placeServer placeserver;

       @Autowired
       public amapServerImpl amapServer;

       //返回固定的几个地址及图片(指圣地巡游)
       @GetMapping("/staticPlace")
       public Result GetStaticPlace()
       {
           //首先获取所有信息
           List<place> list = placeserver.list();
           //我们确定需要返回JSON格式的数据，所以我们需要思考，怎么构造JSON才合理

           //[xxx:xxx][xxx:xxxx][][]这种吧，所以需要返回一个列表，列表内是JSON
           //而我们知道，JOSN格式需要map进行转化，而我们需要一个列表装这个map，每个map为一个对象
           //进行构造
           List<Map<String,Object>> Map = new ArrayList<>();
           for (place p : list)
           {
               HashMap<String, Object> map = new HashMap<>();
               map.put("name",p.name);
               map.put("content",p.content);
               map.put("mapname",p.mapname);
               map.put("coordinate",p.coordinate);
               map.put("photo",p.photo);
               map.put("adcode",p.adcode);
               System.out.println("放adcode"+p.adcode);
               Map.add(map);
           }
           //加完之后就转化成JSON格式数据
           String s = JSON.toJSONString(Map);
           return Result.success(s);
       }

       //返回未来的天气
       @GetMapping("/FutureWeather")
       public Result GetFutureWeather(@RequestParam(name = "adcode",required = false) String adcode)
       {
           String adCodeFromIp;
           if (adcode == null) {
               //如果前端没有传输adcode，则直接通过IP请求获取adcode
               adCodeFromIp =(String)amapServer.getAdCodeFromIp().get("adcode");
           }
           else adCodeFromIp = adcode;
           //根据IP定位的码来发送天气请求
           String[] s = amapServer.IPFutureWeather(adCodeFromIp);
           return Result.success(s);
       }

       //返回现在的天气
       @GetMapping("/NowWeather")
       public Result GetNowWeather(@RequestParam(name = "adcode",required = false) String adcode)
       {
           String adCodeFromIp;
           //首先获取IP定位
           if (adcode == null || adcode.equals("null")) {
               Map<String, Object> adCodeFromIp1 = amapServer.getAdCodeFromIp();
               adCodeFromIp = (String)adCodeFromIp1.get("adcode");
               //获得adcode后去获取市级地理位置
               String location = amapServer.getLocation((String) adCodeFromIp1.get("city"));
               //查找天气
               String s = amapServer.GetNowWeather(adCodeFromIp);
               //先解析成JSON对象
               JSONObject jsonObject = JSON.parseObject(s);
               //添加坐标
               jsonObject.put("coordinate",location);
               //重新转为String
               String NewString = JSON.toJSONString(jsonObject);
                return Result.success(NewString);
           }else adCodeFromIp = adcode;

            //根据IP定位的码来发送天气请求
           String s = amapServer.GetNowWeather(adCodeFromIp);
           return Result.success(s);
       }
}
