package com.ribomation.expressionmetrics;

import com.ribomation.expressionmetrics.logger.StdoutLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * DESCRIPTION
 *
 * @author jens
 * @date 2012-06-18
 */
public class ExpressionRepositoryTest {
    private ExpressionRepository repo;
    private ByteArrayOutputStream buf;
    private File file;

    @Before
    public void setUp() throws Exception {
        buf = new ByteArrayOutputStream();
        repo = new ExpressionRepository(new StdoutLogger(true, new PrintStream(buf, true)));
    }

    @After
    public void tearDown() throws Exception {
        if (file != null) {
            assert file.delete();
        }
    }

    private void writeTo(File f, String txt) throws IOException {
        FileWriter w = new FileWriter(f);
        w.write(txt);
        w.close();
    }

    @Test
    public void testGet() throws Exception {
        assertThat(repo.get("method").getExpression(), is("methodName"));
    }

    @Test
    public void testAdd() throws Exception {
        assertThat(repo.get("foo"), nullValue());
        repo.add(new Expression() {
            public String getName() {
                return "foo";
            }

            public Object eval(Target target) {
                return null;
            }

            public String getExpression() {
                return "bar";
            }
        });
        assertThat(repo.get("foo"), notNullValue());
        assertThat(repo.get("foo").getExpression(), is("bar"));
    }

    @Test
    public void testProcess() throws Exception {
        assertThat(repo.get("expr1"), nullValue());
        assertThat(repo.get("expr2"), nullValue());
        
        repo.process(file);
        assertThat(repo.get("expr1"), nullValue());
        assertThat(repo.get("expr2"), nullValue());

        file = File.createTempFile(this.getClass().getSimpleName(), ".properties");
        writeTo(file, "expr1=$$.answer\nexpr2=$1.toUpperCase()\n");
        repo.process(file);
        assertThat(repo.get("expr1"), notNullValue());
        assertThat(repo.get("expr2"), notNullValue());
        assertThat(repo.get("expr1").getExpression(), is("$$.answer"));
        assertThat(buf.toString(), containsString("Loading"));
        
        buf.reset();
        repo.process(file);
        assertThat(buf.toString(), is(""));

        writeTo(file, "expr1=$$.toString()\nexpr2=$1.toLowerCase()\n");
        repo.process(file);
        assertThat(buf.toString(), containsString("Loading"));
        assertThat(repo.get("expr1").getExpression(), is("$$.toString()"));
    }
    
    
}
