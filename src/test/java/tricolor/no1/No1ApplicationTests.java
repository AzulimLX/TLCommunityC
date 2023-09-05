package tricolor.no1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import tricolor.no1.DTO.danDTO;
import tricolor.no1.Mapper.UserMapper;
import tricolor.no1.Mapper.danMapper;
import tricolor.no1.Server.User.UserServer;
import tricolor.no1.Server.amap.amapServerImpl;
import tricolor.no1.Server.dan.danServer;
import tricolor.no1.Server.place.placeServer;
import tricolor.no1.model.User;
import tricolor.no1.model.dan;
import tricolor.no1.model.place;
import tricolor.no1.util.TimeUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootTest
class No1ApplicationTests {

    @Resource
    private RedisTemplate<String ,Object> redisTemplate;

    @Autowired
    public UserServer userServer;

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public placeServer placeserver;

    @Test
    void lin()
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
            Map.add(map);
        }
        //加完之后就转化成JSON格式数据
        String s = JSON.toJSONString(Map);
        System.out.println(s);
    }

    @Test
    void contextLoads()
    {
          CloseableHttpClient client = HttpClients.createDefault();
          //准备参数
          String key = "eb65909acbeb799e25ffe23340f5be09";

          String url = "https://restapi.amap.com/v3/ip?key="+key;

          //构造请求对象
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpget);
            //获取响应结果
            HttpEntity entity = response.getEntity();
            //工具类，对HttpEntity操作
            String result = EntityUtils.toString(entity,StandardCharsets.UTF_8);
            System.out.println(result);
            //解析JSON字符串为JSONObject
            JSONObject jsonObject = JSON.parseObject(result);
            //获取其中的值
            String adcode = jsonObject.getString("adcode");
            System.out.println(adcode);

            //确保流关闭
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null)
            {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response !=null)
            {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Test
    void weather()
    {
        CloseableHttpClient client = HttpClients.createDefault();
        //准备参数
        String key = "eb65909acbeb799e25ffe23340f5be09";

        String city = "441200";

        String extensions = "all";

        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key="+key+"&city="+city+"&extensions="+extensions;

        //构造请求对象
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpget);
            //获取响应结果
            HttpEntity entity = response.getEntity();
            //工具类，对HttpEntity操作
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            //解析JSON字符串为JSONObject
            JSONObject jsonObject = JSON.parseObject(result);
            //获取数组第一个，也是唯一一个JSON对象
            JSONArray forecasts = jsonObject.getJSONArray("forecasts");
            JSONObject jsonObject2 = forecasts.getJSONObject(0);
            //获取casts真正的数组
            JSONArray casts = jsonObject2.getJSONArray("casts");
            for (int i = 0;i<casts.size();i++)
            {   //解析里面的数据
                JSONObject jsonObject1 = casts.getJSONObject(i);
                String s = JSON.toJSONString(jsonObject1);

            }

            //确保流关闭
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null)
            {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response !=null)
            {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Autowired
    public danServer danServers;




    @Test
    void timeText()
    {
        dan dan = new dan();
        dan.setId(9);
        dan.setAuthor("1234567");
        dan.setContent("歌声好像明媚的春光");
        dan.setCreateTime(TimeUtil.getSqlDate());
        danServers.save(dan);
    }

    @Autowired
    public danMapper danMapperr;
    @Test
    void testss(){
        CloseableHttpClient client = HttpClients.createDefault();
        //准备参数
        String key = "eb65909acbeb799e25ffe23340f5be09";

        String address = "广州市";
        //用来接收坐标
        String location = null;

        String url = "https://restapi.amap.com/v3/geocode/geo?key="+key+"&address="+address;
        //构造请求对象
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpget);
            //获取响应结果
            HttpEntity entity = response.getEntity();
            //工具类，对HttpEntity操作
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            //解析JSON字符串为JSONObject
            JSONObject jsonObject = JSON.parseObject(result);
            //获取数组第一个，也是唯一一个JSON对象
            JSONArray forecasts = jsonObject.getJSONArray("geocodes");
            JSONObject jsonObject2 = forecasts.getJSONObject(0);
            //获取location
            location = jsonObject2.getString("location");


            //确保流关闭
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null)
            {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response !=null)
            {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println(location);
    }

    @Autowired
    amapServerImpl amapServer;

    @Test
    void Tests()
    {
        //查找天气
        String s = amapServer.GetNowWeather("440000");
        System.out.println(s);
    }

}


