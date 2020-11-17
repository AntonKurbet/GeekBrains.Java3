
import org.junit.*;
import ru.gb.java3.lesson6.Testing;

public class TestingTest {
    private Testing t;

    @Before
    public void init(){
         t = new Testing();
    }

    @Test
    public void testProcessArray10() {
        Assert.assertArrayEquals(new int[]{1, 7},t.ProcessArray1(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}));
    }

    @Test(expected = RuntimeException.class)
    public void testProcessArray11() {
        Assert.assertArrayEquals(new int[]{1, 7},t.ProcessArray1(new int[]{1, 2, 9, 9, 2, 3, 9, 1, 7}));
    }

    @Test
    public void testProcessArray12() {
        Assert.assertArrayEquals(new int[]{2, 3, 8, 1, 7},t.ProcessArray1(new int[]{1, 2, 9, 4, 2, 3, 8, 1, 7}));
    }

    @Test
    public void testProcessArray20() {
        Assert.assertTrue(t.ProcessArray2(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}));
    }

    @Test
    public void testProcessArray21() {
        Assert.assertFalse(t.ProcessArray2(new int[]{8, 2, 9, 9, 2, 3, 9, 8, 7}));
    }

    @Test
    public void testProcessArray22() {
        Assert.assertTrue(t.ProcessArray2(new int[]{8, 2, 9, 1, 2, 3, 9, 8, 7}));
    }


}
