package com.ribomation.expressionmetrics;

/**
 * DESCRIPTION
 *
 * @author jens
 * @date 2012-06-17
 */
public class MethodNameExpression implements Expression {
    
    @Override
    public String getName() {
        return "method";
    }

    @Override
    public String getExpression() {
        return "methodName";
    }

    @Override
    public Object eval(Target target) {
        return target.getMethod();
    }
    
}
