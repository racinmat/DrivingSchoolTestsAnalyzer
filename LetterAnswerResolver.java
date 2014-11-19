/**
 * Created by Azathoth on 19. 11. 2014.
 */
public class LetterAnswerResolver extends BaseAnswerResolver {

    @Override
    public int getLength(String string) {
        String stringWithoutSpaces = string.replace(" ", "");
        return stringWithoutSpaces.length();
    }
}
