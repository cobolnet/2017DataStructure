import java.util.Scanner;

public class BinarySearchTree {

    public int fields_count = 1;

    public class Node {
        String[] data = new String[fields_count];
        Node Lchild;
        Node Rchild;

        public Node(String data) {
            this.data[0] = data;
            this.Lchild = null;
            this.Rchild = null;
        }
    }
    public int records_count = 0;


    String[] fields_name = new String[fields_count];
    Node root = null;

    /**Insert Node*/
    public void insert (String data, String[] sdata) {
        Node addNode = new Node(data);
        for (int i = 1; i < fields_count; i++){
            addNode.data[i] = sdata[i];
        }
        if (root == null) {
            root = addNode;
            return;
        }

        Node cur_node = root;

        int[] way = new int[depth(root) + 1];
        int count = 0;
        for (int i = 0; ; i++) {
            if (data.compareTo(cur_node.data[0]) < 0) { //smaller
                if (cur_node.Lchild == null) { //insert
                    cur_node.Lchild = addNode;
                    way[i] = -1;
                    count++;
                    break;
                } else { //increase level
                    cur_node = cur_node.Lchild;
                    way[i] = -1;
                    count++;
                }
            } else if (data.compareTo(cur_node.data[0]) > 0) { //bigger
                if (cur_node.Rchild == null) { //insert
                    cur_node.Rchild = addNode;
                    way[i] = 1;
                    count++;
                    break;
                } else { //increase level
                    cur_node = cur_node.Rchild;
                    way[i] = 1;
                    count++;
                }
            } else { //same data
                System.out.println("기준 필드에서 동일한 데이터를 가진 레코드가 존재합니다.");
                records_count--;
                break;
            }
        }/**End for*/

        balance(way, count);

    }

    public void balance (int[] way, int count) {
        int dep = depth(root); //balancing 하기 전 트리의 깊이
        if (dep < 3) //depth가 3보다 작으면 balance를 검사할 필요가 없음
            return;

        Node[] level = new Node[5]; //leaf node 부터 최대 5개의 parent node
        //level[0]가 최상단 level4]가 마지막 최근에 삽입된 노드
        Node temp = level[2];
        int k = 4;


        for (int i = count; i >= 0; i--) { //마지막 노드까지 삽입 경로 재진입
            temp = root;
            for (int j = 0; j < i; j++) {
                if (way[j] == -1)
                    temp = temp.Lchild;
                else
                    temp = temp.Rchild;
            }
            level[k] = temp;
            k--;
            if (k < 0)
                break;
        }

        if (level[3].Lchild != null || level[3].Rchild != null) {
            if (level[2].Lchild != null && level[2].Rchild != null) {
                if (dep > 3) {
                    if (way[count - 3] == -1) {
                        if (level[1].Rchild != null) {
                            if (level[1].Rchild.Lchild == null && level[1].Rchild.Rchild == null) {
                                if (way[count - 2] == -1) {
                                    level[1] = LLL_R(level[1]);
                                } else {
                                    if (way[count-1] == -1) {
                                        level[1] = LRL(level[1]);
                                    } else
                                        level[1] = LRR(level[1]);
                                }
                            }
                        }
                    } else {
                        if (level[1].Lchild != null) {
                            if (level[1].Lchild.Lchild == null && level[1].Lchild.Rchild == null) {
                                if (way[count - 2] == 1) {
                                    level[1] = RRR_L(level[1]);
                                } else {
                                    if (way[count - 1] == -1) {
                                        level[1] = RLL(level[1]);
                                    } else
                                        level[1] = RLR(level[1]);
                                }
                            }
                        }
                    }
                }
            } else {
                if (way[count - 2] == -1) {
                    if (way[count - 1] == -1) {
                        temp = LL(level[2]);
                        if (dep > 3) {
                            if (way[count - 3] == -1) {
                                level[1].Lchild = temp;
                            } else {
                                level[1].Rchild = temp;
                            }
                        }
                    } else {
                        temp = LR(level[2]);
                        if (dep > 3) {
                            if (way[count - 3] == -1) {
                                level[1].Lchild = temp;
                            } else {
                                level[1].Rchild = temp;
                            }
                        }
                    }
                } else if (way[count - 2] == 1) {
                    if (way[count - 1] == -1) {
                        temp = RL(level[2]);
                        if (dep > 3) {
                            if (way[count - 3] == -1) {
                                level[1].Lchild = temp;
                            } else {
                                level[1].Rchild = temp;
                            }
                        }
                    } else {
                        temp = RR(level[2]);
                        if (dep > 3) {
                            if (way[count - 3] == -1) {
                                level[1].Lchild = temp;
                            } else {
                                level[1].Rchild = temp;
                            }
                        }
                    }
                }
            }
        }
        if (dep == 3)
            root = temp;
        else if (dep == 4)
            root = level[1];
        else {
            if (way[count-4] == -1)
                level[0].Lchild = level[1];
            else
                level[0].Rchild = level[1];
        }
    }

