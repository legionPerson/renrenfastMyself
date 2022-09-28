package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.modules.walk.dao.HnjbzWellProductMapper;
import io.renren.modules.walk.entity.HnjbzWellProduct;
import io.renren.modules.walk.service.WalkWellProductService;
import io.renren.modules.walk.vo.WellProductVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  名优产品服务实现类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Slf4j
@Service
public class WalkWellProductServiceImpl extends ServiceImpl<HnjbzWellProductMapper, HnjbzWellProduct> implements WalkWellProductService {

    @Override
    public R getWellProductList(WellProductVo wellProductVo) {
        Page<HnjbzWellProduct> page = new Page<>(wellProductVo.getPage(),wellProductVo.getPageSize());
        Page<HnjbzWellProduct> infoPage = baseMapper.selectPage(page, null);
        return R.ok().put("data",infoPage);
    }

    @Override
    public R getWellProductById(String id) {
        WellProductVo wellProductVo = new WellProductVo();
        HnjbzWellProduct hnjbzWellProduct = baseMapper.selectById(id);
        return R.ok().put("data",hnjbzWellProduct);
    }

    @Override
    public R insertWellProduct(WellProductVo wellProductVo) {
        HnjbzWellProduct hnjbzWellProduct = new HnjbzWellProduct();
        hnjbzWellProduct.setName(wellProductVo.getName());
        hnjbzWellProduct.setUrl(wellProductVo.getUrl());
        hnjbzWellProduct.setDescribe(wellProductVo.getDescribe());
        hnjbzWellProduct.setImg(wellProductVo.getImg());
        hnjbzWellProduct.setCreateById(wellProductVo.getUserId());
        hnjbzWellProduct.setUpdateById(wellProductVo.getUserId());
        int count = baseMapper.insert(hnjbzWellProduct);
        return R.ok().put("data",count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateWellProduct(WellProductVo wellProductVo) {
        HnjbzWellProduct hnjbzWellProduct = baseMapper.selectById(wellProductVo.getId());
        if (StringUtils.isNotEmpty(wellProductVo.getName())){
            hnjbzWellProduct.setName(wellProductVo.getName());
        }
        if (StringUtils.isNotEmpty(wellProductVo.getUrl())){
            hnjbzWellProduct.setUrl(wellProductVo.getUrl());
        }
        if (StringUtils.isNotEmpty(wellProductVo.getDescribe())){
            hnjbzWellProduct.setDescribe(wellProductVo.getDescribe());
        }
        if (StringUtils.isNotEmpty(wellProductVo.getUserId())){
            hnjbzWellProduct.setUpdateById(wellProductVo.getUserId());
        }
        int count = baseMapper.updateById(hnjbzWellProduct);
        return R.ok().put("data",count);
    }
}
