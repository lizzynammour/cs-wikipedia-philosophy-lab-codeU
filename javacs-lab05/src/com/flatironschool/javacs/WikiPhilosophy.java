package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class WikiPhilosophy {
    
    final static WikiFetcher wf = new WikiFetcher();
    
    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     *
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     *
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // some example code to get you started
        
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wf.fetchWikipedia(url);
        
        Element firstPara = paragraphs.get(0);
        LinkedList<String> visited = new LinkedList<String>();
        LinkedList<String> toVisit = new LinkedList<String>();
        
        Iterable<Node> iter = new WikiNodeIterable(firstPara);
        System.out.println(recursiveFetch(url, visited));
        //System.out.println(node);
        //System.out.println(link);
        
        // the following throws an exception so the test fails
        // until you update the code
        
    }
    
    private static boolean recursiveFetch(String url, LinkedList<String> visited) throws IOException {
        System.out.println(url);
        Elements paragraphs = wf.fetchWikipedia(url);
        
        Element firstPara = paragraphs.get(0);
        Iterable<Node> iter = new WikiNodeIterable(firstPara);
        String first = firstValidLink(iter, url, visited);
        if (first.isEmpty()) {
            return false;
        }
        
        if(first.equals("https://en.wikipedia.org/wiki/Philosophy")) {
            return true;
        }
        return recursiveFetch(first, visited);
    }
    
    private static String firstValidLink(Iterable<Node> iter, String current, LinkedList<String> visited) {
        String link = "";
        for (Node node: iter) {
            if (node instanceof Element) {
                Element el = (Element) node;
                String url =  node.attr("href");
                Element element = el;
                element = element.parent();
                if (!visited.contains(url)&&!element.hasAttr("a") && !element.hasAttr("em") &&url.toLowerCase().startsWith("/wiki") &&!url.isEmpty() && !("https://en.wikipedia.org/wiki"+url).equals(current)) {
                    visited.add(url);
                    return "https://en.wikipedia.org"+url;
                }
            }
            
            
            
        }
        
        
        return link;
    }
    
}

