import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRecorder {
    private BufferedReader readIt;

    private void helloMethod(){
        System.out.println("If you want to add a user, press '1' .");
        System.out.println("If you want to see all users, press '2' .");
        System.out.println("if you want to delete the user press '3' .");
        System.out.println("If you want to change user settings, press '4' .");
        System.out.println("If you want to exit press 'Q' .");
    }

    public void run() throws IOException {
        boolean endWorking = false;
        helloMethod();
        readIt = new BufferedReader(new InputStreamReader(System.in));

        while (endWorking == false) {
            String nStr = readIt.readLine();
            if (nStr.equals("1")) {
                addUser();
            }
            if (nStr == "2") {

            }
            if (nStr == "3") {

            }
            if (nStr == "4") {

            }
            if (nStr == "Q") {
                endWorking = true;
            } else {
                System.out.println("Enter the correct number: 1,2,3 or 4.");
                System.out.println();
                helloMethod();
                continue;
            }
        }
    }

    private void addUser() throws IOException {
        FileWriter writer = new FileWriter("users.txt", true);

        System.out.println("Enter first and last name with a space");
        boolean nameRecorded = false;
        while (nameRecorded == false) {
            String nameLastName = readIt.readLine();
            Pattern pattern = Pattern.compile("[A-Za-z]{1,}\\s[A-Za-z]{1,}");
            Matcher matcher = pattern.matcher(nameLastName);
            if (matcher.matches()) {
                writer.write(nameLastName + " | ");
                nameRecorded = true;
            } else {
                System.out.println("Name and surname must consist of letters, and begin with a capital letter.");
                continue;
            }
        }


        Boolean numberRecorded = false;
        int numberOfPhonesRecorded = 0;
        while (numberRecorded == false || numberOfPhonesRecorded < 3) {
            System.out.println("Enter telephone number. Example : 375** ******* .");
            String telNumber = readIt.readLine();
            Pattern pattern = Pattern.compile("375[0-9]{2}[\\s][0-9]{7}");//("375 - первые три цифры "375",[0-9]{2} дальше идут две любые цифры,[\s] - дальше пробел,[0-9]{7} - семь любых цифр")
            Matcher matcher = pattern.matcher(telNumber);
            if (matcher.matches()) {
                writer.write(telNumber + "|");
                numberRecorded = true;
                numberOfPhonesRecorded++;
            } else {
                System.out.println("You entered the phone number incorrectly. Check that after the code there is a space 375 ** space *******.");
                continue;
            }

            System.out.println("You can enter " + (3 - numberOfPhonesRecorded) + " more numbers. Y/N");
            String unswer = readIt.readLine();
            if (unswer.equals("Y")) {
                continue;
            } else {
                break;
            }
        }

        System.out.println("Enter email");
        boolean mailRightAdd = false;
        while (mailRightAdd == false) {
            String mail = readIt.readLine();
            Pattern pattern = Pattern.compile("([a-zA-Z_0-9]{1,})+@([a-zA-Z_0-9]{1,}+\\.[a-z]{2,4})");//("375 - первые три цифры "375",[0-9]{2} дальше идут две любые цифры,[\s] - дальше пробел,[0-9]{7} - семь любых цифр")
            Matcher matcher = pattern.matcher(mail);
            if (matcher.matches()) {
                writer.write(mail + "|");
                mailRightAdd = true;
            } else {
                System.out.println("Not correct mail");
                continue;
            }
        }

        boolean roleAdd = false;
        int rolesAdded = 0;
        while (roleAdd == false || rolesAdded < 3) {
            System.out.println("Enter role");
            String role = readIt.readLine();
            writer.write(role + "|");
            roleAdd = true;
            rolesAdded++;
            System.out.println("Do you wont to added another role? Y/N");
            String answer = readIt.readLine();
            if(answer.equals("Y")){
                continue;
            }
            else{
                System.out.println("All user parameters are filled in. The user is added to the list.");
                writer.close();
                break;
            }
        }
        return;
    }
}