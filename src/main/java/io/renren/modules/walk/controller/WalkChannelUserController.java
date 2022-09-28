package io.renren.modules.walk.controller;


import io.renren.common.utils.R;
import io.renren.modules.walk.entity.ExcelData;
import io.renren.modules.walk.entity.dto.UserWhiteDto;
import io.renren.modules.walk.service.WalkChannelUserService;
import io.renren.modules.walk.utils.ExcelUtils;
import io.renren.modules.walk.vo.ChannelUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  用户渠道信息前端控制器
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/walk-user")
public class WalkChannelUserController {
    @Autowired
    private WalkChannelUserService walkChannelUserService;

    /**
     * 获取用户基本信息
     * @param channelUserVo
     * @return
     */
    @RequestMapping("getUserDetails")
    @ResponseBody
    public R getUserDetails(@RequestBody @Validated ChannelUserVo channelUserVo){
        return walkChannelUserService.getUserDetails(channelUserVo);
    };

    /**
     * 修改用户基本信息
     * @param channelUserVo
     * @return
     */
    @RequestMapping("updateUserDetails")
    @ResponseBody
    public R updateUserDetails(@RequestBody @Validated ChannelUserVo channelUserVo){
        return walkChannelUserService.updateUserDetails(channelUserVo);
    }

    /**
     * 获取当日生日用户信息
     * @param channelUserVo
     * @return
     */
    @RequestMapping("getUserBirthdayList")
    @ResponseBody
    public R getUserBirthdayList(@RequestBody @Validated ChannelUserVo channelUserVo){
        return walkChannelUserService.getUserBirthdayList(channelUserVo);
    }

    /**
     * 导出excel
     * @param response
     */
    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletResponse response) {
        ExcelData<UserWhiteDto> data = new ExcelData<>();
        data.setData(walkChannelUserService.exportExcelData());
        data.setFileName("健步走");
        ExcelUtils.exportExcel(response,  data, UserWhiteDto.class);
    }

    /**
     * 获取用户基本信息
     * @param channelUserVo
     * @return
     */
    @RequestMapping("getChannelUserInfo")
    @ResponseBody
    public R getChannelUserInfo(@RequestBody @Validated ChannelUserVo channelUserVo){
        return walkChannelUserService.getChannelUserInfo(channelUserVo);
    };

    /**
     * 修改用户基本信息
     * @param channelUserVo
     * @return
     */
    @RequestMapping("updateChannelUser")
    @ResponseBody
    public R updateChannelUser(@RequestBody @Validated ChannelUserVo channelUserVo){
        return walkChannelUserService.updateChannelUser(channelUserVo);
    }

}

