package com.ribomation.expressionmetrics;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * DESCRIPTION
 *
 * @author jens
 * @date 2012-06-16
 */
public class JexlExpressionTest {
    private String name = "foobar";
    private String expr = "$$.getClass().getSimpleName()";
    
    @Test
    public void testGetName() throws Exception {
        assertThat(new JexlExpression(name, expr).getName(), is(name));
    }

    @Test
    public void testGetExpression() throws Exception {
        assertThat(new JexlExpression(name, expr).getExpression(), is(expr));
    }

    @Test
    public void testEval() throws Exception {
        final Object theTarget = this;
        Target target = new Target() {
            public Object getObject() { return theTarget; }
            public Object getReturn() { return "JexlExpressionTest"; }
            public Object getParameter(int idx) { return null; }
            public int    getParameterCount() { return 0; }
            public String getMethod() { return null; }
        };
        
        assertThat(new JexlExpression(name, expr).eval(target).toString(), is("JexlExpressionTest"));
    }
    
}
