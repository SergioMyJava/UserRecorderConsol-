import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRecorder {
    private BufferedReader readIt;
    private HashMap<String, User> fromTxt = new HashMap();

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
                System.out.println();
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
        writer.write("Name:");
        while (nameRecorded == false) {
            String name = readIt.readLine();
            Pattern pattern = Pattern.compile("[A-ZА-Я]{1}[a-zа-я]{1,}");
            Matcher matcher = pattern.matcher(name);
            if (matcher.matches()) {
                writer.write(name + "|");
                nameRecorded = true;
            } else {
                System.out.println("Name must consist of letters, and begin with a capital letter.Try again.");
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
                System.out.println("Surname must consist of letters, and begin with a capital letter.Try again.");
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
                System.out.println("You entered the phone number incorrectly. Check that after the code there is a space 375 ** space *******.Try again . ");
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
                System.out.println("Not correct mail. Example '****@****.***' .Try again.");
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
                System.out.println("You can add "+(3-rolesAdded) +" role? Y/N");
                String answer = readIt.readLine();
                if (answer.equals("Y")) {
                    continue;
                } else {
                    writer.write("]"+"\n" );
                    break;
                }
            }
            else{
                writer.write("]"+"\n" );
                break;
            }
        }
        System.out.println("All user parameters are filled in. The user is added to the list.");
        writer.close();
        System.out.println();
        return;
    }
    private void getAllUsers() throws FileNotFoundException {
        fromTxtToHashMap();
        for(User user : fromTxt.values()){
            System.out.println(user.toString());
        }
    }

    public void fromTxtToHashMap() throws FileNotFoundException {
        FileReader reader = new FileReader("users.txt");
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String lineFromTxt = scanner.nextLine();
            User notYetAdded = workWithLine(lineFromTxt);
            String key = notYetAdded.getName() + " " + notYetAdded.getLastName();
            fromTxt.put(key,notYetAdded);
        }
    }

    public User workWithLine(String line){
        User newUser = null;
        String name = null;
        String lastName = null;
        List<String> phoneNumberList = new LinkedList();
        String mail = null;
        List<String> rolList = new LinkedList();
        int step =1;
        String workingLine = line;
        while (line.length()>2){
            if(step == 1){
                name = takeNecessary(workingLine,':','|');
                workingLine = takeUnnecessary(workingLine,'|');
                step++;
                continue;
            }
            if(step == 2){
                lastName = takeNecessary(workingLine,':','|');
                workingLine = takeUnnecessary(workingLine,'|');
                step++;
                continue;
            }
            if(step == 3){
                String allPhoneFromWorkList = takeNecessary(workingLine,'[',']');
                phoneNumberList = pickList(allPhoneFromWorkList,phoneNumberList);
                workingLine = takeUnnecessary(workingLine,']');
                step++;
                continue;
            }
            if(step == 4){
                mail = takeNecessary(workingLine,':','|');
                workingLine = takeUnnecessary(workingLine,'|');
                step++;
                continue;
            }
            if(step == 5){
                String allRoleFromWorkList = takeNecessary(workingLine,'[',']');
                rolList = pickList(allRoleFromWorkList,rolList);
                workingLine = takeUnnecessary(workingLine,']');
                step++;
                continue;
            }
            if(workingLine.length() == 0){
                break;
            }
        }
        if(workingLine.length() == 0)
            newUser = new User(name,lastName,phoneNumberList,mail,rolList);
        return newUser;
    }

    public String takeNecessary(String line,char first,char next){
        int indexColon = line.indexOf(first);
        int indexStick = line.indexOf(next);
        String nextString = line.substring(indexColon+1,indexStick);
        return nextString;
    }

    public String takeUnnecessary(String line,char unit){
        String workingLine = line.substring(line.indexOf(unit)+1);
        return workingLine;
    }

    public List pickList(String line,List list){
        List listIn = list;
        String lineIn = line;
        while (lineIn.length() != 0){
            String number = lineIn.substring(0,lineIn.indexOf('|'));
            listIn.add(number);
            lineIn = lineIn.substring(lineIn.indexOf('|')+1);

            if(lineIn.length() == 0){
                return listIn;
            }
        }
        return listIn;
    }

    private void deleteUser(String nameDel) throws IOException {
        fromTxtToHashMap();
        if (fromTxt.containsKey(nameDel)) {
            fromTxt.remove(nameDel);
            rightMapToTxt();
            System.out.println(nameDel + " deleted .");
            System.out.println();
        }
        else{
            System.out.println("The name is entered incorrectly or this name does not exist.");
        }
    }

            private void rightMapToTxt() throws IOException {
        clearTxt();
        FileWriter writer = new FileWriter("users.txt", true);
        for (Map.Entry entry : fromTxt.entrySet()) {
            User fromMap = (User) entry.getValue();
            String name = fromMap.getName();
            String lastName = fromMap.getLastName();
            String phone = getStringFromList(fromMap.getPhoneNumber());
            String mail = fromMap.getMail();
            String rol = getStringFromList(fromMap.getRol());
            writer.write("Name:" + name + "|" +" LastName:" + lastName + "|" + " PhoneNumber:" + phone +
                    " Mail:" + mail + "|" + " Role:" + rol + "\n" );
        }
        writer.close();
    }

    private void clearTxt() throws IOException {
        FileWriter clear = new FileWriter("users.txt");
        clear.write("");
        clear.close();
    }

    public String getStringFromList(List<String> list){
        String stringForReturn = "[";
        for(String a : list){
            stringForReturn += a + "|";
        }
        return stringForReturn + "]";
    }
}