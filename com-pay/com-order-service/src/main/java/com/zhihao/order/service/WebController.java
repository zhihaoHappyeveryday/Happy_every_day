package com.zhihao.order.service;

import com.eshuix.common.orderpay.enums.HandlerSuccessBeanName;
import com.eshuix.common.orderpay.enums.PayType;
import com.zhihao.common.orderpay.UnifiedPlaceAnOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class WebController {

	@Autowired
	private UnifiedPlaceAnOrder unifiedPlaceAnOrder;

//	@RequestMapping(value = "/findId")
//	public Map<String, String> findId() throws Exception {
//		//1.生成订单对象.....
//		//2.进行支付下单
//		Map<String, String> stringStringMap = unifiedPlaceAnOrder.placeAnOrder(PayType.ZFB_APP,
//				"123456789101112", "0.1", "测试主题", "咖啡",
//				"coffeeHandlerAfterSuccess");
//		return stringStringMap;
//	}

	@RequestMapping(value = "/findId")
	public void findId(HttpServletResponse response) throws Exception {
		//1.生成订单对象.....
		//2.进行支付下单
		Map<String, String> stringStringMap = unifiedPlaceAnOrder.placeAnOrder(PayType.ZFB_APP,
				"123456789101114", "0.01", "测试主题", "咖啡",
				HandlerSuccessBeanName.MEMBER_SUCCESS_HANDLER.getBeanName());
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(stringStringMap.get("payData"));//直接将完整的表单html输出到页面
		response.getWriter().flush();
		response.getWriter().close();
	}

    
    
}
