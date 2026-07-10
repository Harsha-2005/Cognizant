import java.util.*;
public class Streamtest {
    public static void main(String[] args) {
//        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        nums.stream().filter(n -> n % 2 == 0).forEach(System.out::println);
//        List<String> names = Arrays.asList("Harsha","Pavan","Gagan","Thrinayani");
//        names.stream().map(String::toUpperCase).forEach(System.out::println);
        List<Integer> list=new ArrayList<>();
        int n;
        Scanner sc=new Scanner(System.in);
        n=sc.nextInt();
        for(int i=0;i<n;i++){
            list.add(sc.nextInt());
        }



    }
}
