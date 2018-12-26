package ru.pk.testStreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class StarterPeek {
    private static Logger log = LoggerFactory.getLogger(StarterPeek.class);

    public static void main(String[] parameters) {
        TestPeek tr = new TestPeek();
        tr.init();
        Map<TestPeek.CollectionType, Collection<String>> result = tr.doPeek();
    }
}
