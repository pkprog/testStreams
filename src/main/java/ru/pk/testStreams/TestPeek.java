package ru.pk.testStreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestPeek {
    private static Logger log = LoggerFactory.getLogger(TestPeek.class);

    public enum CollectionType {
        BEFORE_PEEK, AFTER_PEEK
    }

    private List<String> listNames = new LinkedList<>();

    public void init() {
        log.info("Start");
        List<String> surnameList = getSurnamesLoader().load(1000);
        listNames.addAll(surnameList);
        log.info("End");
    }

    public Map<CollectionType, Collection<String>> doPeek() {
        Collection<String> before = new ArrayList<>(listNames.size());
        before.addAll(listNames);
        Collection<String> after = listNames.stream()
                .map(StringBuilder::new)
                .peek(sb -> log.warn(sb.toString()))
                .peek(sb -> sb.append("->3"))
//                .peek(s -> {
//                    s = s + "->4";
//                })
                .peek(sb -> log.warn("2: {}", sb.toString()))
                .map(StringBuilder::toString)
                .collect(Collectors.toList());

        Map<CollectionType, Collection<String>> result = new HashMap<>();
        result.put(CollectionType.BEFORE_PEEK, before);
        result.put(CollectionType.AFTER_PEEK, after);
        return result;
    }

    private ListLoader getSurnamesLoader() {
        return ListLoaderFactory.createGufoDictLoaderSurnames();
    }

}
