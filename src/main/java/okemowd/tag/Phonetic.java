package okemowd.tag;

import java.util.List;

// use kuromoji to get katakana from kanji
import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;
import org.atilika.kuromoji.Tokenizer.Mode;

// use icu4j to get hiragana from katakana
import com.ibm.icu.text.Transliterator;

/**
 * This class offers functions about Kanji. <br>
 * You can use this class several times and there is initialization only first time.
 */
public class Phonetic{
  
  static private boolean isAlreadySetup = false; 
  static private Tokenizer tokenizer;
  static private Transliterator transliterator;
  
  /**
   * Get kana(hiragana) from string which contains kanji.
   * @param base string which contains kanji
   * @return hiragana
   */
  static public String getKana(String base){
    // build only first time
    if(!isAlreadySetup){
      Builder builder = Tokenizer.builder();
      builder.mode(Mode.NORMAL);
      tokenizer = builder.build();
      transliterator = Transliterator.getInstance("Katakana-Hiragana");
      isAlreadySetup = true;
    }
    
    // get tokens
    List<Token> tokens = tokenizer.tokenize(base);

    // create string with kana
    StringBuilder returnValue = new StringBuilder();
    for(Token token : tokens)
      returnValue.append(token.getReading());
    return transliterator.transliterate(returnValue.toString());//trans kana to gana
  }  
}
