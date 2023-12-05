package tricolor.no1.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tricolor.no1.DTO.WorldMsgDTO;
import tricolor.no1.Mapper.WorldMsgMapper;
import tricolor.no1.Server.WorldMsg.WorldMsgServerImpl;
import tricolor.no1.model.Result;
import tricolor.no1.model.WorldMsg;

import java.util.List;

@RestController
@RequestMapping("/WorldMsg")
public class WorldMsgController {

    @Autowired
    WorldMsgServerImpl worldMsgServer;

    @Autowired
    WorldMsgMapper worldMsgMapper;

    //查询最新的十条信息
    @GetMapping("/SelectNewMsg")
    public Result SelectNewMsg()
    {
        List<WorldMsgDTO> MsgList = worldMsgMapper.selectNewMs();
        return Result.success(MsgList);
    }
}
