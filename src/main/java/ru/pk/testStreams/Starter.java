package ru.pk.testStreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pk.testStreams.obj.Human;

public class Starter {
    private static Logger log = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] parameters) {
        TestReduce tr = new TestReduce();
        tr.init();
        Human human = tr.getFirstBySurname("Кузнецов");
        log.warn("Искомый {}", human == null ? "НЕ НАЙДЕН" : human.toString());
    }
}
