package io.renren.modules.walk.controller;


import javax.servlet.http.HttpServletRequest;

import io.renren.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;

import io.renren.common.ref.RetData;
import io.renren.modules.walk.entity.HnjbzWalkDayStatistic;
import io.renren.modules.walk.service.WalkStatisticService;


/**
 * 步数统计
 */
@RestController
@RequestMapping("/app/walkStatistic")
public class WalkStatisticController {

    private Logger logger = LoggerFactory.getLogger(WalkStatisticController.class);

    @Autowired
    private WalkStatisticService walkStatisticService;

    //个人每天排行
    @RequestMapping("/selectPersionDayWalkList")
    public RetData<Object> selectPersionDayWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
        logger.info("selectPersionDayWalkList " + hnjbzWalkDayStatistic.toString());
        if (StringUtils.isEmpty(hnjbzWalkDayStatistic.getOpenid())) {
            return new RetData<>(-1, "用户openid不能为空");
        }
        try {
            return walkStatisticService.selectPersionDayWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }


    //部门每天排行
    @RequestMapping("/selectOrganDayWalkList")
    public RetData<Object> selectOrganDayWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
        logger.info("selectOrganDayWalkList " + hnjbzWalkDayStatistic.toString());
        if (StringUtils.isEmpty(hnjbzWalkDayStatistic.getOpenid())) {
            return new RetData<>(-1, "用户openid不能为空");
        }
        try {
            return walkStatisticService.selectOrganDayWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }

