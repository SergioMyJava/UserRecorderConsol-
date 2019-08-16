import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRecorder {
    private BufferedReader readIt;
    private HashMap<String, String> fromTxt = new HashMap<String, String>();

    private void helloMethod() {
        System.out.println("If you want to add a user, press '1' .");
        System.out.println("If you want to see all users, press '2' .");
        System.out.println("if you want to delete the user press '3' .");
        System.out.println("If you want to change user settings, press '4' .");
        System.out.println("If you want to exit press 'Q' .");
    }

    public void run() throws IOException {
        boolean endWorking = false;
        readIt = new BufferedReader(new InputStreamReader(System.in));

        while (endWorking == false) {
            helloMethod();
            String nStr = readIt.readLine();
            if (nStr.equals("1")) {
                addUser();
                continue;
            }
            if (nStr.equals("2")) {
                getAllUsers();
                continue;
            }
            if (nStr.equals("3")) {
                System.out.println("Enter the name of the user you want to delete");
                String nameDel = readIt.readLine();
                deleteUser(nameDel);
                continue;
            }
            if (nStr.equals("4")) {

            }
            if (nStr.equals("Q")) {
                endWorking = true;
            } else {
                System.out.println("Enter the correct number: 1,2,3 or 4.");
                System.out.println();
                continue;
            }
        }
    }

    private void addUser() throws IOException {
        FileWriter writer = new FileWriter("users.txt", true);

        System.out.println("Enter the first name .");
        boolean nameRecorded = false;
        writer.write("\n" +"Name:");
        while (nameRecorded == false) {
            String name = readIt.readLine();
            Pattern pattern = Pattern.compile("[A-ZА-Я]{1}[a-zа-я]{1,}");
            Matcher matcher = pattern.matcher(name);
            if (matcher.matches()) {
                writer.write(name + "|");
                nameRecorded = true;
            } else {
                System.out.println("Name must consist of letters, and begin with a capital letter.");
                continue;
            }
        }

        System.out.println("Enter the surname .");
        boolean surnameRecorded = false;
        writer.write("LastName:");
        while (surnameRecorded == false) {
            String surname = readIt.readLine();
            Pattern pattern = Pattern.compile("[A-ZА-Я]{1}[a-zа-я]{1,}");
            Matcher matcher = pattern.matcher(surname);
            if (matcher.matches()) {
                writer.write(surname + "|");
                surnameRecorded = true;
            } else {
                System.out.println("Surname must consist of letters, and begin with a capital letter.");
                continue;
            }
        }

        Boolean numberRecorded = false;
        int numberOfPhonesRecorded = 0;
        writer.write("PhoneNumber:[");
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
            if (numberOfPhonesRecorded < 3) {
                System.out.println("You can enter " + (3 - numberOfPhonesRecorded) + " more numbers. Y/N");
                String unswer = readIt.readLine();
                if (unswer.equals("Y") && numberOfPhonesRecorded < 3) {
                    continue;
                } else {
                    writer.write("]");
                    break;
                }
            }
            else {
                writer.write("]");
                break;
            }
        }

        System.out.println("Enter email");
        boolean mailRightAdd = false;
        writer.write("Email:");
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
        writer.write("Role:[");
        while (roleAdd == false || rolesAdded < 3) {
            System.out.println("Enter role");
            String role = readIt.readLine();
            writer.write(role + "|");
            roleAdd = true;
            rolesAdded++;
            if(rolesAdded<3) {
                System.out.println("You can add"+(3-rolesAdded) +" role? Y/N");
                String answer = readIt.readLine();
                if (answer.equals("Y")) {
                    continue;
                } else {
                    writer.write("]");
                    break;
                }
            }
            else{
                writer.write("]");
                break;
            }
        }
        System.out.println("All user parameters are filled in. The user is added to the list.");
        writer.close();
        System.out.println();
        return;
    }

    private void getAllUsers() throws FileNotFoundException {
        fromTxtToMap();
        for (Map.Entry entry : fromTxt.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println();
    }

    private void fromTxtToMap() throws FileNotFoundException {
        FileReader reader = new FileReader("users.txt");
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] charLine = line.toCharArray();
            boolean firstLimit = false;
            for (int i = 0; i < charLine.length; i++) {
                if (charLine[i] == '|' && firstLimit == false) {
                    String name = line.substring(0, i);
                    String otherInformation = line.substring(i, charLine.length);
                    firstLimit = true;
                    fromTxt.put(name, otherInformation);
                }
            }
        }
    }

    private void deleteUser(String nameDel) throws IOException {
        fromTxtToMap();
        if (fromTxt.containsKey(nameDel)) {
            fromTxt.remove(nameDel);
            clearTxt();
            rightMapToTxt();
            System.out.println(nameDel + " deleted .");
            System.out.println();
        }
    }

    private void rightMapToTxt() throws IOException {
        clearTxt();
        FileWriter writer = new FileWriter("users.txt", true);
        for (Map.Entry entry : fromTxt.entrySet()) {
            writer.write("\n" + entry.getKey());
            writer.write((String) entry.getValue());
        }
        writer.close();
    }

    private void clearTxt() throws IOException {
        FileWriter clear = new FileWriter("users.txt");
        clear.write("");
        clear.close();
    }
}