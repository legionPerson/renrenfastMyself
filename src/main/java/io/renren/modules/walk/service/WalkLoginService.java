package io.renren.modules.walk.service;

import io.renren.common.utils.R;
import io.renren.modules.walk.vo.WalkLoginVo;
import io.renren.modules.walk.vo.WalkPhoneLoginVo;

/**
 * 
 * @author zln
 *
 */
public interface WalkLoginService{
	
	R loginIn(WalkLoginVo walkLoginVo);

	R phoneLoginIn(WalkPhoneLoginVo walkPhoneLoginVo);

	R generateIdentifyCode(WalkPhoneLoginVo walkPhoneLoginVo);

}
