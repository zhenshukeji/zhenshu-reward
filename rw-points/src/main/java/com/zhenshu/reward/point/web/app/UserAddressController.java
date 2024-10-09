package com.zhenshu.reward.point.web.app;

import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.constant.enums.KeyDataSource;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.h5.UserAddressBO;
import com.zhenshu.reward.point.service.domain.vo.h5.UserAddressAddVO;
import com.zhenshu.reward.point.service.domain.vo.h5.UserAddressUpdateVO;
import com.zhenshu.reward.point.service.facade.h5.UserAddressFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/11 15:56
 * @desc 收货地址相关接口
 */
@RestController
@RequestMapping("/h5/address")
@Api(tags = "收货地址", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAddressController {
    @Resource
    private UserAddressFacade addressFacade;

    @LoginToken
    @PostMapping("/list")
    @ApiOperation(value = "查询收货地址")
    public Result<List<UserAddressBO>> getAddressList() {
        return Result.buildSuccess(addressFacade.getAddressList());
    }

    @LoginToken
    @LockFunction(methodName = "address", keyName = "userId", form = KeyDataSource.USER)
    @PostMapping
    @ApiOperation(value = "添加收货地址")
    public Result<Void> addAddress(@RequestBody @Validated UserAddressAddVO addVO) {
        addressFacade.addAddress(addVO);
        return Result.buildSuccess();
    }

    @LoginToken
    @LockFunction(methodName = "address", keyName = "userId", form = KeyDataSource.USER)
    @PutMapping
    @ApiOperation(value = "修改收货地址")
    public Result<Void> updateAddress(@RequestBody @Validated UserAddressUpdateVO updateVO) {
        addressFacade.updateAddress(updateVO);
        return Result.buildSuccess();
    }

    @LoginToken
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除收货地址")
    public Result<Void> deleteAddress(@ApiParam("收货地址id") @PathVariable("id") Long id) {
        addressFacade.deleteAddress(id);
        return Result.buildSuccess();
    }

    @LoginToken
    @LockFunction(methodName = "address", keyName = "userId", form = KeyDataSource.USER)
    @PostMapping("/default/{id}")
    @ApiOperation(value = "设为默认")
    public Result<Void> defaultAddress(@ApiParam("收货地址id") @PathVariable("id") Long id) {
        addressFacade.defaultAddress(id);
        return Result.buildSuccess();
    }
}
