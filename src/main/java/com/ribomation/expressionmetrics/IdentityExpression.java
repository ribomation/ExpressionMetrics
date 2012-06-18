package com.ribomation.expressionmetrics;

/**
 * An expression that simply evaluates to its name.
 * Used for faulty expression keys lacking an expression definition. 
 *
 * @author jens
 * @date 2012-06-17
 */
public class IdentityExpression implements Expression {
    private String name;

    public IdentityExpression(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object eval(Target target) {
        return '{' + name + '}';
    }

    @Override
    public String getExpression() {
        return name;
    }
}
