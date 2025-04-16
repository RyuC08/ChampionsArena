import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.lang.reflect.Modifier;

public class DynamicClassLoader {

    private final List<Class<?>> loadedClasses = new ArrayList<>();

    public DynamicClassLoader(String rootDirectoryPath) {
        File rootDir = new File(rootDirectoryPath);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder: " + rootDirectoryPath);
        }

        List<File> classFiles = new ArrayList<>();
        collectClassFiles(rootDir, classFiles);

        URL[] url = new URL[1];
        try {
            url[0] = rootDir.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid directory: " + rootDirectoryPath, e);
        }

        try (URLClassLoader loader = new URLClassLoader(url)) {
            for (File classFile : classFiles) {
                String className = getClassName(rootDir, classFile);
                try {
                    Class<?> clazz = loader.loadClass(className);
                    loadedClasses.add(clazz);
                } catch (ClassNotFoundException e) {
                    System.err.println("Failed to load: " + className);
                }
            }
        }
        catch (java.io.IOException e) {
            throw new RuntimeException("Failed to close class loader", e);
        }
    }

    private void collectClassFiles(File dir, List<File> classFiles) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                collectClassFiles(file, classFiles);
            } else if (file.getName().endsWith(".class")) {
                classFiles.add(file);
            }
        }
    }

    private String getClassName(File rootDir, File classFile) {
        String relativePath = classFile.getAbsolutePath().substring(rootDir.getAbsolutePath().length() + 1);
        return relativePath.replace(File.separatorChar, '.').replaceAll("\\.class$", "");
    }

    public List<Class<?>> getAllClasses() {
        return new ArrayList<>(loadedClasses);
    }

    public <T> List<Class<? extends T>> getSubtypesOf(Class<T> parentType) {
        List<Class<? extends T>> results = new ArrayList<>();
        for (Class<?> clazz : loadedClasses) {
            if (parentType.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                @SuppressWarnings("unchecked")
                Class<? extends T> casted = (Class<? extends T>) clazz;
                results.add(casted);
            }
        }
        return results;
    }
}