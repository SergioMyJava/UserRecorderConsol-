import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRecorderSecond {
    private BufferedReader readIt;

    public void run()throws IOException {
        System.out.println("Good day!");
        System.out.println("If you want to add a user, press '1' .");
        System.out.println("If you want to see all users, press '2' .");
        System.out.println("if you want to delete the user press '3' .");
        System.out.println("If you want to change user settings, press '4' .");
        System.out.println("If you want to exit press 'Q' .");
        readIt = new BufferedReader (new InputStreamReader(System.in));
        String nStr = readIt.readLine();
        if(nStr == "1"){

        }
        if(nStr == "2"){

        }
        if(nStr == "3"){

        }
        if(nStr == "4"){

        }
        if(nStr == "Q"){
            System.exit(0);
        }
        else{
            System.out.println("Enter the correct number: 1,2,3 or 4.");
        }
    }

    private void addUser() throws IOException {
        System.out.println("Enter your first and last name");
        boolean nameRecorded = false;
        while (nameRecorded == false) {                                                         //запись имени
            String nameLastname = readIt.readLine();
            Pattern pattern = Pattern.compile("[A-Za-z][\\s][A-Za-z]");
            Matcher matcher = pattern.matcher(nameLastname);
            if (matcher.matches()) {
                FileWriter writer = new FileWriter("users.txt", true);
                writer.write(nameLastname);
                writer.close();
                nameRecorded = true;
            } else {
                System.out.println("Name and surname must consist of letters, and begin with a capital letter.");
                continue;
            }
        }

        System.out.println("Enter your telephone number. Example : 375** ******* .");
        Boolean numberRecorded = false;
        int numberOfPhonesRecorded = 0;
        while (numberRecorded == false || numberOfPhonesRecorded < 3) {
            String telNumber = readIt.readLine();
            Pattern pattern = Pattern.compile("375[0-9]{2}[\\s][0-9]{7}");//("375 - первые три цифры "375",[0-9]{2} дальше идут две любые цифры,[\s] - дальше пробел,[0-9]{7} - семь любых цифр")
            Matcher matcher = pattern.matcher(telNumber);
            if(matcher.matches()){
                FileWriter writer = new FileWriter("users.txt", true);
                writer.write(telNumber);
                writer.close();
            }
            else{
                System.out.println("You entered the phone number incorrectly. Check that after the code there is a space 375 ** space *******.");
                continue;
            }

            System.out.println("You can enter " +(3-numberOfPhonesRecorded) + " more numbers. Y/N");
            String unswer = readIt.readLine();
            if(unswer == "Y"){
                continue;
            }
            else{
                break;
            }
        }
    }
}
