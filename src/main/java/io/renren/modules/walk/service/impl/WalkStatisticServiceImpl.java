package io.renren.modules.walk.service.impl;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.ref.RetData;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.walk.dao.HnjbzWalkStatisticDao;
import io.renren.modules.walk.entity.HnjbzWalkDayStatistic;
import io.renren.modules.walk.service.WalkStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class WalkStatisticServiceImpl implements WalkStatisticService {


    @Autowired
    private HnjbzWalkStatisticDao hnjbzWalkStatisticDao;


    //当日个人排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectPersionDayWalkList(HnjbzWalkDayStatistic record) {

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return new RetData<>(-1, "用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        record.setStepDate(DateUtils.format(new Date()));
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectPersionDayWalkList(searchPage, record);

        return new RetData<>(0, "获取个人每天步数排行成功", ipage);

    }


    //当日部门排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectOrganDayWalkList(HnjbzWalkDayStatistic record) {

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return new RetData<>(-1, "用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        record.setStepDate(DateUtils.format(new Date()));
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectOrganDayWalkList(searchPage, record);

        return new RetData<>(0, "获取部门每天步数排行成功", ipage);
    }

    //当日用户所属部门排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R selectUserOrganDayWalkList(HnjbzWalkDayStatistic record) {

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return R.error("用户所属单位不存在");
        }
        record.setCompanyId(companyId);
        record.setStepDate(DateUtils.format(new Date()));
        //所有部门排行
        List<HnjbzWalkDayStatistic> hnjbzWalkDayStatisticList = hnjbzWalkStatisticDao.selectOrganDayWalkList(record);
        //部门id
        String deptId = hnjbzWalkStatisticDao.selectUserDept(record.getOpenid());
        for (int i = 0; i < hnjbzWalkDayStatisticList.size(); i++) {
            HnjbzWalkDayStatistic hnjbzWalkDayStatistic = hnjbzWalkDayStatisticList.get(i);
            if (deptId.equals(hnjbzWalkDayStatistic.getOrgId())) {
                //获取用户所属部门排行并返回
                return R.ok("获取当前用户部门排行成功!").put("hnjbzWalkDayStatistic", hnjbzWalkDayStatistic).put("orderNum", i+1);
            }
        }
        return R.error("获取当前用户部门排行失败!");
    }


    //当日单位排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectCompanyDayWalkList(HnjbzWalkDayStatistic record) {

        String branchId = hnjbzWalkStatisticDao.selectUserBranch(record.getOpenid());
        if (StringUtils.isEmpty(branchId)) {
            return new RetData<>(-1, "用户所属网点不存在");
        }

        record.setBranchId(branchId);
        record.setStepDate(DateUtils.format(new Date()));
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectCompanyDayWalkList(searchPage, record);

        return new RetData<>(0, "获取单位每天步数排行成功", ipage);

    }

    //当日用户所属单位排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R selectUserCompanyDayWalkList(HnjbzWalkDayStatistic record) {
        String branchId = hnjbzWalkStatisticDao.selectUserBranch(record.getOpenid());
        if (StringUtils.isEmpty(branchId)) {
            return R.error("用户所属网点不存在");
        }
        record.setBranchId(branchId);
        record.setStepDate(DateUtils.format(new Date()));
        //所有单位排行
        List<HnjbzWalkDayStatistic> hnjbzWalkDayStatisticList = hnjbzWalkStatisticDao.selectCompanyDayWalkList(record);
        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        for (int i = 0; i < hnjbzWalkDayStatisticList.size(); i++) {
            HnjbzWalkDayStatistic hnjbzWalkDayStatistic = hnjbzWalkDayStatisticList.get(i);
            if (companyId.equals(hnjbzWalkDayStatistic.getCompanyId())) {
                return R.ok("获取单位每天步数排行成功").put("orderNum", i+1).put("hnjbzWalkDayStatistic", hnjbzWalkDayStatistic);
            }
        }
        return R.error("获取单位每天步数排行失败");
    }

    //每月个人排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectPersionMonthWalkList(HnjbzWalkDayStatistic record) {

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return new RetData<>(-1, "用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        record.setStepDateBegin(DateUtils.format(cal.getTime()));
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        record.setStepDateEnd(DateUtils.format(cal.getTime()));
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectPersionSummaryWalkList(searchPage, record);

        return new RetData<>(0, "获取个人每月步数排行成功", ipage);
    }

    //每月部门排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectOrganMonthWalkList(HnjbzWalkDayStatistic record) {

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return new RetData<>(-1, "用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        cal.set(Calendar.DAY_OF_MONTH, 1);
        record.setStepDateBegin(DateUtils.format(cal.getTime()));
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        record.setStepDateEnd(DateUtils.format(cal.getTime()));
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectOrganSummaryWalkList(searchPage, record);

        return new RetData<>(0, "获取部门每月步数排行成功", ipage);
    }

	//每月当前用户所属部门排行
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R selectUserOrganMonthWalkList(HnjbzWalkDayStatistic record) {

		String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
		if (StringUtils.isEmpty(companyId)) {
			return R.error("用户所属单位不存在");
		}

		record.setCompanyId(companyId);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		cal.set(Calendar.DAY_OF_MONTH, 1);
		record.setStepDateBegin(DateUtils.format(cal.getTime()));
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		record.setStepDateEnd(DateUtils.format(cal.getTime()));
		List<HnjbzWalkDayStatistic> hnjbzWalkDayStatisticList = hnjbzWalkStatisticDao.selectOrganSummaryWalkList(record);
        String deptId = hnjbzWalkStatisticDao.selectUserDept(record.getOpenid());
        for (int i = 0; i < hnjbzWalkDayStatisticList.size(); i++) {
            HnjbzWalkDayStatistic hnjbzWalkDayStatistic = hnjbzWalkDayStatisticList.get(i);
            if (deptId.equals(hnjbzWalkDayStatistic.getOrgId())) {
                return R.ok("获取部门每月步数排行成功").put("orderNum", i+1).put("hnjbzWalkDayStatistic", hnjbzWalkDayStatistic);
            }
        }
        return R.error("获取部门每月步数排行失败");
	}

    //每月单位排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectCompanyMonthWalkList(HnjbzWalkDayStatistic record) {

        String branchId = hnjbzWalkStatisticDao.selectUserBranch(record.getOpenid());
        if (StringUtils.isEmpty(branchId)) {
            return new RetData<>(-1, "用户所属网点不存在");
        }

        record.setBranchId(branchId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        cal.set(Calendar.DAY_OF_MONTH, 1);
        record.setStepDateBegin(DateUtils.format(cal.getTime()));
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        record.setStepDateEnd(DateUtils.format(cal.getTime()));
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectCompanySummaryWalkList(searchPage, record);

        return new RetData<>(0, "获取单位每月步数排行成功", ipage);
    }

    //每月用户所属单位排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R selectUserCompanyMonthWalkList(HnjbzWalkDayStatistic record) {

        String branchId = hnjbzWalkStatisticDao.selectUserBranch(record.getOpenid());
        if (StringUtils.isEmpty(branchId)) {
            return R.error("用户所属网点不存在");
        }

        record.setBranchId(branchId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        cal.set(Calendar.DAY_OF_MONTH, 1);
        record.setStepDateBegin(DateUtils.format(cal.getTime()));
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        record.setStepDateEnd(DateUtils.format(cal.getTime()));
        List<HnjbzWalkDayStatistic> hnjbzWalkDayStatisticList = hnjbzWalkStatisticDao.selectCompanySummaryWalkList(record);
        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        for (int i = 0; i < hnjbzWalkDayStatisticList.size(); i++) {
            HnjbzWalkDayStatistic hnjbzWalkDayStatistic = hnjbzWalkDayStatisticList.get(i);
            if (companyId.equals(hnjbzWalkDayStatistic.getCompanyId())) {
                return R.ok("获取部门每月步数排行成功").put("orderNum", i+1).put("hnjbzWalkDayStatistic", hnjbzWalkDayStatistic);
            }
        }
        return R.error("获取部门每月步数排行成功");
    }

    //季度个人排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectPersionQuarterWalkList(HnjbzWalkDayStatistic record) {

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return new RetData<>(-1, "用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;

        if (currentMonth <= 3) {
            record.setStepDateBegin(year + "-01-01");
            record.setStepDateEnd(year + "-03-31");

        } else if (currentMonth <= 6) {
            record.setStepDateBegin(year + "-04-01");
            record.setStepDateEnd(year + "-06-30");

        } else if (currentMonth <= 9) {
            record.setStepDateBegin(year + "-07-01");
            record.setStepDateEnd(year + "-09-30");

        } else if (currentMonth <= 12) {
            record.setStepDateBegin(year + "-10-01");
            record.setStepDateEnd(year + "-12-31");

        }
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectPersionSummaryWalkList(searchPage, record);

        return new RetData<>(0, "获取个人季度步数排行成功", ipage);
    }

    //季度部门排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectOrganQuarterWalkList(HnjbzWalkDayStatistic record) {

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return new RetData<>(-1, "用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;

        if (currentMonth <= 3) {
            record.setStepDateBegin(year + "-01-01");
            record.setStepDateEnd(year + "-03-31");

        } else if (currentMonth <= 6) {
            record.setStepDateBegin(year + "-04-01");
            record.setStepDateEnd(year + "-06-30");

        } else if (currentMonth <= 9) {
            record.setStepDateBegin(year + "-07-01");
            record.setStepDateEnd(year + "-09-30");

        } else if (currentMonth <= 12) {
            record.setStepDateBegin(year + "-10-01");
            record.setStepDateEnd(year + "-12-31");

        }
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectOrganSummaryWalkList(searchPage, record);

        return new RetData<>(0, "获取部门季度步数排行成功", ipage);
    }

    //季度用户所属部门排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R selectUserOrganQuarterWalkList(HnjbzWalkDayStatistic record) {

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return R.error("用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;

        if (currentMonth <= 3) {
            record.setStepDateBegin(year + "-01-01");
            record.setStepDateEnd(year + "-03-31");

        } else if (currentMonth <= 6) {
            record.setStepDateBegin(year + "-04-01");
            record.setStepDateEnd(year + "-06-30");

        } else if (currentMonth <= 9) {
            record.setStepDateBegin(year + "-07-01");
            record.setStepDateEnd(year + "-09-30");

        } else if (currentMonth <= 12) {
            record.setStepDateBegin(year + "-10-01");
            record.setStepDateEnd(year + "-12-31");

        }
        List<HnjbzWalkDayStatistic> hnjbzWalkDayStatisticList = hnjbzWalkStatisticDao.selectOrganSummaryWalkList(record);

        String deptId = hnjbzWalkStatisticDao.selectUserDept(record.getOpenid());
        for (int i = 0; i < hnjbzWalkDayStatisticList.size(); i++) {
            HnjbzWalkDayStatistic hnjbzWalkDayStatistic = hnjbzWalkDayStatisticList.get(i);
            if (deptId.equals(hnjbzWalkDayStatistic.getOrgId())) {
                return R.ok("获取部门季度步数排行成功").put("orderNum", i+1).put("hnjbzWalkDayStatistic", hnjbzWalkDayStatistic);
            }
        }
        return R.error("获取部门季度步数排行失败");
    }


    //季度单位排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectCompanyQuarterWalkList(HnjbzWalkDayStatistic record) {

        String branchId = hnjbzWalkStatisticDao.selectUserBranch(record.getOpenid());
        if (StringUtils.isEmpty(branchId)) {
            return new RetData<>(-1, "用户所属网点不存在");
        }

        record.setBranchId(branchId);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;

        if (currentMonth <= 3) {
            record.setStepDateBegin(year + "-01-01");
            record.setStepDateEnd(year + "-03-31");

        } else if (currentMonth <= 6) {
            record.setStepDateBegin(year + "-04-01");
            record.setStepDateEnd(year + "-06-30");

        } else if (currentMonth <= 9) {
            record.setStepDateBegin(year + "-07-01");
            record.setStepDateEnd(year + "-09-30");

        } else if (currentMonth <= 12) {
            record.setStepDateBegin(year + "-10-01");
            record.setStepDateEnd(year + "-12-31");

        }
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectCompanySummaryWalkList(searchPage, record);

        return new RetData<>(0, "获取单位季度步数排行成功", ipage);

    }

    //季度用户所属单位排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R selectUserCompanyQuarterWalkList(HnjbzWalkDayStatistic record) {

        String branchId = hnjbzWalkStatisticDao.selectUserBranch(record.getOpenid());
        if (StringUtils.isEmpty(branchId)) {
            return R.error("用户所属网点不存在");
        }

        record.setBranchId(branchId);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;

        if (currentMonth <= 3) {
            record.setStepDateBegin(year + "-01-01");
            record.setStepDateEnd(year + "-03-31");

        } else if (currentMonth <= 6) {
            record.setStepDateBegin(year + "-04-01");
            record.setStepDateEnd(year + "-06-30");

        } else if (currentMonth <= 9) {
            record.setStepDateBegin(year + "-07-01");
            record.setStepDateEnd(year + "-09-30");

        } else if (currentMonth <= 12) {
            record.setStepDateBegin(year + "-10-01");
            record.setStepDateEnd(year + "-12-31");

        }
        List<HnjbzWalkDayStatistic> hnjbzWalkDayStatisticList = hnjbzWalkStatisticDao.selectCompanySummaryWalkList(record);

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        for (int i = 0; i < hnjbzWalkDayStatisticList.size(); i++) {
            HnjbzWalkDayStatistic hnjbzWalkDayStatistic = hnjbzWalkDayStatisticList.get(i);
            if (companyId.equals(hnjbzWalkDayStatistic.getCompanyId())) {
                return R.ok("获取单位季度步数排行成功").put("orderNum", i+1).put("hnjbzWalkDayStatistic", hnjbzWalkDayStatistic);
            }
        }
        return R.error("获取单位季度步数排行失败");
    }


    //全部个人排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectPersionTotalWalkList(HnjbzWalkDayStatistic record) {

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return new RetData<>(-1, "用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectPersionSummaryWalkList(searchPage, record);

        return new RetData<>(0, "获取个人总步数排行成功", ipage);
    }


    //全部部门排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectOrganTatalWalkList(HnjbzWalkDayStatistic record) {
        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return new RetData<>(-1, "用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectOrganSummaryWalkList(searchPage, record);

        return new RetData<>(0, "获取部门总步数排行成功", ipage);

    }

    //全部用户所属部门排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R selectUserOrganTatalWalkList(HnjbzWalkDayStatistic record) {
        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        if (StringUtils.isEmpty(companyId)) {
            return R.error("用户所属单位不存在");
        }

        record.setCompanyId(companyId);
        List<HnjbzWalkDayStatistic> hnjbzWalkDayStatisticList = hnjbzWalkStatisticDao.selectOrganSummaryWalkList( record);

        String deptId = hnjbzWalkStatisticDao.selectUserDept(record.getOpenid());
        for (int i = 0; i < hnjbzWalkDayStatisticList.size(); i++) {
            HnjbzWalkDayStatistic hnjbzWalkDayStatistic = hnjbzWalkDayStatisticList.get(i);
            if (deptId.equals(hnjbzWalkDayStatistic.getOrgId())) {
                return R.ok("获取部门总步数排行成功").put("orderNum", i+1).put("hnjbzWalkDayStatistic", hnjbzWalkDayStatistic);
            }
        }
        return R.error("获取部门总步数排行失败");
    }

    //全部单位排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> selectCompanyTatalWalkList(HnjbzWalkDayStatistic record) {

        String branchId = hnjbzWalkStatisticDao.selectUserBranch(record.getOpenid());
        if (StringUtils.isEmpty(branchId)) {
            return new RetData<>(-1, "用户所属网点不存在");
        }

        record.setBranchId(branchId);
        Page<Map<String, Object>> searchPage = new Page<>(record.getPage(), record.getLimit());
        IPage<HnjbzWalkDayStatistic> ipage = hnjbzWalkStatisticDao.selectCompanySummaryWalkList(searchPage, record);

        return new RetData<>(0, "获取单位总步数排行成功", ipage);
    }

    //全部用户所属单位排行
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R selectUserCompanyTatalWalkList(HnjbzWalkDayStatistic record) {

        String branchId = hnjbzWalkStatisticDao.selectUserBranch(record.getOpenid());
        if (StringUtils.isEmpty(branchId)) {
            return R.error("用户所属网点不存在");
        }

        record.setBranchId(branchId);
        List<HnjbzWalkDayStatistic> hnjbzWalkDayStatisticList = hnjbzWalkStatisticDao.selectCompanySummaryWalkList(record);

        String companyId = hnjbzWalkStatisticDao.selectUserCompany(record.getOpenid());
        for (int i = 0; i < hnjbzWalkDayStatisticList.size(); i++) {
            HnjbzWalkDayStatistic hnjbzWalkDayStatistic = hnjbzWalkDayStatisticList.get(i);
            if (companyId.equals(hnjbzWalkDayStatistic.getCompanyId())) {
                return R.ok("获取单位总步数排行成功").put("orderNum", i+1).put("hnjbzWalkDayStatistic", hnjbzWalkDayStatistic);
            }
        }
        return R.error("获取单位总步数排行失败");
    }
}
