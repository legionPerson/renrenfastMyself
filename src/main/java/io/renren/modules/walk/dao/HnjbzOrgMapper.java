package io.renren.modules.walk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.walk.entity.HnjbzOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  组织Mapper 接口
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Mapper
public interface HnjbzOrgMapper extends BaseMapper<HnjbzOrg> {

    @Select("select h1.name as branch,h4.name as company,h5.name as department from HNJBZ_ORG h1 left join HNJBZ_ORG h2 on h2.ORG_LEVEL=2 and h1.id=h2.PARENT_ID left join HNJBZ_ORG h3 on h3.ORG_LEVEL=3 and h2.id=h3.PARENT_ID left join HNJBZ_ORG h4 on h4.ORG_LEVEL=4 and h3.id=h4.PARENT_ID left join HNJBZ_ORG h5 on h5.ORG_LEVEL=5 and h4.id=h5.PARENT_ID where h1.ORG_LEVEL=1 and h5.id=#{orgId}")
    public HnjbzOrg getBranchAndDepartment(String orgId);
}
