import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * Created by Azathoth on 19. 11. 2014.
 */
abstract public class BaseAnswerResolver {

    public boolean haveAllAnswersSameLength(Question question) {
        return this.getFirstAnswerLength(question) == this.getSecondAnswerLength(question) && this.getSecondAnswerLength(question) == this.getThirdAnswerLength(question);
    }

    public boolean isCorrectAnswerAsLongAsAnother(Question question) throws Exception {
        if (this.getCorrectAnswer(question) == CorrectAnswerLength.SHORT) {
            return this.getShortestAnswerLength(question) == this.getMediumAnswerLength(question);
        } else if (this.getCorrectAnswer(question) == CorrectAnswerLength.MEDIUM) {
            return (this.getLongestAnswerLength(question) == this.getMediumAnswerLength(question)) || (this.getShortestAnswerLength(question) == this.getMediumAnswerLength(question));
        } else if (this.getCorrectAnswer(question) == CorrectAnswerLength.LONG) {
            return this.getLongestAnswerLength(question) == this.getMediumAnswerLength(question);
        } else {
            throw new InvalidPropertiesFormatException("wrong correct answer type");
        }
    }

    public CorrectAnswerLength getCorrectAnswer(Question question) throws Exception {
        int correct = 0;

        if (question.getCorrectAnswer() == CorrectAnswer.FIRST) {
            correct = getFirstAnswerLength(question);
        } else if(question.getCorrectAnswer() == CorrectAnswer.SECOND) {
            correct = getSecondAnswerLength(question);
        } else if (question.getCorrectAnswer() == CorrectAnswer.THIRD) {
            correct = getThirdAnswerLength(question);
        }

        if (correct == getShortestAnswerLength(question)) {
            return CorrectAnswerLength.SHORT;
        } else if(correct == getMediumAnswerLength(question)) {
            return CorrectAnswerLength.MEDIUM;
        } else if (correct == getLongestAnswerLength(question)) {
            return CorrectAnswerLength.LONG;
        }else {
            throw new InvalidArgumentException(new String[]{"Can not determine which answer is correct.", String.valueOf(getFirstAnswerLength(question)), String.valueOf(getSecondAnswerLength(question)), String.valueOf(getThirdAnswerLength(question)), String.valueOf(correct)});
        }
    }


    public int getShortestAnswerLength(Question question) {
        return Math.min(Math.min(this.getFirstAnswerLength(question), this.getSecondAnswerLength(question)), this.getThirdAnswerLength(question));
    }

    public int getMediumAnswerLength(Question question) throws Exception {
        List<Integer> answers = new ArrayList<Integer>();
        answers.add(this.getFirstAnswerLength(question));
        answers.add(this.getSecondAnswerLength(question));
        answers.add(this.getThirdAnswerLength(question));
        answers.remove(answers.indexOf(this.getLongestAnswerLength(question)));
        answers.remove(answers.indexOf(this.getShortestAnswerLength(question)));
        if (answers.size() != 1) {
            throw new Exception("wrong sizes");
        } else {
            return answers.get(0);
        }
    }

    public int getLongestAnswerLength(Question question) {
        return Math.max(Math.max(this.getFirstAnswerLength(question), this.getSecondAnswerLength(question)), this.getThirdAnswerLength(question));
    }


    public int getFirstAnswerLength(Question question) {
        return this.getLength(question.getFirstAnswer());
    }

    public int getSecondAnswerLength(Question question) {
        return this.getLength(question.getSecondAnswer());
    }

    public int getThirdAnswerLength(Question question) {
        return  this.getLength(question.getThirdAnswer());
    }

    abstract public int getLength(String string);
}