    /**Rotation*/
    private Node LL(Node top){
        Node topLeft = top.Lchild;
        top.Lchild = null;
        topLeft.Rchild = top;
        return topLeft;
    }
    private Node LR(Node top){
        Node topLeft = top.Lchild;
        Node leaf = top.Lchild.Rchild;

        top.Lchild = null;
        topLeft.Rchild = null;

        leaf.Lchild = topLeft;
        leaf.Rchild = top;

        return leaf;
    }
    private Node RR(Node top){
        Node topRight = top.Rchild;
        top.Rchild = null;
        topRight.Lchild = top;
        return topRight;
    }
    private Node RL(Node top){
        Node topRight = top.Rchild;
        Node leaf = top.Rchild.Lchild;

        top.Rchild = null;
        topRight.Lchild = null;

        leaf.Rchild = topRight;
        leaf.Lchild = top;

        return leaf;
    }
    private Node LLL_R(Node top){
        Node topLeft = top.Lchild;
        Node movemid = top.Lchild.Rchild;

        top.Lchild.Rchild = null;
        top.Lchild = null;
        topLeft.Rchild = null;

        topLeft.Rchild = top;
        top.Lchild = movemid;

        return topLeft;
    }
    private Node LRL(Node top){
        Node topLeft = top.Lchild;
        Node mid = top.Lchild.Rchild;
        Node midleaf = top.Lchild.Rchild.Lchild;

        mid.Lchild = null;
        top.Lchild = null;
        topLeft.Rchild = null;

        midleaf.Lchild = topLeft;
        midleaf.Rchild = top;
        top.Lchild = mid;

        return midleaf;
    }
    private Node LRR(Node top){
        Node topLeft = top.Lchild;
        Node midleaf = top.Lchild.Rchild.Rchild;

        topLeft.Rchild.Rchild = null;
        top.Lchild = null;

        midleaf.Lchild = topLeft;
        midleaf.Rchild = top;

        return midleaf;
    }
    private Node RRR_L(Node top){
        Node topRight = top.Rchild;
        Node movemid = top.Rchild.Lchild;

        top.Rchild.Lchild = null;
        top.Rchild = null;
        topRight.Lchild = null;

        topRight.Lchild = top;
        top.Rchild = movemid;

        return topRight;
    }
    private Node RLR(Node top){
        Node topRight = top.Rchild;
        Node mid = top.Rchild.Lchild;
        Node midleaf = top.Rchild.Lchild.Rchild;

        mid.Rchild = null;
        topRight.Lchild = null;
        top.Rchild = null;

        midleaf.Rchild = topRight;
        midleaf.Lchild = top;
        top.Rchild = mid;

        return midleaf;
    }
    private Node RLL(Node top){
        Node topRight = top.Rchild;
        Node midleaf = top.Rchild.Lchild.Lchild;

        topRight.Lchild.Lchild = null;
        top.Rchild = null;

        midleaf.Rchild = topRight;
        midleaf.Lchild = top;

        return midleaf;
    }

    /**Delete Node*/
    public void delete (String data){
        Node search_node = root;
        Node parent_node = root;
        Node temp_node = null;
        int parent_child = 0;
        int child_count = 0;

        /*******************************************************************/
        /**parent_child == 0: to delete node is root************************/
        /**parent_child == 1: parent`s to delete child node is right child**/
        /**parent_child == -1: parent`s to delete child node is left child**/
        /**child_count == 0: to delete node does not have any child*********/
        /**child_count == 1: to delete node has right child*****************/
        /**child_count == -1: to delete node has left child*****************/
        /**child_count == 2: to delete node has both children***************/
        /*******************************************************************/
        for (;;){
            if (data.compareTo(search_node.data[0]) < 0) {
                parent_node = search_node;
                search_node = search_node.Lchild;
                parent_child = -1;
            } else if (data.compareTo(search_node.data[0]) > 0) {
                parent_node = search_node;
                search_node = search_node.Rchild;
                parent_child = 1;
            } else {
                if (search_node.Lchild == null && search_node.Rchild == null)
                    child_count = 0;
                else if (search_node.Lchild == null)
                    child_count = 1;
                else if (search_node.Rchild == null)
                    child_count = -1;
                else
                    child_count = 2;
                break;
            }
        }/**End for*/

        if (child_count == 0) {
            if (parent_child == -1) {
                parent_node.Lchild = null;
            } else if (parent_child == 1){
                parent_node.Rchild = null;
            } else {
                root.data[0] = null;
            }
        } else if (child_count == -1){
            temp_node = search_node.Lchild;
            if (parent_child == -1) {
                parent_node.Lchild = temp_node;
            } else if (parent_child == 1) {
                parent_node.Rchild = temp_node;
            } else {
                temp_node = root.Lchild;
                root = temp_node;
            }
        } else if (child_count == 1){
            temp_node = search_node.Rchild;
            if (parent_child == -1) {
                parent_node.Lchild = temp_node;
            } else if (parent_child == 1) {
                parent_node.Rchild = temp_node;
            } else {
                temp_node = root.Rchild;
                root = temp_node;
            }
        } else {
            temp_node = return_node(search_node);
            temp_node.Lchild = search_node.Lchild;
            temp_node.Rchild = search_node.Rchild;

            if (parent_child == -1) {
                parent_node.Lchild = temp_node;
            } else if (parent_child == 1){
                parent_node.Rchild = temp_node;
            } else {
                root = temp_node;
            }
        }
    }

