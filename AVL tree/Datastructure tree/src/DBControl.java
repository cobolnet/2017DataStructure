import java.util.Scanner;

public class DBControl {

    BinarySearchTree mytree = new BinarySearchTree();
    Scanner input = new Scanner(System.in);


    /**Prompt the user for selection**/
    int Menu() {
        int ch;
        System.out.println("0. quit");
        System.out.println("1. Define DataBase");
        System.out.println("2. Enter Information");
        System.out.println("3. Browse");
        System.out.println("4. Search_Modify");
        System.out.println("5. Delete");
        System.out.println(mytree.records_count + " records in table");
        do{
            System.out.print("Enter number of your choice: ");
            ch = input.next().charAt(0);
        } while (ch < '0' || ch > '5');
        return (ch - '0');
    }

    void Define() {
        char ch;
        int i;

        do {
            System.out.print("number of fields(1~10) (0 to skip): ");
            mytree.fields_count = input.nextInt();
            if (mytree.fields_count == 0)
                return;
        } while (mytree.fields_count < 0 || mytree.fields_count > 10);

        mytree.fields_name = new String[mytree.fields_count];

        for (i = 0; i < mytree.fields_count; i++) {

            System.out.println("Enter name of field " + (i+1));
            mytree.fields_name[i] = input.next();
            System.out.print("Is this field defined OK? (Y/N): ");
            ch = input.next().toUpperCase().charAt(0);

            if (ch != 'Y')
                i--;
        }
    }

    void Enter() {
        String[] temp = new String[mytree.fields_count];
        char ch;

        for(;;){
            System.out.println("field size: " + mytree.fields_count);
            System.out.println("record size: " + mytree.records_count);
            for(int j = 0;j < mytree.fields_count ;j++){
                System.out.print(mytree.fields_name[j] +  " data: ");
                temp[j] = input.next();
                System.out.print("OK? Y or N: ");
                ch = input.next().toUpperCase().charAt(0);
                if (ch == 'Y')
                    continue;
                else
                    j--;
            }
            mytree.insert(temp[0], temp);
            mytree.records_count++;
            System.out.print("More? Y/N: ");
            ch = input.next().toUpperCase().charAt(0);
            if (ch == 'Y')
                continue;
            else
                break;
        }
    }

    void Browse() {
        mytree.browse();
    }

    void Search_Modify() {
        System.out.print("Enter data to search: ");
        String search = input.next();
        mytree.search(search);
    }

    void Delete() {
        System.out.print("Enter data to delete Record "+ mytree.fields_name[0] +": ");
        String search = input.next();
        mytree.delete(search);
        mytree.records_count--;
    }
}
