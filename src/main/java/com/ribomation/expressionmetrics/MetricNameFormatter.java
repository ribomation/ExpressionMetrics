package com.ribomation.expressionmetrics;

import com.ribomation.expressionmetrics.expression.IdentityExpression;
import com.ribomation.expressionmetrics.expression.JexlExpression;

/**
 * Replaces '{key}' tokens in a string (metric name), with the value
 * of evaluated expressions.
 *
 * @author jens
 * @date 2012-06-17
 */
public class MetricNameFormatter {
    private ExpressionRepository    expressions;

    public MetricNameFormatter(ExpressionRepository repo) {
        this.expressions = repo;
    }

    public MetricNameFormatter() {
        this.expressions = ExpressionRepository.instance;
    }

    /**
     * Replaces all '{key}' tokens in the provided input string
     *
     * @param metricName input string
     * @param target     instrumentation context, i.e. object/method
     * @return the result of substituting all keys with evaluated values
     */
    public String format(String metricName, Target target) {
        final int N = metricName.length();
        final StringBuilder result = new StringBuilder(2 * N);
        for (int k = 0; k < N; ++k) {
            char ch = metricName.charAt(k);
            
            if (ch == ';') {
                break; // don't read past the first expression separator
            }
            if (ch != '{') {
                result.append(ch);
                continue; // not a key, carry on
            }

            // grab the key
            int start = ++k;
            while (k < N && metricName.charAt(k) != '}') ++k;
            int end = k;
            String key = metricName.substring(start, end);

            Expression expr = expressions.get(key);
            if (expr == null) {
                // load all expression definitions.
                String[] segments = metricName.split(";");
                for (int s = 1; s < segments.length; ++s) {
                    String[] def = segments[s].split("=");
                    if (def.length == 2) {
                        expressions.add(new JexlExpression(def[0], def[1]));
                    }
                }
                expr = expressions.get(key);

                // If still not found, then add it as an IdentityExpression.
                if (expr == null) {
                    expr = expressions.add(new IdentityExpression(key));
                }
            }
            
            result.append(expr.eval(target));
        }
        
        return result.toString();
    }

}
