import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import static org.apache.commons.collections4.CollectionUtils.permutations;

public class DoubleDigestProblem {
    private static List<Integer> A = new ArrayList<Integer>();
    private static List<Integer> B = new ArrayList<Integer>();
    private static List<Integer> AB = new ArrayList<Integer>();

    public static void main(String[] args){
        fillLists();

        printFragmentList(A, "A");
        printFragmentList(B, "B");
        printFragmentList(AB, "AB");

        Collection<List<Integer>> permutationsA = getAllPossibleCombinations(A);
        Collection<List<Integer>> permutationsB = getAllPossibleCombinations(B);

        printAllPossibleOrders(permutationsA, "A");
        printAllPossibleOrders(permutationsB, "B");
    }

    public static void printAllPossibleOrders(Collection<List<Integer>> permutations, String name){
        System.out.print("\n\nAnzahl gueltiger Fragment-Reihenfolgen fuer " + name + ": " + permutations.size());
        int counter = 1;
        for(List<Integer> permutation : permutations){
            System.out.print("\n" + counter + ". Fragment-Reihenfolge " + name + " = (");
            for(int fragment : permutation) {
                System.out.print(fragment + ", ");
            }
            System.out.print(").");
        }
    }

    public static Collection<List<Integer>> getAllPossibleCombinations(List<Integer> list){
        Collection<List<Integer>> collection = permutations(list);

        return collection;
    }

    public static void printFragmentList(List<Integer> fragments, String name){
        System.out.print("\nFragments from " + name + ": (");
        for(int fragment : fragments) {
            System.out.print(fragment + ", ");
        }
        System.out.print(")");
    }

    public static void fillLists(){
        Scanner in = new Scanner(System.in);
        int value;
        int count = 0;

        System.out.println("How many fragments should Enzyme A have?");
        int lengthA = in.nextInt();
        System.out.println("\nHow many fragments should Enzyme B have?");
        int lengthB = in.nextInt();
        System.out.println("\nHow many fragments should Enzyme AB have?");
        int lengthAB = in.nextInt();

        do{
            System.out.println("\nEnter the length of a fragement for A:");
            value = in.nextInt();

            if(value <= 0){
                System.out.println("Try again. Only positive integers are allowed");
            }else{
                System.out.println("Okay.");
                A.add(value);
                lengthA--;
            }
        }while(lengthA > 0);

        do{
            System.out.println("\nEnter the length of a fragement for B:");
            value = in.nextInt();

            if(value <= 0){
                System.out.println("Try again. Only positive integers are allowed");
            }else{
                System.out.println("Okay.");
                B.add(value);
                lengthB--;
            }
        }while(lengthB > 0);

        do{
            System.out.println("\nEnter the length of a fragement for AB:");
            value = in.nextInt();

            if(value <= 0){
                System.out.println("Try again. Only positive integers are allowed");
            }else{
                System.out.println("Okay.");
                AB.add(value);
                lengthAB--;
            }
        }while(lengthAB > 0);

        Collections.sort(A);
        Collections.sort(B);
        Collections.sort(AB);
    }

    public static void findAllValidFragmentOrders(){
        int strangLength = 0;

        for(int fragment : A){
            strangLength += fragment;
        }

        strangLength++;

        int[] cutPositionsForA = new int[strangLength];
        int[] cutPositionsForB = new int[strangLength];
        int[] cutPositionsforB = new int[strangLength];

        for(int i = 1; i < strangLength; i++){
            
        }
    }
}
