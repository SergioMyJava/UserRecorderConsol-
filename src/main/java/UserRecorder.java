import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRecorder {
int numberOfTelefone = 0;

    public void writeName(String name) throws IOException {
        FileWriter writer = new FileWriter("users.txt", true);
        writer.write(name + " ");
        writer.close();
    }

    public void writeSurename(String surName) throws IOException {
        FileWriter writer = new FileWriter("users.txt", true);
        writer.write(surName + "\n");
        writer.close();
    }

    protected boolean writeNumberTel(String telNum) throws IOException {
        Pattern pattern = Pattern.compile("375[0-9]{2}[\\s][0-9]{7}]");//("375 - первые три цифры "375",[0-9]{2} дальше идут две любые цифры,[\s] - дальше пробел,[0-9]{7} - семь любых цифр")
        Matcher matcher = pattern.matcher(telNum);
        if (matcher.matches()) {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(telNum);
            return true;
        } else {
            return false;
        }
    }


}
