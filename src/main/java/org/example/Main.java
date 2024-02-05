package org.example;

public class Main {
    public static void main(String[] args) {
        String url = "https://www.kinopoisk.ru/film/326/reviews/ord/date/status/all/perpage/200";
        MyParser myParser = new MyParser();
        myParser.parse(url, 1);
    }
}
