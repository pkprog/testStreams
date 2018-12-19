package ru.pk.testStreams.gufosite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pk.testStreams.HtmlLoader;
import ru.pk.testStreams.StringTools;
import ru.pk.testStreams.UrlPage;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GufoMeLoader extends HtmlLoader {
    private static Logger log = LoggerFactory.getLogger(GufoMeLoader.class);

    private final String PAGE_GUFO_ME_BASE = "https://gufo.me/dict/";

    private final String PATTERN = "<a\\s.*>[a-zA-Zа-яА-Я\\-\\s]+<\\/a>"; //"<a\\s.*>[a-zA-Z\\-]+<\\/a>";

    private final Pattern patternA;
    private final GufoDict gufoDict;

    {
        this.patternA = Pattern.compile(PATTERN);
    }

    public GufoMeLoader(GufoDict gufoDict) {
        this.gufoDict = gufoDict;
    }

    public GufoMeLoader() {
        this.gufoDict = GufoDict.SURNAMES;
    }

    @Override
    public List<String> load(final int MAX_COUNT) {
        List<String> fioList = new LinkedList<>();

        int page = 1;
        Iterator<String> lettersIterator = new LettersIterator();
        String letter = lettersIterator.next();

        while(fioList.size() < MAX_COUNT) {
            UrlPage dynamicUrl = urlMaker(this.gufoDict, page, letter);

            List<String> lines;
            try {
                log.debug("Load {} page {} for letter {}", gufoDict.name(), page, letter);
                lines = getPage(dynamicUrl);
            } catch (Exception e) {
                log.error("Error reading site:", dynamicUrl);
                e.printStackTrace();
                return Collections.emptyList();
            }

            if (lines == null) {
                log.trace("Page not found. Next letter");
                if (lettersIterator.hasNext()) {
                    letter = lettersIterator.next();
                    page = 1;

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                    continue;
                } else
                    break;
            }

            for (String line : lines) {
                List<String> parsed = parseLine(line);
                for (int i = 0; i < parsed.size() && fioList.size() < MAX_COUNT; i++) {
                    fioList.add(parsed.get(i));
                }
            }

            page++;
        }

        return fioList;
    }

    private List<String> parseLine(String line) {
        List<String> parsed = new LinkedList<>();
        Matcher matcher = patternA.matcher(line);
        while (matcher.find()) {
            String text = matcher.group();

            int instr = text.indexOf("</a>");
            text = text.substring(0, instr);
            int instr2 = text.lastIndexOf(">");
            text = text.substring(instr2 + 1);

            if (StringTools.isEmpty(text) && text.length() > 1) {
                parsed.add(text);
            }
        }

        return parsed;
    }

    private UrlPage urlMaker(GufoDict dict, int page, String letter) {
//    private final String PAGE_URL = "https://gufo.me/dict/surnames_ru";
//    private final String PAGE_URL2 = "https://gufo.me/dict/surnames_ru?page=1&letter=%D0%B0";

        StringBuilder sb = new StringBuilder(PAGE_GUFO_ME_BASE)
                .append(dict.getValue())
                .append("?page=").append(page)
                .append("&letter=").append(letter);
        return new UrlPage(sb.toString());
    }

    private class LettersIterator implements Iterator<String> {
//        private final String LETTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        private final String LETTERS = "клмнопрстуфхцчшщъыьэюя";
        private int num = -1;

        @Override
        public boolean hasNext() {
            return getLetter(num+1) != null;
        }

        @Override
        public String next() {
            num++;
            String next = getLetter(num);
            if (next == null) throw new NoSuchElementException();
            return next;
        }

        private String getLetter(int num) {
            if (num >= LETTERS.length()) return null;
            return LETTERS.substring(num, num+1);
        }
    }

}
