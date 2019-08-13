import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {
    public  static void main (String[] args){
        Pattern pattern = Pattern.compile("375[0-9]{2}[\\s][0-9]{7}");
        Matcher matcher = pattern.matcher("37544 1234567");
        boolean matches = matcher.matches();
        System.out.println(matches);
    }
}
