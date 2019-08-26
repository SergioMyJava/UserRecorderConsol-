import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRecorder {
    private BufferedReader readIt;
    private Map<String, User> database = new HashMap();

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
                System.out.println("Enter the name of the user you want to make changes");
                String mustChange = readIt.readLine();
                changeUser(mustChange);
                continue;
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

            if (checkPhoneValidity(telNumber)) {
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
            } else {
                writer.write("]");
                break;
            }
        }

        System.out.println("Enter email");
        boolean mailRightAdd = false;
        writer.write("Email:");
        while (mailRightAdd == false) {
            String mail = readIt.readLine();
            if (checkMailValidity(mail)) {
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
            if (rolesAdded < 3) {
                System.out.println("You can add " + (3 - rolesAdded) + " role? Y/N");
                String answer = readIt.readLine();
                if (answer.equals("Y")) {
                    continue;
                } else {
                    writer.write("]" + "\n");
                    break;
                }
            } else {
                writer.write("]" + "\n");
                break;
            }
        }
        System.out.println("All user parameters are filled in. The user is added to the list.");
        writer.close();
        System.out.println();
        return;
    }

    private boolean checkPhoneValidity(String phoneNumber) {
        Pattern pattern = Pattern.compile("375[0-9]{2}[\\s][0-9]{7}");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private boolean checkMailValidity(String mail) {
        Pattern pattern = Pattern.compile("([a-zA-Z_0-9]{1,})+@([a-zA-Z_0-9]{1,}+\\.[a-z]{2,4})");
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    private void getAllUsers() throws FileNotFoundException {
        fromTxtToHashMap();
        for (User user : database.values()) {
            System.out.println(user.toString());
        }
    }

    private void fromTxtToHashMap() throws FileNotFoundException {
        FileReader reader = new FileReader("users.txt");
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String lineFromTxt = scanner.nextLine();
            User notYetAdded = workWithLine(lineFromTxt);
            String key = notYetAdded.getName() + " " + notYetAdded.getLastName();
            database.put(key, notYetAdded);
        }
    }

    private User workWithLine(String line) {
        User newUser = null;
        String name = null;
        String lastName = null;
        List<String> phoneNumberList = new LinkedList();
        String mail = null;
        List<String> rolList = new LinkedList();
        int step = 1;
        String workingLine = line;
        while (line.length() > 2) {
            if (step == 1) {
                name = takeNecessary(workingLine, ':', '|');
                workingLine = takeUnnecessary(workingLine, '|');
                step++;
                continue;
            }
            if (step == 2) {
                lastName = takeNecessary(workingLine, ':', '|');
                workingLine = takeUnnecessary(workingLine, '|');
                step++;
                continue;
            }
            if (step == 3) {
                String allPhoneFromWorkList = takeNecessary(workingLine, '[', ']');
                phoneNumberList = pickList(allPhoneFromWorkList, phoneNumberList);
                workingLine = takeUnnecessary(workingLine, ']');
                step++;
                continue;
            }
            if (step == 4) {
                mail = takeNecessary(workingLine, ':', '|');
                workingLine = takeUnnecessary(workingLine, '|');
                step++;
                continue;
            }
            if (step == 5) {
                String allRoleFromWorkList = takeNecessary(workingLine, '[', ']');
                rolList = pickList(allRoleFromWorkList, rolList);
                workingLine = takeUnnecessary(workingLine, ']');
                step++;
                continue;
            }
            if (workingLine.length() == 0) {
                break;
            }
        }
        if (workingLine.length() == 0)
            newUser = new User(name, lastName, phoneNumberList, mail, rolList);
        return newUser;
    }

    private String takeNecessary(String line, char first, char next) {
        int indexColon = line.indexOf(first);
        int indexStick = line.indexOf(next);
        String nextString = line.substring(indexColon + 1, indexStick);
        return nextString;
    }

    private String takeUnnecessary(String line, char unit) {
        String workingLine = line.substring(line.indexOf(unit) + 1);
        return workingLine;
    }

    private List pickList(String line, List list) {
        List listIn = list;
        String lineIn = line;
        while (lineIn.length() != 0) {
            String number = lineIn.substring(0, lineIn.indexOf('|'));
            listIn.add(number);
            lineIn = lineIn.substring(lineIn.indexOf('|') + 1);

            if (lineIn.length() == 0) {
                return listIn;
            }
        }
        return listIn;
    }

    private void deleteUser(String nameDel) throws IOException {
        fromTxtToHashMap();
        if (database.containsKey(nameDel)) {
            database.remove(nameDel);
            rightMapToTxt();
            System.out.println(nameDel + " deleted .");
            System.out.println();
        } else {
            System.out.println("The name is entered incorrectly or this name does not exist.");
        }
    }

    private void rightMapToTxt() throws IOException {
        clearTxt();
        FileWriter writer = new FileWriter("users.txt", true);
        for (Map.Entry entry : database.entrySet()) {
            User fromMap = (User) entry.getValue();
            String name = fromMap.getName();
            String lastName = fromMap.getLastName();
            String phone = getStringFromList(fromMap.getPhoneNumber());
            String mail = fromMap.getMail();
            String rol = getStringFromList(fromMap.getRole());
            writer.write("Name:" + name + "|" + " LastName:" + lastName + "|" + " PhoneNumber:" + phone +
                    " Mail:" + mail + "|" + " Role:" + rol + "\n");
        }
        writer.close();
    }

    private void clearTxt() throws IOException {
        FileWriter clear = new FileWriter("users.txt");
        clear.write("");
        clear.close();
    }

    private String getStringFromList(List<String> list) {
        String stringForReturn = "[";
        for (String a : list) {
            stringForReturn += a + "|";
        }
        return stringForReturn + "]";
    }

    private void changeUser(String mustChangeName) throws IOException {
        boolean endWorking = false;
        fromTxtToHashMap();
        if (database.containsKey(mustChangeName)) {
            while (endWorking == false) {
                System.out.println("what do you want to change ?");
                System.out.println("If Name, push - 1.");
                System.out.println("If LastName, push - 2.");
                System.out.println("If PhoneNumber push - 3.");
                System.out.println("If Mail push - 4.");
                System.out.println("If Role push - 5.");
                System.out.println("If exit push - Q.");
                User userForChange = database.get(mustChangeName);
                String answer = readIt.readLine();
                if (answer.equals("1")) {
                    userForChange = changeName(userForChange);
                    continue;
                }
                if (answer.equals("2")) {
                    userForChange = changeLastName(userForChange);
                    continue;
                }
                if (answer.equals("3")) {
                    userForChange = changePhoneNumber(userForChange);
                    continue;
                }
                if (answer.equals("4")) {
                    userForChange = changeMail(userForChange);
                    continue;

                }
                if (answer.equals("5")) {
                    userForChange = changeRole(userForChange);
                }
                if (answer.equals("Q")) {
                    clearTxt();
                    rightMapToTxt();
                    break;
                }
            }
        } else {
            System.out.println("The name is entered incorrectly or this name does not exist.");
        }
    }

    private User changeName(User userForChange) throws IOException {
        System.out.println("Enter new name.");
        User toBeChanged = userForChange;
        Boolean nameChanged = false;
        while (nameChanged == false) {
            String newName = readIt.readLine();
            Pattern pattern = Pattern.compile("[A-ZА-Я]{1}[a-zа-я]{1,}");
            Matcher matcher = pattern.matcher(newName);
            if (matcher.matches()) {
                toBeChanged.setName(newName);
                nameChanged = true;
                System.out.println("Name changed.");
                System.out.println();
                return toBeChanged;
            } else {
                System.out.println("Name must consist of letters, and begin with a capital letter.Try again.");
                continue;
            }
        }
        return toBeChanged;
    }

    private User changeLastName(User userForChange) throws IOException {
        System.out.println("Enter new LastName.");
        User toBeChanged = userForChange;
        Boolean lastNameChanged = false;
        while (lastNameChanged == false) {
            String newLastName = readIt.readLine();
            Pattern pattern = Pattern.compile("[A-ZА-Я]{1}[a-zа-я]{1,}");
            Matcher matcher = pattern.matcher(newLastName);
            if (matcher.matches()) {
                toBeChanged.setLastName(newLastName);
                lastNameChanged = true;
                System.out.println("LastName changed.");
                System.out.println();
                return toBeChanged;
            } else {
                System.out.println("LastName must consist of letters, and begin with a capital letter.Try again.");
                continue;
            }
        }
        return toBeChanged;
    }

    private User changePhoneNumber(User userForChange) throws IOException {
        User toBeChanged = userForChange;
        Boolean changePhoneNumber = false;
        while (changePhoneNumber == false) {
            System.out.println("All numbers that this user has:" + userForChange.getPhoneNumber().toString());
            System.out.println("What number do you want to change, the first, second or third? Enter the number 1,2 or 3.\n" +
                    "If you want to add a number, press 4. If you want to exit press 5.");
            String answer = readIt.readLine();
            Pattern pattern = Pattern.compile("[1-4]{1}");
            Matcher matcher = pattern.matcher(answer);
            List<String> phoneFromMap = userForChange.getPhoneNumber();
            if (matcher.matches()) {
                int enteredNumber = Integer.parseInt(answer);

                if (enteredNumber == 5) {
                    break;
                }
                if (enteredNumber == 4) {
                    if (phoneFromMap.size() < 3) {
                        System.out.println("Enter phone number.");
                        String newPhoneNumber = readIt.readLine();
                        phoneFromMap.add(newPhoneNumber);
                        toBeChanged.setPhoneNumber(phoneFromMap);
                        System.out.println("Phone number added.");
                        System.out.println();
                        return toBeChanged;
                    } else {
                        System.out.println("You can enter only three numbers.");
                        continue;
                    }
                }

                if (enteredNumber <= phoneFromMap.size()) {
                    System.out.println("Enter phone number.");
                    String newPhoneNumber = readIt.readLine();
                    if (checkPhoneValidity(newPhoneNumber)) {
                        phoneFromMap.set(enteredNumber - 1, newPhoneNumber);
                        toBeChanged.setPhoneNumber(phoneFromMap);
                        System.out.println("Phone number is added.");
                        System.out.println();
                        return toBeChanged;
                    } else {
                        System.out.println("You entered the phone number incorrectly. Check that after the code there is a space 375 ** space *******.Try again . ");
                        continue;
                    }
                } else {
                    System.out.println("You enter a number larger than the phones listed. If you want to add then press 4.");
                }
            } else {
                System.out.println("You entered an invalid number.Enter from 1 to 4.");
                continue;
            }
        }
        return toBeChanged;
    }

    private User changeMail(User userForChange) throws IOException {
        User toBeChanged = userForChange;
        boolean mailChanged = false;
        while (mailChanged == false) {
            System.out.println("Your old mail:" + toBeChanged.getMail() + ". If you want exit press Q");
            System.out.println("Enter new email.");
            String newMail = readIt.readLine();

            if (newMail.equals("Q")) {
                break;
            }

            if (checkMailValidity(newMail)) {
                toBeChanged.setMail(newMail);
                mailChanged = true;
                System.out.println("Mail changed.");
                System.out.println();
                return toBeChanged;
            } else {
                System.out.println("Not correct mail. Example '****@****.***' .Try again.");
                continue;
            }
        }
        return toBeChanged;
    }

    private User changeRole(User userForChange) throws IOException {
        User toBeChanged = userForChange;
        Boolean changeRole = false;
        while (changeRole == false) {
            System.out.println("All role that this user has:" + userForChange.getRole().toString());
            System.out.println("Role under what number do you want to change, the first, second or third? Enter the number 1,2 or 3.\n" +
                    "If you want to add a role, press 4. If you want to exit press 5.");
            String answer = readIt.readLine();
            Pattern pattern = Pattern.compile("[1-4]{1}");
            Matcher matcher = pattern.matcher(answer);
            List<String> roleFromMap = userForChange.getRole();
            if (matcher.matches()) {
                int enteredNumber = Integer.parseInt(answer);

                if (enteredNumber == 5) {
                    break;
                }
                if (enteredNumber == 4) {
                    if (roleFromMap.size() < 3) {
                        System.out.println("Enter the role.");
                        String newRole = readIt.readLine();
                        roleFromMap.add(newRole);
                        toBeChanged.setRole(roleFromMap);
                        System.out.println("Role added.");
                        System.out.println();
                        return toBeChanged;
                    } else {
                        System.out.println("You can enter only three roles.");
                        continue;
                    }
                }

                if (enteredNumber <= roleFromMap.size()) {
                    System.out.println("Enter the role.");
                    String newRole = readIt.readLine();

                    roleFromMap.set(enteredNumber - 1, newRole);
                    toBeChanged.setRole(roleFromMap);
                    System.out.println("New role is added.");
                    System.out.println();
                    return toBeChanged;
                } else {
                    System.out.println("You enter a number larger than the role listed. If you want to add then press 4.");
                }
            } else {
                System.out.println("You entered an invalid number.Enter from 1 to 4.");
                continue;
            }
        }
        return toBeChanged;
    }
}