package tech.pcloud.proxy.configure.service.agents;

/**
 * @param <T> target object
 * @param <S> source obect
 * @ClassName ConfigureModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:00
 */
public interface ConfigureModelAgent<T, S> {
    default T toTarget(S s) {
        if (s == null) {
            return null;
        }
        return exchage2Target(s);
    }

    T exchage2Target(S s);

    default S toSource(T t) {
        if (t == null) {
            return null;
        }
        return exchange2Source(t);
    }

    S exchange2Source(T t);
}
