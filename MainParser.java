import java.io.*;
import java.util.List;

/**
 Copyright (c) 2014, Matěj Račinský
 All rights reserved.

 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 disclaimer in the documentation and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
        BaseAnswerResolver resolver = new WordsAndCharactersAnswerResolver();

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

        double percentShortDifferent = ( (double) shortAnswers/ (double) totalWithDifferentLengths)*100;
        double percentMediumDifferent = ( (double) mediumAnswers/ (double) totalWithDifferentLengths)*100;
        double percentLongDifferent = ( (double) longAnswers/ (double) totalWithDifferentLengths)*100;

        double percentUnsure = ( (double) (sameLengths+sameLengthsInCorrectAnswer)/ (double) totalAnswers)*100;
        double percentTotal = percentLong+percentMedium+percentShort;
        double percentTotalWIthUnsure = percentUnsure+percentTotal;
        System.out.println("výsledky");
        System.out.println("procentuálně");
        System.out.println("nejkratší bez nejasných případů");
        System.out.println(percentShortDifferent+"%");
        System.out.println("středně dlouhé bez nejasných případů");
        System.out.println(percentMediumDifferent+"%");
        System.out.println("nejdelší bez nejasných případů");
        System.out.println(percentLongDifferent+"%");
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
        System.out.println("celkem i s neurčitými");
        System.out.println(percentTotalWIthUnsure+"%");
//        System.out.println("konkrétně");
//        System.out.println("nejkratší");
//        System.out.println(shortAnswers);
//        System.out.println("středně dlouhé");
//        System.out.println(mediumAnswers);
//        System.out.println("nejdelší");
//        System.out.println(longAnswers);
//        System.out.println("neurčitě");
//        System.out.println(sameLengths+sameLengthsInCorrectAnswer);
//        System.out.println("celkem");
//        System.out.println(totalWithDifferentLengths);
//
//        System.out.println("stejně dlouhé všechny odpovědi");
//        System.out.println(sameLengths);
//
//        System.out.println("stejně dlouhá správná odpověď jako vedlejší");
//        System.out.println(sameLengthsInCorrectAnswer);
//
//        System.out.println("celkem po přičtení otázek se stejně dlouhými odpověďmi");
//        System.out.println(totalAnswers);

        System.out.println("kontrola součtu");
        System.out.println(sameLengths+totalWithDifferentLengths+sameLengthsInCorrectAnswer);

    }

}
