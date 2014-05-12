package dk.statsbiblioteket.hadoop.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    private static void selectFromStaticString() {
        String tableHtml = "<html>"
            + "<head>bla</head>"
            + "<body>"
            + "<h2>Headline</h2>"
            + "<center>"
            + "<table>"
            + "<thead>"
            + "<tr><th>Task Id</th><th>Start Time</th><th>Finish Time<br/></th><th>Error</th></tr>"
            + "</thead>"
            + "<tbody>"
            + "<tr>"
            + "<td><a href=\"http:foo.com/1\">task id</a></td>"
            + "<td>start time 1</td>"
            + "<td>finish time 1</td>"
            + "<td></td>"
            + "</tr>"
            + "<tr>"
            + "<td><a href=\"http:foo.com/2\">task id</a></td>"
            + "<td>start time 2</td>"
            + "<td>finish time 2</td>"
            + "<td></td>"
            + "</tr>"
            + "</tbody>"
            + "</table>"
            + "</center>"
            + "</body>"
            + "</html>";

        String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
        Document doc = Jsoup.parse(html);
        Element link = doc.select("a").first();

        String text = doc.body().text();
        String linkHref = link.attr("href");
        String linkText = link.text();

        String linkOuterH = link.outerHtml();
        String linkInnerH = link.html();

        System.out.println("The text: " + text);
        System.out.println("The a reference: " + linkHref);
        System.out.println("The link text: " + linkText);
        System.out.println("outer link: " + linkOuterH);
        System.out.println("Inner link: " + linkInnerH);

        Document table = Jsoup.parse(tableHtml);

        for (Element row : table.select("tr")) {
            Elements tds = row.select("td");
            if (tds.size() > 2) {
                System.out.println(tds.get(0).text() + ", " + tds.get(1).text() + ", " + tds.get(2).text());
            }
        }
    }

    public static void main(String[] args) {
        /* First try to parse a String */
        //selectFromStaticString();

        if (args.length!=1) {
            System.out.println("Must be invoked with one argument: path to HTML file.");
            return;
        }
        System.err.println("Will parse " + args[0] + " to stdout");

        try {

            /* Next try to parse a file */
            //File input = new File("src/resources/table1.html");
            File input = new File(args[0]);
            /* The last argument is used to resolve relative URLs that these tables
             * do not have so it's just an empty string 
             */
            Document table1 = Jsoup.parse(input, "UTF-8", "");
            LocalDateTime startTime, finishTime;

            for (Element row : table1.select("tr")) {
                Elements tds = row.select("td");
                if (tds.size() > 3) {
                    String mapId = tds.get(0).select("a").first().text();

                    startTime  = LocalDateTime.parse("2014/"+tds.get(1).text(), DateTimeFormatter.ofPattern("u/d/M k:m:s"));
                    finishTime = LocalDateTime.parse("2014/"+tds.get(2).text().substring(0,12), DateTimeFormatter.ofPattern("u/d/M k:m:s"));

                    Duration d = Duration.between(startTime, finishTime);


                    System.out.println(mapId + ", " + startTime + ", " + finishTime + ", " + d.getSeconds());
                }
            }
        } catch(IOException e) {
            System.out.println("Ups, couldn't parse the file: " + e);
        }
    }

}
