package tricolor.no1.Server.amap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class amapServerImpl {

    private final String Mykey = "eb65909acbeb799e25ffe23340f5be09";


   //根据IP地址获取adcode

    /**
     *   此为返回城市IP的接口
     * @return 城市编码adcode
     */
    public Map<String,Object> getAdCodeFromIp(){
        //新建Map
        Map<String,Object> local = new HashMap<>();
        CloseableHttpClient client = HttpClients.createDefault();
        //准备参数
        String key = Mykey;

        String url = "https://restapi.amap.com/v3/ip?key="+key;

        String adcode = null;
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
            //获取其中的值
            adcode = jsonObject.getString("adcode");
            local.put("adcode",adcode);
            //获取当前的省
            local.put("province",jsonObject.getString("province"));
            //获取当前的市
            local.put("city",jsonObject.getString("city"));
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
        return local;
    }

    //获取到的adCode来请求一组未来天气

    /**
     *
     * @param adcode：城市码，需要先调用IP定位接口,
     * @return 返回JSON格式的String字符串
     */
    //TODO:还需要完善
    public String[] IPFutureWeather(String adcode)
    {
        CloseableHttpClient client = HttpClients.createDefault();
        //准备参数
        String key = Mykey;

        String city = adcode;

        String extensions = "all";

        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key="+key+"&city="+city+"&extensions="+extensions;

        String[] s  = new String[4];

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
                s[i] = JSON.toJSONString(jsonObject1);

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
        return s;
    }

    /**
     * 这是返回现在天气的接口
     * @param adcode ：城市编码，需要调用IP定位接口获取
     * @return 返回JSON格式的String字符串
     */
    public String GetNowWeather(String adcode)
    {

        CloseableHttpClient client = HttpClients.createDefault();
        //准备参数
        String key = Mykey;

        String city = adcode;

        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key="+key+"&city="+city;

        String s = null;

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
            JSONArray forecasts = jsonObject.getJSONArray("lives");
            JSONObject jsonObject2 = forecasts.getJSONObject(0);
            //转为String
            s = jsonObject2.toJSONString();

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
        return s;
    }

    /**
     *  根据地名获取地区编码
     * @param Myaddress 地名
     * @return 地区编码，String类型的 xxxx,xxxx .前端需要先转成数组再放进地图
     */
    public String getLocation (String Myaddress)
    {
        CloseableHttpClient client = HttpClients.createDefault();
        //准备参数
        String key = Mykey;

        String address = Myaddress;
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
       return location;
    }


}
