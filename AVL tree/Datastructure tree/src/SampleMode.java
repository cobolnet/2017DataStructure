import java.util.Scanner;

public class SampleMode {

    BinarySearchTree mytree = new BinarySearchTree();
    Scanner input = new Scanner(System.in);

    int Menu() {
        int ch,dep;
        System.out.println("0. quit");
        System.out.println("1. Enter Information");
        System.out.println("2. Browse");
        System.out.println("3. Delete");
        System.out.println(mytree.records_count + " records in table");
        dep = mytree.depth(mytree.root);
        System.out.println("tree depth " + dep);
        System.out.println();
        do{
            System.out.print("Enter number of your choice: ");
            ch = input.next().charAt(0);
        } while (ch < '0' || ch > '3');
        return (ch - '0');
    }

    void Enter() {
        String[] temp = new String[1];
        char ch;
        mytree.fields_name[0] = "sample";

        for(;;){
            System.out.println("field size: " + mytree.fields_count);
            System.out.println("record size: " + mytree.records_count);
            for(int j = 0;j < 1 ;j++){
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

    void Delete() {
        System.out.print("Enter data to delete Record "+ mytree.fields_name[0] +": ");
        String search = input.next();
        mytree.delete(search);
        mytree.records_count--;
    }


}
