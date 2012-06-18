package com.ribomation.expressionmetrics;

/**
 * DESCRIPTION
 *
 * @author jens
 * @date 2012-06-17
 */
public class ClassNameExpression implements Expression {
    
    @Override
    public String getName() {
        return "class";
    }

    @Override
    public String getExpression() {
        return "className";
    }

    @Override
    public Object eval(Target target) {
        return target.getObject().getClass().getSimpleName();
    }
    
}
