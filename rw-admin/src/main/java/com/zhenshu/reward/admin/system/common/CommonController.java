package com.zhenshu.reward.admin.system.common;

import com.zhenshu.reward.common.library.third.cdn.service.CdnService;
import com.zhenshu.reward.common.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/14 10:29
 * @desc 通用
 */
@RestController
@RequestMapping("/common")
@Api(tags = "通用", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonController {
    @Resource
    private CdnService cdnService;

    @PostMapping("/cdn/upload")
    @ApiOperation(value = "文件上传", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "type", required = false) String type) throws IOException {
        String url = cdnService.putObject(file);
        Map<String, String> ajax = new LinkedHashMap<>();
        ajax.put("url", url);
        ajax.put("type", type);
        return Result.buildSuccess(ajax);
    }

    @PostMapping("/aliyun/oss/signature")
    @ApiOperation(httpMethod = "POST", value = "阿里云OSS签名计算")
    public Result<Map<String, Object>> store(@RequestParam(value = "extension", required = false)
                                             @ApiParam("文件拓展名") @Size(max = 10) String extension) {
        Map<String, Object> map = cdnService.calculatePostSignature(extension);
        return Result.buildSuccess(map);
    }
}
