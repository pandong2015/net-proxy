package tech.pcloud.nnts.core.service;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.pcloud.framework.utility.common.IdGenerate;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class IdGenerateService {
    public enum IdType {
        REQUEST, SERVICE
    }

    private Map<IdType, AtomicLong> idMap = Maps.newConcurrentMap();

    public long generate(IdType type) {
        AtomicLong bId = idMap.get(type);
        if (bId == null) {
            bId = new AtomicLong();
            idMap.put(type, bId);
        }
        return IdGenerate.generate(bId.getAndIncrement());
    }
}
