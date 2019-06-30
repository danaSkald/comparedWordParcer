package service;

/**
 *
 * @author Dana
 */
public class LengthComparator  implements  java.util.Comparator<String>{
   
    @Override
    public int compare(String str1, String str2) {
        return str2.length() -str1.length();
    }
}
