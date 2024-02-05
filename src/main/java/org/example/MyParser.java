package org.example;

import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyParser {
    private final Map<String, String> reviews = new HashMap<>();

    public void parse(String url, int page) {
        Document document;
        String newUrl = page > 1 ? url + "/page/" + page : url;
        try {
            document = Jsoup.connect(newUrl).get();
            Elements elements = document.select(".reviewItem");
            for (Element el : elements) {
                String name = el.select(".profile_name > a").text();
                String review = el.select("span[itemprop]").text();
                reviews.put(name, review);
            }
            if (document.select(".navigator").get(0).select("li.arr:contains(»)").size() > 0) {
                parse(url, ++page);
            } else {
                writeToFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile() {
        String csvFilePath = "output.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            String[] header = {"Имя", "Рецензия"};

            writer.writeNext(header);

            reviews.forEach((name, review) -> writer.writeNext(new String[]{name, review}));

            System.out.println("Данные успешно записаны в файл " + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
