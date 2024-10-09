package com.zhenshu.reward.point.web.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.bk.BkFeedbackBO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkFeedbackHandlerVO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkFeedbackQueryVO;
import com.zhenshu.reward.point.service.facade.bk.BkFeedbackFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzq
 * @version 1.0
 * @date 2022-12-07
 * @desc
 */
@RestController
@RequestMapping("/bk/feedback")
@PreAuthorize("@ss.hasPermi('integral:feedback')")
@Api(tags = "意见反馈", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkFeedbackController {
    @Resource
    private BkFeedbackFacade bkFeedbackFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表分页查询意见反馈表")
    public Result<IPage<BkFeedbackBO>> listPage(@Validated @RequestBody BkFeedbackQueryVO queryVO) {
        IPage<BkFeedbackBO> page = bkFeedbackFacade.listPage(queryVO);
        return new Result<IPage<BkFeedbackBO>>().success(page);
    }

    @Log(title = "意见反馈标记处理", businessType = BusinessType.UPDATE)
    @PostMapping("/handled")
    @ApiOperation(value = "标记处理")
    public Result<Object> handled(@Validated @RequestBody BkFeedbackHandlerVO handlerVO) {
        bkFeedbackFacade.handled(handlerVO);
        return new Result<>().success();
    }

    @Log(title = "导出意见反馈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(@RequestBody @Validated BkFeedbackQueryVO queryVO) {
        List<BkFeedbackBO> list = bkFeedbackFacade.export(queryVO);
        ExcelUtil.exportIOFlow(list, "意见反馈", "feedback");
    }

}
