import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    final static String dir = "data";
    final static String fileName = "contacts.txt";

    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        Path path = Paths.get(dir, fileName);
        Path direc = Paths.get(dir);

        //define the list
        List<String> list = null;
        ArrayList <String> names = new ArrayList<>();
        ArrayList <String> phone = new ArrayList<>();
        ArrayList<String> compare = new ArrayList<>();



        if (!Files.exists(path)){
            try {
                Files.createDirectories(direc);
                Files.createFile(path);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }

        }
        else {
            try {
                list = Files.readAllLines(path);

                for (String eachline : list){
                    String [] each = eachline.split(": ");
                    names.add(each[0]);
                    phone.add(each[1]);
                    compare.add(each[0].toLowerCase());
                }
                for (int i = 0; i < phone.size(); i++){
                    if (phone.get(i).length() == 7){
                        phone.set(i, phone.get(i).substring(0,3) + "-" + phone.get(i).substring(3));
                    }
                    else if (phone.get(i).length() == 10){
                        phone.set(i, phone.get(i).substring(0,3) + "-" + phone.get(i).substring(3, 6) + "-" + phone.get(i).substring(6));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int menu;
        System.out.println("Welcome to Contacts Manager");
        do {
            menu = appMenu();
            switch(menu){
                case 1:
                    System.out.println("         Name | Phone Number |");
                    System.out.println("----------------------------------");
                    for (int i = 0; i < names.size(); i++){
                        System.out.printf("%13s | %12s |\n", names.get(i), phone.get(i));
                    }
                    System.out.println("----------------------------------");
                    break;
                case 2:
                    String newName;
                    System.out.println("Enter the name of the new contact:");
                    newName = scanner.nextLine();
                    if (compare.contains(newName.toLowerCase())){
                        System.out.println("There's already a contact named " + names.get(compare.indexOf(newName)) +
                                ". Do you want to overwrite it?" );
                        if (yesNo()){
                            System.out.println("Enter the number that will replace: ");
                            String changeNumber = scanner.next();
                            phone.set(compare.indexOf(newName), changeNumber);
                        }
                    }
                    names.add(newName);
                    compare.add(newName);
                    String newNumber;
                    do {
                        System.out.println("Enter the number of the new contact:");
                        newNumber = scanner.next();
                        if (newNumber.length() != 7 && newNumber.length() != 10){
                            System.out.println("You need to input a correct phone number.");
                        }
                    } while (newNumber.length() != 7 && newNumber.length() != 10);
                    if (newNumber.length() == 7){
                        newNumber = newNumber.substring(0,3) + "-" + newNumber.substring(3);
                    }
                    else {
                        newNumber = newNumber.substring(0,3) + "-" + newNumber.substring(3, 6) + "-" + newNumber.substring(6);
                    }
                    phone.add(newNumber);
                    System.out.println("Contact is added.");
                    break;
                case 3:
                    String search;
                    System.out.println("Enter contact to search: ");
                    search = scanner.nextLine().toLowerCase();
                    if (compare.contains(search)){
                        System.out.println("Here is the contact information:");
                        System.out.println(names.get(compare.indexOf(search)) + ": " + phone.get(compare.indexOf(search)));
                    }
                    else {
                        System.out.println("That name was not found.");
                    }
                    break;
                case 4:
                    String delete;
                    System.out.println("Enter contact to delete: ");
                    delete = scanner.nextLine().toLowerCase();
                    if (compare.contains(delete)){
                        names.remove(names.get(compare.indexOf(delete)));
                        phone.remove(phone.get(compare.indexOf(delete)));
                        compare.remove(compare.get(compare.indexOf(delete)));
                    }
                    else {
                        System.out.println("That name was not found.");
                    }
                    break;
            }

        } while (menu != 5);
    }

    public static int appMenu(){
        System.out.println("\n1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        return getInt(1, 5);
    }

    public static int getInt(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        String check;
        System.out.printf("Enter integer value between %s and %s:\n", min, max);
        check = scanner.next();
        int number;
        try {
            number = Integer.parseInt(check);
            if (number < min || number > max) {
                System.out.println("That number is not in range.");
                number = getInt(min, max);
                return number;
            }
            else {
                return number;
            }
        } catch (NumberFormatException nfe){
            System.out.println("Wrong input. try again: ");
            return getInt(min, max);
        }
    }

    public static boolean yesNo(){
        Scanner scanner = new Scanner(System.in);
        String check;
        System.out.println("Enter yes (or y) for yes:");
        check = scanner.next();
        return check.equalsIgnoreCase("yes") || check.equalsIgnoreCase("y");
    }
}
