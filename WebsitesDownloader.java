import com.sun.javaws.exceptions.InvalidArgumentException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Azathoth on 17. 11. 2014.
 */
public class WebsitesDownloader {

    private List<String> links;
    private String baseLink;
    private String questionsFolder;

    public WebsitesDownloader() {
        this.links = new ArrayList<String>();
        this.links.add("http://www.autoskola-testy.cz/prohlizeni_otazek.php?okruh=1");
        this.links.add("http://www.autoskola-testy.cz/prohlizeni_otazek.php?okruh=2");
        this.links.add("http://www.autoskola-testy.cz/prohlizeni_otazek.php?okruh=3");
        this.links.add("http://www.autoskola-testy.cz/prohlizeni_otazek.php?okruh=4");
        this.links.add("http://www.autoskola-testy.cz/prohlizeni_otazek.php?okruh=5");
        this.links.add("http://www.autoskola-testy.cz/prohlizeni_otazek.php?okruh=6");
        this.links.add("http://www.autoskola-testy.cz/prohlizeni_otazek.php?okruh=7");
        this.baseLink = "http://www.autoskola-testy.cz/prohlizeni_otazek.php";
        this.questionsFolder = "questionsAndAnswers/";
    }

    public void downloadLinksFromAllSites() throws IOException {
        for(String url: this.links) {
            downloadLinksFromWebsite(url);
        }
    }

    private void downloadLinksFromWebsite(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        Document document = connection.get();
        Elements links = document.select("a[href^=\"?otazka\"]");
//        System.out.println(links.toString());
        List<String> hypertexts = new ArrayList<String>();
        for(Element link: links){
            String relativeLink = link.attr("href");
            System.out.println(relativeLink);
            String absoluteLink = this.baseLink+relativeLink;
            hypertexts.add(absoluteLink);
        }
        saveLinksToFile(hypertexts);
    }

    private void saveLinksToFile(List<String> links) throws IOException {
        String fileName = "linksToQuestions.txt";
        saveToFile(fileName, links);
    }

    public void downloadQuestionsAndAnswers() throws IOException {
        File file = new File("linksToQuestions.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {
            String url = reader.readLine();
            System.out.println(url);
            downloadWebsite(url);
        }
    }

    public void downloadWebsite(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        Document document = connection.get();
        connection.timeout(1000000000);
        String documentText = document.toString();
        String fileName = url.substring(url.indexOf("=")+1);
        saveToFile(questionsFolder +fileName, documentText);
    }

    private void saveToFile(String fileName, List<String> content) throws IOException {
        File file = new File(fileName);
        PrintWriter outputFile;
        if (!file.exists()) {
            outputFile = new PrintWriter(fileName);
        } else {
            outputFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
        }
        for (String line : content) {
            outputFile.println(line);
        }
        outputFile.close();
    }

    private void saveToFile(String fileName, String content) throws IOException {
        List<String> contentList = new ArrayList<String>();
        contentList.add(content);
        saveToFile(fileName, contentList);
    }

    public List<Question> parseQuestionsAndAnswersFromHTMLFiles() throws IOException, InvalidArgumentException {
        File folder = new File(questionsFolder);
        File[] listOfFiles = folder.listFiles();
        List<Question> questions = new ArrayList<Question>();
        for (File file : listOfFiles) {
            Question question = parseQuestionsAndAnswersFromHTMLFile(file);
            if (question != null) {
                questions.add(question);
            }
        }
        return questions;
    }

    private Question parseQuestionsAndAnswersFromHTMLFile(File file) throws IOException, InvalidArgumentException {
        Document document = Jsoup.parse(file, "windows-1250");
        return parseQuestionsAndAnswersFromDocument(document);
    }

    public Question parseQuestionsAndAnswersFromDocument(Document document) throws InvalidArgumentException {
        String title = document.title();
//        System.out.println(title);
        int baseLength = "AutoĹˇkola testy - OtĂˇzka".length();
        int numberStart;
        if (title.indexOf('0') == -1){
            numberStart = title.indexOf('1');
        } else if(title.indexOf('1') == -1) {
            numberStart = title.indexOf('0');
        } else {
            numberStart = Math.min(title.indexOf('0'), title.indexOf('1'));
        }

        String question = document.title().substring(numberStart);
        int questionNumber = Integer.parseInt(question);
        Elements divs = document.select("div[style=margin-left:20;width:770px;]");
        Element divWithQuestion = divs.first();
        Elements lists = divWithQuestion.select("ul");
        Element questionList = lists.first();
        return parseAnswers(questionList, questionNumber);
    }

    private Question parseAnswers(Element ul, int questionNumber) throws InvalidArgumentException {
        Elements answers = ul.select("li");             //vybere všechny tři odpovědi
        boolean textAnswers = true;
        int firstAnswerLength = 0;
        String firstAnswer = "";
        int secondAnswerLength = 0;
        String secondAnswer = "";
        int thirdAnswerLength = 0;
        String thirdAnswer = "";
        int iterCount = 0;
        int correctAnswerLength = 0;
        String correctAnswerText = "";
        for (Element answer : answers) {
            int answerLength = 0;
            String answerText = "";
            iterCount++;
            Elements correctAnswers = answer.select("span[id=otazka_spravna]");         //zjistí, zda je odpověď správná
            if (correctAnswers.isEmpty()) {
                String text = answer.ownText();                                         //odpověď není správná, odtrhne se očíslování odpovědi
                if (text.length() < 4) {                                                  //text odpovědi je menší než min. délka (4, protože jednopísmenná odpověď+číslování)
                    textAnswers = false;
                    continue;
                } else {
                    String textWithoutNumbers = text.substring(3);
                    answerText = textWithoutNumbers;
                    answerLength = answerText.length();
//                    System.out.println(textWithoutNumbers);
//                    System.out.println(answerLength);
                }
            } else {
                Element correctAnswer = correctAnswers.first();             //odpověď je správná a proto je v tagu, ze kterého se vytáhne
                correctAnswerText = correctAnswer.ownText();
                answerText = correctAnswerText;
                answerLength = answerText.length();
                correctAnswerLength = answerLength;
//                System.out.println(correctAnswerText);
//                System.out.println(answerLength);
            }
            switch (iterCount) {
                case 1:
                    firstAnswer = answerText;
                    firstAnswerLength = answerLength;
                    break;
                case 2:
                    secondAnswer = answerText;
                    secondAnswerLength = answerLength;
                    break;
                case 3:
                    thirdAnswer = answerText;
                    thirdAnswerLength = answerLength;
                    break;
            }
        }
        if (textAnswers) {
            CorrectAnswer correctAnswer = getCorrectAnswerEnum(firstAnswer, secondAnswer, thirdAnswer, correctAnswerText);
            Question question = new Question(questionNumber, correctAnswer, firstAnswer, secondAnswer, thirdAnswer);
//            System.out.println(question.toString());
            return question;
        }
        return null;
    }

    private CorrectAnswer getCorrectAnswerEnum(String first, String second, String third, String correct) throws InvalidArgumentException {
        if (correct.equals(first)) {
            return CorrectAnswer.FIRST;
        } else if(correct.equals(second)) {
            return CorrectAnswer.SECOND;
        } else if (correct.equals(third)) {
            return CorrectAnswer.THIRD;
        }else {
            throw new InvalidArgumentException(new String[]{"Can not determine which answer is correct.", String.valueOf(first), String.valueOf(second), String.valueOf(third), String.valueOf(correct)});
        }
    }

}
