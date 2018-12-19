package ru.pk.testStreams;

import ru.pk.testStreams.gufosite.GufoDict;
import ru.pk.testStreams.gufosite.GufoMeLoader;

public class ListLoaderFactory {

    public static ListLoader createGufoDictLoaderSurnames() {
        return createGufoDictLoader(GufoDict.SURNAMES);
    }
    public static ListLoader createGufoDictLoaderNames() {
        return createGufoDictLoader(GufoDict.NAMES);
    }

    private static ListLoader createGufoDictLoader(GufoDict gufoDict) {
        return new GufoMeLoader(gufoDict);
    }

}
