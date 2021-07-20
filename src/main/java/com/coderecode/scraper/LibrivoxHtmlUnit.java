package com.coderecode.scraper;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

public class LibrivoxHtmlUnit {

    public static void main(String[] args) {
        String url = "https://librivox.org/the-first-men-in-the-moon-by-hg-wells";
        HtmlPage page = getDocument(url);
        printBookTitleText(page);
        printBookTitle(page);
        printChapterTitle(page);
        printChapterDetails(page);
        printChapterTitleXpath(page);
    }

    private static void printBookTitleText(HtmlPage page) {
        String title = page.getTitleText();
        System.out.println(title);
    }

    public static void printBookTitle(HtmlPage page) {
        HtmlElement title = page.getFirstByXPath("//title");
        System.out.println(title.asNormalizedText());
    }

    public static void printChapterTitle(HtmlPage page) {
        String selector = ".chapter-download tbody tr td:nth-child(2)";
        DomNodeList<DomNode> cells = page.querySelectorAll(selector);
        for (var cell : cells) {
            System.out.println(cell.asNormalizedText());
        }
    }

    public static void printChapterTitleXpath(HtmlPage page) {
        String xpath = "//*[@class='chapter-download']//tbody/tr/td[2]";
        List<HtmlElement> cells = page.getByXPath(xpath);
        for (var cell : cells) {
            System.out.println(cell.asNormalizedText());
        }
    }

    public static void printChapterDetails(HtmlPage page) {
        String selector = ".chapter-download tbody tr";
        DomNodeList<DomNode> rows = page.querySelectorAll(selector);
        for (DomNode row : rows) {
            String chapter = row.querySelector("td:nth-child(2) a").asNormalizedText();
            String reader = row.querySelector("td:nth-child(3) a").asNormalizedText();
            String duration = row.querySelector("td:nth-child(4)").asNormalizedText();
            System.out.println(chapter + "\t " + reader + "\t " + duration);
        }
    }


    public static HtmlPage getDocument(String url) {
        HtmlPage page = null;
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        try {
            page = webClient.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }


}