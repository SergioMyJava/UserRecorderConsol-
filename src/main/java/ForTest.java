import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ForTest {
        public HashMap<String,String> fromTxt = new HashMap<String, String>();

    public void fromTxtToHashMap() throws FileNotFoundException {
        FileReader reader = new FileReader("users.txt");
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()){
            String name;
            String lastName;
            List<String> phoneNumber;
            String mail;
            List<String> rol;
            String line = scanner.nextLine();
            char[] charLine = line.toCharArray();
            int colon = 0;
            int passageNumber = 0;
            char[] cutCharline;
                for(int i = 0;i<charLine.length;i++){

                    if(charLine[i] == ':'){
                        colon = i;
                    }

                    if(charLine[i] == '|' && passageNumber == 0){
                        name = line.substring(colon,i-1);
                        passageNumber++;
                        continue;
                    }
                    if(charLine[i] == '|' && passageNumber == 1){
                        lastName = line.substring(colon,i-1);
                        passageNumber++;
                        continue;
                    }
                    if(charLine[i] == '[' && passageNumber == 2){
                        for() {
                            while (charLine[i] == ']') {

                            }
                        }
                    }
                }
        }
    }

    public void rightFromMapToTXT(){

        for (Map.Entry entry : fromTxt.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: "
                    + entry.getValue());
        }
    }
}
