import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azathoth on 19. 11. 2014.
 */
public class SmartWordsResolver extends BaseAnswerResolver {

    @Override
    public int getLength(String string) {
        String[] words = string.split(" ");
        List<String> longWords = new ArrayList<String>();
        for (String word : words) {
            if (word.length() > 2) {
                longWords.add(word);
            }
        }
        return longWords.size();
    }
}
