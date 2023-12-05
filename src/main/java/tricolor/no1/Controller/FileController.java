package tricolor.no1.Controller;

import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tricolor.no1.model.Result;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip}")
    String ip;

    @Value("${server.port}")
    String Port;

    //文件根目录
    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";


    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {   //首先把传进来的文件信息获取一下
        String originalFilename = file.getOriginalFilename();//文件的原始名称
        // aaa.png
        String mainName = FileUtil.mainName(originalFilename); //aaa
        String extName = FileUtil.extName(originalFilename);//png
        if (!FileUtil.exist(ROOT_PATH))
        {
           FileUtil.mkdir(ROOT_PATH);//如果当前文件的父级目录不存在，则创建
        }

        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) //如果当前上传的文件已经存在了，那么这个时候我就要重命名一个文件
        {
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);//存储文件到本地的磁盘里面去
        String url = "http://"+ ip +":" + Port + "/file/download/" + originalFilename;
        System.out.println("已经存入文件"+originalFilename);
        return Result.success(url); //返回文件的链接，这个链接就是文件的下载地址，这下载地址就是我后台提供出来的
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException{
        //进行文件查找
        String filePath = ROOT_PATH + File.separator + fileName;
        if (!FileUtil.exist(filePath))//如果文件不存在
        {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        System.out.println("写出文件" + fileName);
    }

}
