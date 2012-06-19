package com.ribomation.expressionmetrics;

import com.ribomation.expressionmetrics.expression.ClassNameExpression;
import com.ribomation.expressionmetrics.expression.JexlExpression;
import com.ribomation.expressionmetrics.expression.MethodNameExpression;
import com.ribomation.expressionmetrics.expression.PackageNameExpression;
import com.ribomation.expressionmetrics.logger.NullLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Keeps all expressions.
 *
 * @author jens
 * @date 2012-06-18
 */
public class ExpressionRepository  {
    public static final ExpressionRepository   instance = new ExpressionRepository();
    private final ConcurrentHashMap<String, Expression> expressions;
    private final AtomicLong lastModified = new AtomicLong(0);
    private Logger log = new NullLogger();

    public ExpressionRepository() {
        expressions = new ConcurrentHashMap<String, Expression>(128, 0.7F, 6 * Runtime.getRuntime().availableProcessors());
        add(new PackageNameExpression());
        add(new ClassNameExpression());
        add(new MethodNameExpression());
    }

    public ExpressionRepository(Logger log) {
        this();
        this.log = log;
    }

    public void setLogger(Logger log) {
        this.log = log;
    }

    /**
     * Returns the expression with the given name, or null.
     * @param name  its name
     * @return its expression object, or null
     */
    public Expression get(String name) {
        return expressions.get(name);
    }
    
    /**
     * Adds a new expression to the repo.
     *
     * @param expr new expression to insert
     */
    public Expression add(Expression expr) {
        log.i("Adding expression %s=%s", expr.getName(), expr.getExpression());
        expressions.put(expr.getName(), expr);
        return expr;
    }

    /**
     * Adds a set of expressions to the repo.
     * @param expressionSet     expressions
     */
    public void add(Properties expressionSet) {
        for (Map.Entry e : expressionSet.entrySet()) {
            add(new JexlExpression(e.getKey().toString(), e.getValue().toString()));
        }
    }

    /**
     * Inspects the last-modification timestamp of the given file and
     * reloads it, if it was newer.
     * @param expressionFile   the file
     */
    public void process(File expressionFile) {
        if (expressionFile == null) return;
        if (expressionFile.lastModified() > lastModified.get()) {
            try {
                lastModified.set(expressionFile.lastModified());
                log.i("Loading expressions file '%s'", expressionFile.getAbsolutePath());
                add(load(expressionFile));
            } catch (Exception e) {
                log.e("Failed to load expressions file", e);
            }
        }
    }

    /**
     * Loads the content of the file as a properties objects.
     * supports both '.properties' and ''.xml.
     * @param file          the file to load
     * @return              the expression set
     * @throws IOException  if something went wrong
     */
    protected static Properties load(File file) throws IOException {
        Properties p = new Properties();
        InputStream is = new FileInputStream(file);
        if (file.getName().endsWith(".properties")) {
            p.load(is);
        } else if (file.getName().endsWith(".xml")) {
            p.loadFromXML(is);
        }
        is.close();
        return p;
    }
    
    
}
