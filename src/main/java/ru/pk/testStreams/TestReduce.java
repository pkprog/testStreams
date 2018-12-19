package ru.pk.testStreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pk.testStreams.obj.Human;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestReduce {
    private static Logger log = LoggerFactory.getLogger(TestReduce.class);

    List<Human> list = new LinkedList<>();

    public void init() {
        log.info("Start");
        List<String> surnameList = getSurnamesLoader().load(2000);
        log.warn("Surnames count=", surnameList.size());
        List<String> namesList = getNamesLoader().load(100);
        log.warn("Names count=", namesList.size());

        List<Human> hList = surnameList.stream()
                .map(s -> {
                    long age = Math.round(Math.random() * 30);
                    int namesCount = namesList.size();
                    long nameIndex = Math.round(Math.random() * (namesCount-1));

                    Human h = new Human(namesList.get((int)nameIndex), "", s, age);
                    return h;
                })
                .collect(Collectors.toList());

        log.info("Generated humans:");
        hList.forEach(h -> log.info(h.toString()));

        list.addAll(hList);
    }

    public Human getFirstBySurname(String surname) {
        Human hNull = Human.getNull();
        Human hSurname = new Human(null, null, surname, Long.MIN_VALUE);

        Human resultNew = list.stream().filter(Objects::nonNull).reduce(hSurname, (h1, h2) -> {
            if (h1.getLastName().equalsIgnoreCase(h2.getLastName())) {
                return h2;
            }
            return h1;
        });

/*
        Optional<Human> result = list.stream().reduce((h1, h2) -> {
            if (h1 != null && surname.equalsIgnoreCase(h1.toString())) {
                return h1;
            } else if (h2 != null && surname.equalsIgnoreCase(h2.toString())) {
                return h2;
            } else
                return hNull;
        });

        return result.get().equals(hNull) ? null : result.get();
*/

        return resultNew.equals(hSurname) ? null : resultNew;
    }

    private ListLoader getSurnamesLoader() {
        return ListLoaderFactory.createGufoDictLoaderSurnames();
    }
    private ListLoader getNamesLoader() {
        return ListLoaderFactory.createGufoDictLoaderNames();
    }

}
