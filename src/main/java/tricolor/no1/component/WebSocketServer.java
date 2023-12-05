package tricolor.no1.component;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import tricolor.no1.DTO.SocketMsg;
import tricolor.no1.Server.User.UserServiceImpl;
import tricolor.no1.Server.WorldMsg.WorldMsgServerImpl;
import tricolor.no1.model.User;
import tricolor.no1.model.WorldMsg;
import tricolor.no1.util.TimeUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * @Auther: zj
 * @Date: 2018/8/16 17:55
 * @Description: websocket的具体实现类
 * 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，
 * 但在springboot中连容器都是spring管理的。
虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
所以可以用一个静态set保存起来。
 */
@ServerEndpoint(value = "/websocket/{IDS}")
@Component
public class WebSocketServer {
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String IDS;

    //用来记录sessionId和该session进行绑定
    private static Map<String,Session> map = new HashMap<>();

    //根据ID寻找User信息,因为Spring管理Bean都为单例模式，而WebSocket管理为多对象，所以直接AutoWired会冲突
    //需要自己写一个注入方法
    private static   UserServiceImpl userService;

    //创建一个世界语录的Bean
    private static WorldMsgServerImpl worldMsgServer;

    @Autowired
    public WebSocketServer(UserServiceImpl userService1, WorldMsgServerImpl worldMsgServer1)
    {
        WebSocketServer.userService = userService1;
        WebSocketServer.worldMsgServer = worldMsgServer1;
    }

    public WebSocketServer(){}

    private  User MyUser;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("IDS") String IDS ) {
        this.session = session;
        this.IDS=IDS;
        map.put(IDS, session);
        webSocketSet.add(this);     //加入set中
        System.out.println("有新连接加入:"+ IDS +",当前在线人数为" + webSocketSet.size());
        this.session.getAsyncRemote().sendText("恭喜"+IDS+"成功连接上WebSocket(其频道号："+IDS+")-->当前在线人数为："+webSocketSet.size());
        //先找找相关用户的信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user",IDS);
        MyUser = userService.getOne(userQueryWrapper);
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session,@PathParam("IDS") String IDS) {
        System.out.println("来自客户端的消息:"+IDS + message);

        //从客户端传来的数据是JSON数据，所以这里使用jackson转换为socketMsg对象
        //通过type判断是单聊还是群聊
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsg socketMsg;

        try{
            socketMsg = objectMapper.readValue(message,SocketMsg.class);
            if (socketMsg.getType() ==1)
            {
                //单聊，则需要找到发送者和接收者
                socketMsg.setFromUser(IDS);//发送者
                Session fromSession = map.get(socketMsg.getFromUser());
                Session toSession = map.get(socketMsg.getToUser());

                //发送给接收者
                if (toSession != null)
                {
                    fromSession.getAsyncRemote().sendText(IDS+":"+socketMsg.getMsg());
                    toSession.getAsyncRemote().sendText(IDS+":"+socketMsg.getMsg());

                }
                else
                {
                    //先对信息进行存储
                    //发送给发送者
                  fromSession.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的频道号不对");

                }

            }
            else
            {
                //进行群发
                broadcast(IDS + ":" + socketMsg.getMsg() + ":" + MyUser.getUsername() + ":" + MyUser.getPhoto());
                //进行存储
                WorldMsg worldMsg = new WorldMsg();
                worldMsg.setMsg(socketMsg.getMsg());
                worldMsg.setFromUser(socketMsg.getFromUser());
                worldMsg.setCreateTime(TimeUtil.getNowTime());
                worldMsgServer.save(worldMsg);
            }

        }catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
    /**
     * 群发自定义消息
     * */
    public void broadcast(String message) {
        for (WebSocketServer item : webSocketSet) {
            Session itemSession = item.session;
            // 向一个客户端发送消息
            RemoteEndpoint.Async asyncRemote = itemSession.getAsyncRemote();

            // 使用异步方法发送消息，并等待发送完成
            Future<Void> sendFuture = asyncRemote.sendText(message);

            // 在发送完成前等待
            try {
                sendFuture.get(); // 等待发送完成
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}