import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Enter your name");
        BufferedReader readIt = new BufferedReader (new InputStreamReader(System.in));
        String nStr = readIt.readLine();
        UserRecorder exp = new UserRecorder();
        exp.writeName(nStr);

        System.out.println("Enter your Last Name");
        String lastNStr = readIt.readLine();
        exp.writeSurename(lastNStr);

        System.out.println("Enter your telephone number");
    }
}
