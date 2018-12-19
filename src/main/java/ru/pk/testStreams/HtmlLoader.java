package ru.pk.testStreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public abstract class HtmlLoader implements ListLoader {
    private static Logger log = LoggerFactory.getLogger(HtmlLoader.class);

    protected List<String> getPage(UrlPage urlPage) throws Exception {
        URL url = new URL(urlPage.getUrl());
        URLConnection urlConnection = url.openConnection();
        if (!(urlConnection instanceof HttpURLConnection)) {
            throw new RuntimeException("Connection non HTTP");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        if (HttpURLConnection.HTTP_NOT_FOUND == httpURLConnection.getResponseCode()) {
            return null;
        }

        InputStream inputStream = new BufferedInputStream(url.openStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> lines = new LinkedList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        log.warn("Readed lines:", lines.size());

        inputStream.close();
        httpURLConnection.disconnect();
        return lines;
    }

}
