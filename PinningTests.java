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
    ///////////////////////////////////////////////////////////
    //THE FOLLOWING METHODS TEST MY MODIFIED convertToInt()
    ///////////////////////////////////////////////////////////

    //The method should return an int only if the int passed in is greater than or equal
    //to zero. Therefore, 0 should be returned if 0 is passed in.
    @Test
    public void convertToIntTestZero(){
        Method method;

        try {
            Class[] argTypes = new Class[] { int.class };
            method = MainPanel.class.getDeclaredMethod("convertToInt", argTypes);
            method.setAccessible(true);
            MainPanel panel2 = new MainPanel(15);
            Object returnValue = method.invoke(panel2, 0);
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
    //The method should return an int only if the int passed in is greater than or equal
    //to zero. Therefore, the MAX_VALUE of an integer should be returned when MAX_VALUE is passed in
    @Test
    public void convertToIntTestPositive(){
        Method method;
        try {
            Class[] argTypes = new Class[] { int.class };
            method = MainPanel.class.getDeclaredMethod("convertToInt", argTypes);
            method.setAccessible(true);
            MainPanel panel2 = new MainPanel(15);
            Object returnValue = method.invoke(panel2, Integer.MAX_VALUE);
            int result = ((Integer) returnValue).intValue();

            assertEquals(Integer.MAX_VALUE, result);
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        }

    }
    //The method should return an int only if the int passed in is greater than or equal
    //to zero. Therefore, the MIN_VALUE of an integer should throw an exception
    @Test
    public void convertToIntTestNegative(){
        Method method;
        try {
            Class[] argTypes = new Class[]{int.class};
            method = MainPanel.class.getDeclaredMethod("convertToInt", argTypes);
            method.setAccessible(true);
            MainPanel panel2 = new MainPanel(15);
            Object returnValue = method.invoke(panel2, Integer.MIN_VALUE);
            int result = ((Integer) returnValue).intValue();

            fail("No problem trying to convert negative");
        } catch (InvocationTargetException e) {
            return;
            //Test passes, negative ints should throw exception
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        }

    }

    ///////////////////////////////////////////////////////////
    //THE FOLLOWING METHODS TEST MY MODIFIED cell.toString()
    ///////////////////////////////////////////////////////////

    //Assure alive cells are set to "X"
    @Test
    public void cellAliveStringTestTrue(){
        Cell c = new Cell();
        c.setAlive(true);
        assertEquals("X", c.toString());
    }

    //Assure Dead cells are set to "."
    @Test
    public void cellDeadStringTestTrue(){
        Cell c = new Cell();
        c.setAlive(false);
        assertEquals(".", c.toString());
    }

    //Assure cells that haven't been modified are still dead by default
    @Test
    public void cellDefaultStringTestTrue(){
        Cell c = new Cell();//starts false by default
        assertEquals(".", c.toString());
    }

    //Assure that trying to set a cell's life multiple times works
    @Test
    public void cellAliveStringTestMultipleIterations(){
        Cell c = new Cell();
        c.setAlive(true);
        c.setAlive(false);
        c.setAlive(false);
        c.setAlive(true);
        c.setAlive(true);
        assertEquals("X", c.toString());
    }



    ///////////////////////////////////////////////////////////
    //THE FOLLOWING METHODS TEST MY MODIFIED runContinuous()
    ///////////////////////////////////////////////////////////


    //This test is what I used for my manual testing of my modified runContinuous().
    //The modified version is not easy to write a unit test for to assert the improvements
    //However this test let me conduct my manual test in a controlled environment
    //On my machine, the old code ran ~75 iterations in the 2 second span specified
    //My modified method runs ~230 iterations in the same time span with the same arguments
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