package io.renren.modules.walk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzOrg;



public interface WalkOrgService extends IService<HnjbzOrg> {

    R getOrgList();
}
