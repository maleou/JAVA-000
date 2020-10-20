package homework.day01.work_2;

import java.io.*;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = new HelloClassLoader().findClass("Hello.xlass");
        System.out.println(clazz);
        Method hello = clazz.getMethod("hello");
        hello.invoke(clazz.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("src/main/java/homework/day01/work_2/" + name);
        System.out.println(file.getAbsolutePath());
        byte[] data = null;
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] bytes = new byte[1000];
            int n;
            while ((n = fis.read(bytes)) != -1) {
                bos.write(bytes, 0, n);
            }
            fis.close();
            data = bos.toByteArray();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (255 - data[i]);
        }
        return defineClass(data, 0, data.length);
    }
}
