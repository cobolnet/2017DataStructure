import java.util.Scanner;

public class main {

    public static void main(String args[]) {

        DBControl mydb = new DBControl();
        Scanner input = new Scanner(System.in);

        int choice;
        System.out.print("SampleMode On? (0/1): ");
        choice = input.nextInt();

        if (choice == 1){
            SampleMode mytest = new SampleMode();
            do{
                choice = mytest.Menu();
                switch(choice) {
                    case 1:
                        mytest.Enter();
                        break;
                    case 2:
                        mytest.Browse();
                        break;
                    case 3:
                        mytest.Delete();
                        break;
                }
            } while(choice != 0);
        }

        do{
            choice = mydb.Menu();
            switch (choice) {
                case 1:
                    mydb.Define();
                    break;
                case 2:
                    mydb.Enter();
                    break;
                case 3:
                    mydb.Browse();
                    break;
                case 4:
                    mydb.Search_Modify();
                    break;
                case 5:
                    mydb.Delete();
                    break;
            }
        }while(choice != 0);

    }
}
