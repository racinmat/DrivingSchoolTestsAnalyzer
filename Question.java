import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * Created by Azathoth on 18. 11. 2014.
 */
public class Question implements Serializable {

    private int questionNumber;
    private CorrectAnswer correctAnswer;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;

    /**
     *
     * @param questionNumber
     * @param correctAnswer
     * @param firstAnswer
     * @param secondAnswer
     * @param thirdAnswer
     */
    public Question(int questionNumber, CorrectAnswer correctAnswer, String firstAnswer, String secondAnswer, String thirdAnswer) {
        this.questionNumber = questionNumber;
        this.correctAnswer = correctAnswer;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public CorrectAnswer getCorrectAnswer() {
        return correctAnswer;
    }

    public int getFirstAnswerLength() {
        return firstAnswer.length();
    }

    public int getSecondAnswerLength() {
        return secondAnswer.length();
    }

    public int getThirdAnswerLength() {
        return thirdAnswer.length();
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (questionNumber != question.questionNumber) return false;
        if (correctAnswer != question.correctAnswer) return false;
        if (!firstAnswer.equals(question.firstAnswer)) return false;
        if (!secondAnswer.equals(question.secondAnswer)) return false;
        if (!thirdAnswer.equals(question.thirdAnswer)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionNumber;
        result = 31 * result + correctAnswer.hashCode();
        result = 31 * result + firstAnswer.hashCode();
        result = 31 * result + secondAnswer.hashCode();
        result = 31 * result + thirdAnswer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionNumber=" + questionNumber +
                ", correctAnswer=" + correctAnswer +
                ", firstAnswer='" + firstAnswer + '\'' +
                ", secondAnswer='" + secondAnswer + '\'' +
                ", thirdAnswer='" + thirdAnswer + '\'' +
                '}';
    }

}
