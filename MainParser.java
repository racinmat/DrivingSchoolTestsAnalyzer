import java.io.*;
import java.util.List;

/**
 * Created by Azathoth on 17. 11. 2014.
 */
public class MainParser {

    public static void main(String[] args) throws Exception {
        WebsitesDownloader downloader = new WebsitesDownloader();
//        downloader.downloadLinksFromAllSites();
//        downloader.downloadQuestionsAndAnswers();
        List<Question> questions;

//        questions = downloader.parseQuestionsAndAnswersFromHTMLFiles();
//        saveAnswersToFile(questions);

        questions = loadAnswersFromFile();
        calculateStatistics(questions);

//        String name = "questionsAndAnswers/4-stat_znamena";
//        File page = new File(name);
//        Connection connection = Jsoup.connect("http://www.autoskola-testy.cz/prohlizeni_otazek.php?otazka=471-muze_byt_schopnost_k_rizeni_vozidla_ovlivnena_i_po_poziti_maleho_mnozstvi_alkoholu_napr_pul_litr");
//        Document document = connection.get();
//        Document document = Jsoup.parse(page, "windows-1250");
//        downloader.parseQuestionsAndAnswersFromDocument(document);

    }

    public static void saveAnswersToFile(List<Question> questions) throws IOException {
        FileOutputStream fileOut =
                new FileOutputStream("questions.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(questions);
        out.close();
        fileOut.close();
    }

    public static List<Question> loadAnswersFromFile() {
        List<Question> questions;
        try {
            FileInputStream fileIn = new FileInputStream("questions.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            questions = (List<Question>) in.readObject();
            in.close();
            fileIn.close();
            return questions;
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        } catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
    }

    public static void calculateStatistics(List<Question> questions) throws Exception {
        BaseAnswerResolver resolver = new WordAnswerResolver();

        int shortAnswers = 0;
        int mediumAnswers = 0;
        int longAnswers = 0;
        int totalAnswers = 0;

        int totalWithDifferentLengths = 0;
        int sameLengths = 0;

        int sameLengthsInCorrectAnswer = 0;

        for (Question question : questions) {
            totalAnswers++;
            if (resolver.haveAllAnswersSameLength(question)) {
                sameLengths++;
            } else if(resolver.isCorrectAnswerAsLongAsAnother(question)) {
                sameLengthsInCorrectAnswer++;
            } else {
                totalWithDifferentLengths++;
                if (resolver.getCorrectAnswer(question) == CorrectAnswerLength.SHORT) {
                    shortAnswers++;
                } else if(resolver.getCorrectAnswer(question) == CorrectAnswerLength.MEDIUM) {
                    mediumAnswers++;
                } else if(resolver.getCorrectAnswer(question) == CorrectAnswerLength.LONG) {
                    longAnswers++;
                }
            }
        }
        double percentShort = ( (double) shortAnswers/ (double) totalAnswers)*100;
        double percentMedium = ( (double) mediumAnswers/ (double) totalAnswers)*100;
        double percentLong = ( (double) longAnswers/ (double) totalAnswers)*100;
        double percentUnsure = ( (double) (sameLengths+sameLengthsInCorrectAnswer)/ (double) totalAnswers)*100;
        double percentTotal = percentLong+percentMedium+percentShort;
        System.out.println("výsledky");
        System.out.println("procentuálně");
        System.out.println("nejkratší");
        System.out.println(percentShort+"%");
        System.out.println("středně dlouhé");
        System.out.println(percentMedium+"%");
        System.out.println("nejdelší");
        System.out.println(percentLong+"%");
        System.out.println("neurčitě");
        System.out.println(percentUnsure+"%");
        System.out.println("celkem");
        System.out.println(percentTotal+"%");
        System.out.println("konkrétně");
        System.out.println("nejkratší");
        System.out.println(shortAnswers);
        System.out.println("středně dlouhé");
        System.out.println(mediumAnswers);
        System.out.println("nejdelší");
        System.out.println(longAnswers);
        System.out.println("neurčitě");
        System.out.println(sameLengths+sameLengthsInCorrectAnswer);
        System.out.println("celkem");
        System.out.println(totalWithDifferentLengths);

        System.out.println("stejně dlouhé všechny odpovědi");
        System.out.println(sameLengths);

        System.out.println("stejně dlouhá správná odpověď jako vedlejší");
        System.out.println(sameLengthsInCorrectAnswer);

        System.out.println("celkem po přičtení otázek se stejně dlouhými odpověďmi");
        System.out.println(totalAnswers);

        System.out.println("kontrola součtu");
        System.out.println(sameLengths+totalWithDifferentLengths+sameLengthsInCorrectAnswer);

    }

}
