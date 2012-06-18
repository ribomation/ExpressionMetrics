package com.ribomation.expressionmetrics;

/**
 * Encapsulation of a single reflective expression.
 * @author  jens
 * @date  2012-06-04
 */
public interface Expression {
    /**
     * Returns its lokkup name.
     * @return ita name
     */
    String getName();

    /**
     * Evaluates the expression.
     * @param target    the target object
     * @return the returned result
     */
    Object eval(Target target);

    /**
     * Returns the string representation of the expression.
     * @return the expression itself
     */
    String getExpression();
}
