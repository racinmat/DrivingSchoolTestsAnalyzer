/**
 * Created by Azathoth on 19. 11. 2014.
 */
public class WordAnswerResolver extends BaseAnswerResolver {

    public int getLength(String text) {
        String[] words = text.split(" ");
        return words.length;
    }

}
