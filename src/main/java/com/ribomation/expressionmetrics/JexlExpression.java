package com.ribomation.expressionmetrics;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.Script;

/**
 * Reflection expressions that uses JEXL.
 *
 * @author jens
 * @date 2012-06-04
 */
public class JexlExpression implements Expression {
    private String  name;
    private Script  script;

    private final JexlEngine jexl = new JexlEngine();
    
    {
        jexl.setCache(64);
        jexl.setLenient(true);
        jexl.setSilent(true);
    }

    public JexlExpression(String name, String expression) {
        this.name = name;
        this.script = jexl.createScript(expression);
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getExpression() {
        return script.getText();
    }

    @Override
    public Object eval(Target target) {
        JexlContext ctx = new MapContext();
        ctx.set("$$", target.getObject());
        ctx.set("$0", target.getReturn());
        final int parameterCount = target.getParameterCount();
        for (int p = 1; p <= parameterCount; ++p) {
            ctx.set("$"+p, target.getParameter(p));
        }
        
        return script.execute(ctx);
    }
    
}
