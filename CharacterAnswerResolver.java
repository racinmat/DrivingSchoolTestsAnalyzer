import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * Created by Azathoth on 19. 11. 2014.
 */
public class CharacterAnswerResolver implements IAnswerResolver {

    public boolean haveAllAnswersSameLength(Question question) {
        return this.getFirstAnswerLength() == this.getSecondAnswerLength() && this.getSecondAnswerLength() == this.getThirdAnswerLength();
    }

    public boolean isCorrectAnswerAsLongAsAnother(Question question) throws InvalidPropertiesFormatException {
        if (this.getCorrectAnswer() == CorrectAnswerLength.SHORT) {
            return this.getShortestAnswerLength() == this.getMediumAnswerLength();
        } else if (this.getCorrectAnswer() == CorrectAnswerLength.MEDIUM) {
            return (this.getLongestAnswerLength() == this.getMediumAnswerLength()) || (this.getShortestAnswerLength() == this.getMediumAnswerLength());
        } else if (this.getCorrectAnswer() == CorrectAnswerLength.LONG) {
            return this.getLongestAnswerLength() == this.getMediumAnswerLength();
        } else {
            throw new InvalidPropertiesFormatException("wrong correct answer type");
        }
    }

    @Override
    public int getFirstAnswerLength(Question question) {
        return question.getFirstAnswer().length();
    }

    @Override
    public int getSecondAnswerLength(Question question) {
        return question.getSecondAnswer().length();
    }

    @Override
    public int getThirdAnswerLength(Question question) {
        return question.getThirdAnswer().length();
    }

    public CorrectAnswerLength getCorrectAnswer(Question question) throws InvalidArgumentException {
        List<Integer> answers = new ArrayList<Integer>();
        answers.add(this.getFirstAnswerLength(question));
        answers.add(this.getSecondAnswerLength(question));
        answers.add(this.getThirdAnswerLength(question));
        Collections.sort(answers);
        int shortAnswer = answers.get(0);
        int mediumAnswer = answers.get(1);
        int longAnswer = answers.get(2);

        if (this.correctAnswer == CorrectAnswer.FIRST) {
            return CorrectAnswerLength.SHORT;
        } else if(this.correctAnswer == CorrectAnswer.FIRST) {
            return CorrectAnswerLength.MEDIUM;
        } else if (this.correctAnswer == CorrectAnswer.FIRST) {
            return CorrectAnswerLength.LONG;
        }
        if (correct == shortAnswer) {
            return CorrectAnswerLength.SHORT;
        } else if(correct == mediumAnswer) {
            return CorrectAnswerLength.MEDIUM;
        } else if (correct == longAnswer) {
            return CorrectAnswerLength.LONG;
        }else {
            throw new InvalidArgumentException(new String[]{"Can not determine which answer is correct.", String.valueOf(first), String.valueOf(second), String.valueOf(third), String.valueOf(correct)});
        }
    }


    public int getShortestAnswerLength() {
        return Math.min(Math.min(this.getFirstAnswerLength(), this.getSecondAnswerLength()), this.getThirdAnswerLength());
    }

    public int getMediumAnswerLength() throws Exception {
        List<Integer> answers = new ArrayList<Integer>();
        answers.add(this.getFirstAnswerLength());
        answers.add(this.getSecondAnswerLength());
        answers.add(this.getThirdAnswerLength());
        answers.remove(answers.indexOf(this.getLongestAnswerLength()));
        answers.remove(answers.indexOf(this.getShortestAnswerLength()));
        if (answers.size() != 1) {
            throw new Exception("wrong sizes");
        } else {
            return answers.get(0);
        }
    }

    public int getLongestAnswerLength(Question question) {
        return Math.max(Math.max(this.getFirstAnswerLength(question), this.getSecondAnswerLength(question)), this.getThirdAnswerLength(question));
    }

}
