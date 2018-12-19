package ru.pk.testStreams;

import java.util.List;

public interface ListLoader {
    List<String> load(int maxCount);
}
