package gg.nextforge.nextbot.addons;

import java.net.URL;
import java.net.URLClassLoader;

public class ChildFirstClassLoader extends URLClassLoader {
    private final ClassLoader parent;

    public ChildFirstClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.parent = parent;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try { c = findClass(name); } catch (ClassNotFoundException ignored) {}
                if (c == null) c = parent.loadClass(name);
            }
            if (resolve) resolveClass(c);
            return c;
        }
    }
}
