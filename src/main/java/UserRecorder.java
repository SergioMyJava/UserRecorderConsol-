import java.io.FileWriter;
import java.io.IOException;

public class UserRecorder {

    public void writeName(String name)throws IOException{
        FileWriter writer = new FileWriter("users.txt", true);
        writer.write(name + " ");
        writer.close();
    }

    public void writeSurename(String surName)throws IOException{
        FileWriter writer = new FileWriter("users.txt", true);
        writer.write(surName+ "\n");
        writer.close();
    }

    protected void wrietNumberTel(String telNum)throws IOException{
if
        FileWriter writer = new FileWriter("users.txt", true);
        writer.write(telNum);
        writer.close();
    }
}