    //当前用户部门每天排行
    @RequestMapping("/selectUserOrganDayWalkList")
    public R selectUserOrganDayWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
        logger.info("selectUserOrganDayWalkList " + hnjbzWalkDayStatistic.toString());
        if (StringUtils.isEmpty(hnjbzWalkDayStatistic.getOpenid())) {
            return R.error("用户openid不能为空");
        }
        try {
            return walkStatisticService.selectUserOrganDayWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("查询失败");
        }
    }


    //单位每天排行
    @RequestMapping("/selectCompanyDayWalkList")
    public RetData<Object> selectCompanyDayWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
        logger.info("selectCompanyDayWalkList " + hnjbzWalkDayStatistic.toString());
        if (StringUtils.isEmpty(hnjbzWalkDayStatistic.getOpenid())) {
            return new RetData<>(-1, "用户openid不能为空");
        }
        try {
            return walkStatisticService.selectCompanyDayWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }

	//当前用户单位每天排行
	@RequestMapping("/selectUserCompanyDayWalkList")
	public R selectUserCompanyDayWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
		logger.info("selectUserCompanyDayWalkList " + hnjbzWalkDayStatistic.toString());
		if (StringUtils.isEmpty(hnjbzWalkDayStatistic.getOpenid())) {
			return R.error("用户openid不能为空");
		}
		try {
			return walkStatisticService.selectUserCompanyDayWalkList(hnjbzWalkDayStatistic);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("查询失败");
		}
	}


    //个人当月排行
    @RequestMapping("/selectPersionMonthWalkList")
    public RetData<Object> selectPersionMonthWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
        logger.info("selectPersionMonthWalkList " + hnjbzWalkDayStatistic.toString());
        try {
            return walkStatisticService.selectPersionMonthWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }


    //部门当月排行
    @RequestMapping("/selectOrganMonthWalkList")
    public RetData<Object> selectOrganMonthWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
        logger.info("selectOrganMonthWalkList " + hnjbzWalkDayStatistic.toString());
        try {
            return walkStatisticService.selectOrganMonthWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }

	//当前用户部门当月排行
	@RequestMapping("/selectUserOrganMonthWalkList")
	public R selectUserOrganMonthWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
		logger.info("selectUserOrganMonthWalkList " + hnjbzWalkDayStatistic.toString());
		try {
			return walkStatisticService.selectUserOrganMonthWalkList(hnjbzWalkDayStatistic);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(-1, "查询失败");
		}
	}


    //单位当月排行
    @RequestMapping("/selectCompanyMonthWalkList")
    public RetData<Object> selectCompanyMonthWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {

        logger.info("selectCompanyMonthWalkList " + hnjbzWalkDayStatistic.toString());
        try {
            return walkStatisticService.selectCompanyMonthWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }

	//当前用户单位当月排行
	@RequestMapping("/selectUserCompanyMonthWalkList")
	public R selectUserCompanyMonthWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {

		logger.info("selectUserCompanyMonthWalkList " + hnjbzWalkDayStatistic.toString());
		try {
			return walkStatisticService.selectUserCompanyMonthWalkList(hnjbzWalkDayStatistic);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(-1, "查询失败");
		}
	}

    //个人季度排行
    @RequestMapping("/selectPersionQuarterWalkList")
    public RetData<Object> selectPersionQuarterWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {

        logger.info("selectPersionQuarterWalkList " + hnjbzWalkDayStatistic.toString());
        try {
            return walkStatisticService.selectPersionQuarterWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }


    //部门季度排行
    @RequestMapping("/selectOrganQuarterWalkList")
    public RetData<Object> selectOrganQuarterWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {

        logger.info("selectOrganQuarterWalkList " + hnjbzWalkDayStatistic.toString());
        try {
            return walkStatisticService.selectOrganQuarterWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }

    }

	//当前用户所属部门季度排行
	@RequestMapping("/selectUserOrganQuarterWalkList")
	public R selectUserOrganQuarterWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
		logger.info("selectUserOrganQuarterWalkList " + hnjbzWalkDayStatistic.toString());
		try {
			return walkStatisticService.selectUserOrganQuarterWalkList(hnjbzWalkDayStatistic);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(-1, "查询失败");
		}
	}


    //单位季度排行
    @RequestMapping("/selectCompanyQuarterWalkList")
    public RetData<Object> selectCompanyQuarterWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
        logger.info("selectCompanyQuarterWalkList " + hnjbzWalkDayStatistic.toString());
        try {
            return walkStatisticService.selectCompanyQuarterWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }

	//当前用户单位季度排行
	@RequestMapping("/selectUserCompanyQuarterWalkList")
	public R selectUserCompanyQuarterWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
		logger.info("selectUserCompanyQuarterWalkList " + hnjbzWalkDayStatistic.toString());
		try {
			return walkStatisticService.selectUserCompanyQuarterWalkList(hnjbzWalkDayStatistic);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(-1, "查询失败");
		}
	}


    //个人总排行
    @RequestMapping("/selectPersionTotalWalkList")
    public RetData<Object> selectPersionTotalWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {

        logger.info("selectPersionTotalWalkList " + hnjbzWalkDayStatistic.toString());
        try {
            return walkStatisticService.selectPersionTotalWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }


    //部门总排行
    @RequestMapping("/selectOrganTatalWalkList")
    public RetData<Object> selectOrganTatalWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {

        logger.info("selectOrganTatalWalkList " + hnjbzWalkDayStatistic.toString());
        try {
            return walkStatisticService.selectOrganTatalWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }

	//当前用户部门总排行
	@RequestMapping("/selectUserOrganTatalWalkList")
	public R selectUserOrganTatalWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {
		logger.info("selectUserOrganTatalWalkList " + hnjbzWalkDayStatistic.toString());
		try {
			return walkStatisticService.selectUserOrganTatalWalkList(hnjbzWalkDayStatistic);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(-1, "查询失败");
		}
	}


    //单位总排行
    @RequestMapping("/selectCompanyTatalWalkList")
    public RetData<Object> selectCompanyTatalWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {

        logger.info("selectCompanyTatalWalkList " + hnjbzWalkDayStatistic.toString());
        try {
            return walkStatisticService.selectCompanyTatalWalkList(hnjbzWalkDayStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new RetData<>(-1, "查询失败");
        }
    }

	//当前用户单位总排行
	@RequestMapping("/selectUserCompanyTatalWalkList")
	public R selectUserCompanyTatalWalkList(@RequestBody HnjbzWalkDayStatistic hnjbzWalkDayStatistic, HttpServletRequest request) {

		logger.info("selectUserCompanyTatalWalkList " + hnjbzWalkDayStatistic.toString());
		try {
			return walkStatisticService.selectUserCompanyTatalWalkList(hnjbzWalkDayStatistic);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(-1, "查询失败");
		}
	}


}
