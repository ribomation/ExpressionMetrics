package com.ribomation.expressionmetrics;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logs to stdout.
 *
 * @author jens
 * @date 2012-06-18
 */
public class StdoutLogger implements Logger {
    private boolean verbose = true;
    private PrintStream out = System.out;
    private SimpleDateFormat    fmt = new SimpleDateFormat("HH:mm:ss");

    public StdoutLogger() {
    }

    public StdoutLogger(boolean verbose, PrintStream out) {
        this.verbose = verbose;
        this.out = out;
    }

    @Override
    public boolean isVerbose() {
        return verbose;
    }
    
    private Object[] concat(Object[] a1, Object[] a2) {
        Object[] r = new Object[a1.length + a2.length];
        for (int i = 0; i < a1.length; i++) r[i] = a1[i];
        for (int i = 0; i < a2.length; i++) r[i + a1.length] = a2[i];
        return r;
    }

    protected void print(String level, String msg, Object... args) {
        Object[] prefix = {fmt.format(new Date()), level};
        out.printf("%s %-6s: " + msg + "%n", concat(prefix, args));
    }

    @Override
    public void v(String pattern, Object... args) {
        print("VERBOSE", pattern, args);
    }
    @Override
    public void i(String pattern, Object... args) {
        print("INFO", pattern, args);
    }

    @Override
    public void e(String msg, Throwable error) {
        print("ERROR", msg);
        error.printStackTrace(out);
    }
    
}
