package com.ribomation.expressionmetrics;

/**
 * DESCRIPTION
 *
 * @author jens
 * @date 2012-06-17
 */
public class PackageNameExpression implements Expression {
    
    @Override
    public String getName() {
        return "package";
    }

    @Override
    public String getExpression() {
        return "packageName";
    }

    @Override
    public Object eval(Target target) {
        return target.getObject().getClass().getPackage().getName();
    }
}
