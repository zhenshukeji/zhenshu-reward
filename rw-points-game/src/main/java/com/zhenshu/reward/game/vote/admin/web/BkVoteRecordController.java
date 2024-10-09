package com.zhenshu.reward.game.vote.admin.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.vote.admin.domain.bo.BkVoteRecordBO;
import com.zhenshu.reward.game.vote.admin.domain.vo.BkVoteRecordQueryVO;
import com.zhenshu.reward.game.vote.admin.facade.BkVoteRecordFacade;
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
 * @author hong
 * @version 1.0
 * @date 2023/10/16 13:48
 * @desc 投票记录
 */
@RestController
@PreAuthorize("@ss.hasPermi('vote:activity')")
@RequestMapping("/bk/voteRecord")
@Api(tags = "投票记录", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkVoteRecordController {
    @Resource
    private BkVoteRecordFacade bkVoteRecordFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkVoteRecordBO>> list(@RequestBody @Validated BkVoteRecordQueryVO queryVO) {
        IPage<BkVoteRecordBO> page = bkVoteRecordFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @Log(title = "导出投票记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(@RequestBody @Validated BkVoteRecordQueryVO queryVO) {
        List<BkVoteRecordBO> list = bkVoteRecordFacade.listAll(queryVO);
        ExcelUtil.exportIOFlow(list, "投票记录", "投票记录");
    }
}
