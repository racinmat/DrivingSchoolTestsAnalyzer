import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by Azathoth on 19. 11. 2014.
 */
public interface IAnswerResolver {

    public CorrectAnswerLength getCorrectAnswer(Question question) throws InvalidArgumentException;

    public boolean haveAllAnswersSameLength(Question question);

    public boolean isCorrectAnswerAsLongAsAnother(Question question) throws InvalidPropertiesFormatException;

    public int getFirstAnswerLength(Question question);

    public int getSecondAnswerLength(Question question);

    public int getThirdAnswerLength(Question question);

    public int getShortestAnswerLength(Question question);

    public int getMediumAnswerLength(Question question);

    public int getLongestAnswerLength(Question question);

}
