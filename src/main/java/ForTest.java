import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ForTest {
        public HashMap<String,String> fromTxt = new HashMap<String, String>();

    public void fromTxtToHashMap() throws FileNotFoundException {
        FileReader reader = new FileReader("users.txt");
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            char[] charLine = line.toCharArray();
            boolean firstLimit = false;
                for(int i = 0;i<charLine.length;i++){
                    if(charLine[i] == '|' && firstLimit == false){
                        String name = line.substring(0,i-1);
                        String otherInformation = line.substring(i,charLine.length);
                        firstLimit = true;
                        fromTxt.put(name,otherInformation);
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
