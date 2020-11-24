package ru.gb.java3.lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class TestAnnot {
    public TestAnnot() {
    }

    @BeforeSuite
    public int prepareTest() {
        return 0;
    }
    @AfterSuite
    public int cleanupTest() {
        return 0;
    }
    @Test
    public int testMethod1() {
        return 1;
    }

    @Test(priority = 1)
    public int testMethod2() {
        return 2;
    }

    @Test(priority = 2)
    public int testMethod3() {
        return 3;
    }

    static class PriorityMethod {
        int priority;
        Method method;

        public PriorityMethod(int priority, Method method) {
            this.priority = priority;
            this.method = method;
        }
    }

    static void start(String className) throws ClassNotFoundException, RuntimeException {
        ArrayList<PriorityMethod> tests = new ArrayList<>();
        Method before = null, after = null;

        Class c = Class.forName(className);
        Method[] methods = c.getMethods();
        for (Method m: methods) {
            Test a = m.getAnnotation(Test.class);
            if ( a != null)
                tests.add(new PriorityMethod(a.priority(),m));
            else if (m.getAnnotation(BeforeSuite.class) != null)
                if (before == null) before = m;
                else throw new RuntimeException("Multiple 'before'");
            else if (m.getAnnotation(AfterSuite.class) != null)
                if (after == null) after = m;
                else throw new RuntimeException("Multiple 'after'");
        }
        if (before != null) tests.add(new PriorityMethod(0,before));
        if (after != null) tests.add(new PriorityMethod(11,after));
        tests.sort(new Comparator<PriorityMethod>() {
            @Override
            public int compare(PriorityMethod o1, PriorityMethod o2) {
                return o1.priority - o2.priority ;
            }
        });

        for (PriorityMethod t: tests) {
            try {
                TestAnnot o = new TestAnnot();
                Object result = t.method.invoke(o);
                System.out.println(t.method.getName() + "=" + result + " priority=" + t.priority);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
