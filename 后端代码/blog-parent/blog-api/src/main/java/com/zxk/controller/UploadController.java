package com.zxk.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.zxk.util.QiniuUtils;
import com.zxk.vo.error_code.ErrorCode;
import com.zxk.vo.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author 闫柯含
 * @date 2022年 02月 06日 下午 9:13
 * @功能说明: =>文件上传
 */
@RestController
@RequestMapping("upload")
@AllArgsConstructor
public class UploadController {

    // 七牛云（图片）服务器
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file){
        String newFileName = UUID.randomUUID().toString()
                + "."
                + StrUtil.subAfter(file.getOriginalFilename(), ".", true);
        boolean uploadRes = qiniuUtils.upload(file, newFileName);
        if (uploadRes){
            return Result.success(qiniuUtils.url + newFileName);
        }
        return Result.fail(ErrorCode.UPLOAD_FAIL.getCode(), ErrorCode.UPLOAD_FAIL.getMsg());
    }

}
