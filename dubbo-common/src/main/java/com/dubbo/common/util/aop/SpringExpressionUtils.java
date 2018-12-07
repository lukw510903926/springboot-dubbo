package com.dubbo.common.util.aop;

import java.lang.reflect.Method;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpringExpressionUtils {

	private SpringExpressionUtils(){}

	public static <T> T parseValue(String key, Method method, Object[] args, Class<T> resultType) {

		StandardEvaluationContext context = new StandardEvaluationContext();
		Expression expression = getExpression(key, method, args, context);
		return expression != null ? expression.getValue(context, resultType) : null;
	}

	private static Expression getExpression(String key, Method method, Object[] args, EvaluationContext context) {

		try {
			// 获取被拦截方法参数名列表(使用Spring支持类库)
			LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
			String[] parameters = discoverer.getParameterNames(method);
			// 使用SPEL进行key的解析
			ExpressionParser parser = new SpelExpressionParser();
			// 把方法参数放入SPEL上下文中
			if(ArrayUtils.isNotEmpty(parameters)){
				for (int i = 0; i < parameters.length; i++) {
					context.setVariable(parameters[i], args[i]);
				}
			}
			return parser.parseExpression(key);
		} catch (ParseException e) {
			return null;
		}
	}
}
