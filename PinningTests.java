/**
 * Created by goot on 7/13/16.
 */
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PinningTests {
    private MainPanel panel;

    @Before
    public void setUp() throws Exception {
        panel = new MainPanel(15);
    }


    @Test
    public void convertToIntTestZero(){
        Method method;

        try {
            Class[] argTypes = new Class[] { int.class };
            method = MainPanel.class.getDeclaredMethod("convertToInt", argTypes);
            method.setAccessible(true);
            MainPanel mp2 = new MainPanel(15);
            Object returnValue = method.invoke(mp2, 0);
            int result = ((Integer) returnValue).intValue();

            assertEquals(0, result);
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void continuousNumTurnsTest(){
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                panel.stop();
            }
        }, 2, TimeUnit.SECONDS);
        panel.runContinuous();
    }

}