    /**Return right minimum leaf Node*/
    private Node return_node (Node input){
        Node cur_node = input.Rchild;
        Node parent_node = input.Rchild;
        Node minimum_node = input.Rchild;
        for(;;){
            if (cur_node.Lchild != null){
                parent_node = cur_node;
                cur_node = cur_node.Lchild;
                minimum_node = cur_node;
            } else {
                if (minimum_node.Rchild != null){
                    Node temp = minimum_node.Rchild;
                    parent_node.Lchild = temp;
                    minimum_node.Rchild = null;
                    return minimum_node;
                } else {
                    parent_node.Lchild = null;
                    return minimum_node;
                }
             }
        }/**End for*/
    }

    public void search(String input){
        Node search = root;
        char ch;
        Scanner cinput = new Scanner(System.in);

        for (;;){
            if (input.compareTo(search.data[0]) < 0){
                search = search.Lchild;
            } else if (input.compareTo(search.data[0]) >  0){
                search = search.Rchild;
            } else {
                for (int i = 0; i < fields_count; i++) {
                    System.out.print(fields_name[i] + ": "+ search.data[i]+ " | ");
                }
                System.out.println("");
                System.out.println("--------------------");
                System.out.println("Modification, Exit");
                System.out.print("Select option you wanted: ");
                ch = cinput.next().toUpperCase().charAt(0);

                if (ch == 'M')
                    modify(search);
                else
                    break;
            }
            if (search == null){
                System.out.println("No exist");
                return;
            }
        }
    }

    public void modify(Node input){
        Scanner sinput = new Scanner(System.in);
        System.out.println("----------modify record----------");
        for (int i = 1; i < fields_count; i++) {
            System.out.print(fields_name[0] + "`s "+ fields_name[i] + " data: ");
            String temp = sinput.next();
            input.data[i] = temp;
        }
    }

    public void browse(){
        Node browse = root;
        int dep = depth(root);
        int[] way = new int[dep-1];
        int ch,i;
        i = 0;
        Scanner input = new Scanner(System.in);
        for(;;){

            if (i == 0)
                System.out.println("----root----");

            for (int j = 0; j < fields_count; j++) {
                System.out.print(" " + browse.data[j]);
            }
            System.out.println(" ");
            if(browse.Lchild != null)
                System.out.print(browse.Lchild.data[0] + " ");
            else
                System.out.print("Left null");

            if(browse.Rchild != null)
                System.out.println(" " + browse.Rchild.data[0]);
            else
                System.out.println("  Right null");

            for (;;) {
                System.out.print("Browsing Left, Right, Undo or Exit: ");
                ch = input.next().toUpperCase().charAt(0);
                if (ch == 'L' || ch == 'R' || ch == 'U' || ch == 'E')
                    break;
            }
            switch (ch){
                case 'L':
                    browse = browse.Lchild;
                    way[i] = -1;
                    i++;
                    break;
                case 'R':
                    browse = browse.Rchild;
                    way[i] = 1;
                    i++;
                    break;
                case 'U':
                    Node temp = root;
                    for (int j = 0; j < i-1; j++){
                        if (way[j] == -1)
                            temp = temp.Lchild;
                        else
                            temp = temp.Rchild;
                    }
                    browse = temp;
                    i--;
                    break;
                case 'E':
                    return;
            }
        }
    }

    /** depth of tree*/
    public int depth(Node root) {
        if (root == null)
            return 0;

        int left = depth(root.Lchild);
        int right = depth(root.Rchild);

        if (left > right)
            return left + 1;
        else
            return right + 1;
    }
}
