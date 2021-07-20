package com.coderecode.scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LibrivoxJsoup {
    public static void main(String[] args) {
        printBookTitleDOM(getDocument("https://en.wikipedia.org/wiki/Jsoup"));

        String url = "https://librivox.org/the-first-men-in-the-moon-by-hg-wells/";
        Document document = getDocument(url);
        printChapterTitle(document);
        printChapterDetails(document);
        printBookTitle(document);

    }

    public static void printBookTitleDOM(Document document) {
        Elements titles = document.getElementsByClass("firstHeading");
        System.out.println(titles.get(0).text());

    }

    public static void printBookTitle(Document document) {
        String selector = ".page.book-page h1";
        Element title = document.selectFirst(selector);
        if (title != null) System.out.println(title.text());

    }

    public static void printChapterTitle(Document document) {
        String selector = ".chapter-download tbody tr td:nth-child(2)";
        Elements cells = document.select(selector);
        for (Element cell : cells) {
            System.out.println(cell.text());
        }
    }

    public static void printChapterDetails(Document document) {
        String selector = ".chapter-download tbody tr";
        Elements rows = document.select(selector);
        for (Element row : rows) {
            final String chapter = row.select("td:nth-child(2) a").text();
            final String reader = row.select("td:nth-child(3) a").text();
            final String duration = row.select("td:nth-child(4)").text();
            System.out.println(chapter + "\t " + reader + "\t " + duration);
        }
    }


    public static Document getDocument(String url) {
        Document document = null;
        try {
            Connection conn = Jsoup.connect(url);
            conn.userAgent("custom user agent");
            conn.ignoreContentType(true); // useful for JSON response
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}