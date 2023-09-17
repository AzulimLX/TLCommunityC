package tricolor.no1.Controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tricolor.no1.DTO.danDTO;
import tricolor.no1.Mapper.danMapper;
import tricolor.no1.Server.dan.danServer;
import tricolor.no1.model.Result;
import tricolor.no1.model.dan;
import tricolor.no1.util.TimeUtil;

import java.sql.Date;
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

    //保存弹幕信息
    @PostMapping("/saveDan")
    public Result saveDan(@RequestBody dan dandan)
    {
        //给弹幕设置时间
        Date sqlDate = TimeUtil.getSqlDate();
        dandan.setCreateTime(sqlDate);
        //直接保存
        QueryWrapper<dan> danQueryWrapper = new QueryWrapper<>();
        danQueryWrapper.eq("author",dandan.author);
        boolean save = danServers.save(dandan);
        return Result.success(save);
    }

}
