package tech.pcloud.proxy.network.core.utils;

import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName ReflectionUtil
 * @Author pandong
 * @Date 2019/1/30 14:58
 **/
public class ReflectionUtil {

    public static <T> List<Class<? extends T>> listInterfaceImplements(String prefix, Class<T> interfaceClass) {
        Reflections reflections = new Reflections(prefix);
        Set<Class<? extends T>> classes = reflections.getSubTypesOf(interfaceClass);
        List<Class<? extends T>> classList = classes.stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .collect(Collectors.toList());

        return classList;
    }

    public static <T> T newInstance(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        return tClass.newInstance();
    }
}
