package com.ribomation.expressionmetrics;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * DESCRIPTION
 *
 * @author jens
 * @date 2012-06-17
 */
public class MetricNameFormatterTest {
    private MetricNameFormatter formatter;
    private Target target;
    private ByteArrayOutputStream buf;

    @Before
    public void setUp() throws Exception {
        buf = new ByteArrayOutputStream();
        Logger logger = new StdoutLogger(false, new PrintStream(buf, true));
        ExpressionRepository repo = new ExpressionRepository(logger);
        formatter = new MetricNameFormatter(repo);
        target = new FoobarTarget(this);
    }

    private class FoobarTarget implements Target {
        private final Object theTarget;
        private String param1 = "howdy";
        private Boolean param2 = Boolean.TRUE;

        public FoobarTarget(Object theTarget) {
            this.theTarget = theTarget;
        }

        public Object getObject() {
            return theTarget;
        }

        public Object getReturn() {
            return foobar(param1, param2);
        }

        public Object getParameter(int idx) {
            if (idx == 1) return param1;
            if (idx == 2) return param2;
            return null;
        }

        public int getParameterCount() {
            return 2;
        }

        public String getMethod() {
            return "foobar";
        }
    }
    
    public String foobar(String data, boolean cap) {
        return cap ? data.toUpperCase() : data.toLowerCase();
    }
    
    public int getAnswer() {
        return 42;
    }
    
    
    @Test
    public void testFormat_noSubstitution() throws Exception {
        String metricName = "root|sub|metric";
        String result = formatter.format(metricName, target);
        assertThat(result, is(metricName));
        System.out.printf("BUF: %s", buf);
    }

    @Test
    public void testFormat_empty() throws Exception {
        String metricName = "";
        String result = formatter.format(metricName, target);
        assertThat(result, is(metricName));
    }

    @Test
    public void testFormat_method() throws Exception {
        String metricName = "root|sub|{method}";
        String result = formatter.format(metricName, target);
        assertThat(result, is("root|sub|" + target.getMethod()));
    }

    @Test
    public void testFormat_package() throws Exception {
        String metricName = "root|sub|{package}";
        String result = formatter.format(metricName, target);
        assertThat(result, is("root|sub|" + this.getClass().getPackage().getName()));
    }

    @Test
    public void testFormat_class() throws Exception {
        String metricName = "root|sub|{class}";
        String result = formatter.format(metricName, target);
        assertThat(result, is("root|sub|" + this.getClass().getSimpleName()));
    }

    @Test
    public void testFormat_allThree() throws Exception {
        String metricName = "root|{package}|{class}|{method}";
        String result = formatter.format(metricName, target);
        assertThat(result, is("root|" + 
                this.getClass().getPackage().getName() + "|" + this.getClass().getSimpleName() + "|" + target.getMethod()));
    }

    @Test
    public void testFormat_noMatch() throws Exception {
        String metricName = "root|{none}|metric;a=$$;b=$1";
        String result = formatter.format(metricName, target);
        assertThat(result, is("root|{none}|metric"));
    }

    @Test
    public void testFormat_inlineExpr() throws Exception {
        String metricName = "root|{e}|metric;e=$$.answer";
        String result = formatter.format(metricName, target);
        assertThat(result, is("root|42|metric"));
    }
    
    @Test
    public void testFormat_inlineExpr2() throws Exception {
        String metricName = "root|{e}|metric;e=$$.foobar('howdy', true)";
        String result = formatter.format(metricName, target);
        assertThat(result, is("root|HOWDY|metric"));
    }

    @Test
    public void testFormat_inlineExpr3() throws Exception {
        String metricName = "root|{e1}|{e2}:{e3};e1=$1.toUpperCase();e2=!$2;e3=$0.substring(0,2)";
        String result = formatter.format(metricName, target);
        assertThat(result, is("root|HOWDY|false:HO"));
    }

}
