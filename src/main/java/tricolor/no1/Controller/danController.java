package tricolor.no1.Controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tricolor.no1.DTO.danDTO;
import tricolor.no1.Mapper.danMapper;
import tricolor.no1.Server.dan.danServer;
import tricolor.no1.model.Result;

import java.util.List;

@RestController
@RequestMapping("/dan")
public class danController {

    @Autowired
    public danServer danServers;

    @Autowired
    public danMapper danMapperr;


    //首先确定返回什么，1.我想弹幕有内容 2.弹幕能找到那个人的账号头像，与弹幕拼在一起，所以需要联表查询了。
    @GetMapping("/getAll")
    public Result getAllDan()
    {
        List<danDTO> danDTOS = danMapperr.getdanList();
        String jsonString = JSON.toJSONString(danDTOS);
        return Result.success(jsonString);
    }

}
