import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ForTest {
        public HashMap<String,User> fromTxt = new HashMap<String, User>();

    public void fromTxtToHashMap() throws FileNotFoundException {
        FileReader reader = new FileReader("users.txt");
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String name = null;
            String lastName = null;
            List<String> phoneNumber = new LinkedList<String>();
            String mail = null;
            List<String> rol = new LinkedList<String>();
            String line = scanner.nextLine();
            char[] charLine = line.toCharArray();
            int colon = 0;
            int passageNumber = 0;
            for (int i = 0; i < charLine.length; i++) {

                if (charLine[i] == ':') {
                    colon = i+1;
                }

                if (charLine[i] == '|' && passageNumber == 0) {
                    name = line.substring(colon, i - 1);
                    passageNumber++;
                    continue;
                }
                if (charLine[i] == '|' && passageNumber == 1) {
                    lastName = line.substring(colon, i - 1);
                    passageNumber++;
                    continue;
                }

                if (charLine[i] == '[' && passageNumber == 2) {
                    for (int y = i; y < charLine.length; y++) {

                        if (charLine[y] == '|') {
                            String number = line.substring(y - 13, y );
                            phoneNumber.add(number);
                        }
                        if (charLine[y] == ']') {
                            break;
                        }
                    }
                    passageNumber++;
                    continue;
                }

                if (charLine[i] == '|' && passageNumber == 3) {
                    mail = line.substring(colon, i - 1);
                    passageNumber++;
                    continue;
                }

                if (charLine[i] == '[' && passageNumber == 4) {
                    for (int y = i; y < charLine.length; y++) {

                        if (charLine[y] == '|') {
                            for (int j = y; j < charLine.length; j++) {
                                if (charLine[j] == '|') {
                                    String role = line.substring(y + 1, j - 1);
                                    rol.add(role);
                                }

                            }
                            if (charLine[y] == ']') {
                                break;
                            }
                        }
                        if (charLine[y] == ']') {
                            break;
                        }
                    }
                    if (charLine[i] == ']') {
                        break;
                    }

                }
            }
            User newUser = new User(name,lastName,phoneNumber,mail,rol);

            fromTxt.put(name+" "+lastName,newUser);
        }
    }
    public void rightFromMapToTXT(){

        for (Map.Entry entry : fromTxt.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: "
                    + entry.getValue());
        }
    }
}

