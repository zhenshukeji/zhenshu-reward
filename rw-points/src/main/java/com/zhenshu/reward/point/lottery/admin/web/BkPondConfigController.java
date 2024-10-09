package com.zhenshu.reward.point.lottery.admin.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.lottery.service.admin.domain.bo.BkPondConfigBO;
import com.zhenshu.reward.point.lottery.service.admin.domain.vo.BkPondConfigAddVO;
import com.zhenshu.reward.point.lottery.service.admin.domain.vo.BkPondConfigEditVO;
import com.zhenshu.reward.point.lottery.service.admin.domain.vo.BkPondConfigQueryVO;
import com.zhenshu.reward.point.lottery.service.admin.facade.BkPondConfigFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuzq
 * @Date 2022/10/12 9:55
 * @Version 1.0
 */

@RestController
@PreAuthorize("@ss.hasPermi('lottery:pond')")
@RequestMapping("/bk/lottery/pond")
@Api(tags = "后台-奖池配置", value = "WEB - PondConfigController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkPondConfigController {
    @Resource
    private BkPondConfigFacade pondConfigFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkPondConfigBO>> list(@RequestBody BkPondConfigQueryVO queryVO) {
        return new Result<IPage<BkPondConfigBO>>().success(pondConfigFacade.listPage(queryVO));
    }

    @PostMapping("/listAll")
    @ApiOperation(value = "获取所有奖池配置")
    public Result<List<BkPondConfigBO>> listAll(@RequestBody BkPondConfigQueryVO queryVO) {
        return new Result<List<BkPondConfigBO>>().success(pondConfigFacade.listAll(queryVO));
    }

    @Log(title = "新增奖池配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation(value = "新增奖池配置")
    public Result<Object> add(@RequestBody @Validated BkPondConfigAddVO addVO) {
        addVO.verifyTime();
        pondConfigFacade.add(addVO);
        return new Result<>().success();
    }

    @Log(title = "修改奖池配置", businessType = BusinessType.UPDATE)
    @PutMapping("/updateById")
    @ApiOperation(value = "根据Id修改奖池配置")
    public Result<Object> updateById(@RequestBody @Validated BkPondConfigEditVO editVO) {
        editVO.verifyTime();
        pondConfigFacade.updateById(editVO);
        return new Result<>().success();
    }

    @Log(title = "删除奖池配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据Id删除奖池配置")
    public Result<Object> deleteById(@PathVariable("id") Long id) {
        pondConfigFacade.deleteById(id);
        return new Result<>().success();
    }

}
