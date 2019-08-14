import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException {
//        System.out.println("Enter your name");
//        BufferedReader readIt = new BufferedReader (new InputStreamReader(System.in));
//        String nStr = readIt.readLine();
//        UserRecorder exp = new UserRecorder();
//        exp.writeName(nStr);
//
//        System.out.println("Enter your Last Name");
//        String lastNStr = readIt.readLine();
//        exp.writeSurename(lastNStr);
//
//        System.out.println("Enter your telephone number. Example : 375** ******* .");
//        Boolean needNumber = true;
//        int numberOfPhones = 0;
//        while (needNumber == true || numberOfPhones<3) {
//            String telNumber = readIt.readLine();
//            if (exp.writeNumberTel(telNumber)) {
//                numberOfPhones++;
//                int remainingNumber = 3 - numberOfPhones;
//                System.out.println("You can enter "+ remainingNumber +" more phone numbers. Y/N");
//                String unswer = readIt.readLine();
//                if (unswer == "Y") {
//                    continue;
//                }
//                else{
//                    needNumber = false;
//                }
//            } else {
//                System.out.println("You entered the phone number incorrectly. Check that after the code there is a space 375 ** space *******.");
//                continue;
//            }
//        }

        UserRecorder test = new UserRecorder();
        test.run();

    }
}
