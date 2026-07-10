import java.util.*;
import java.util.stream.*;

public class collectors {
    public static void main(String[] args){
//        Scanner input=new Scanner(System.in);
//        String DepName=input.nextLine();
//        Employee employees=new Employee(DepName,);
//        Map<String, List<Employee>> byDept = employees.stream().collect(Collectors.groupingBy(Employee::getDept));
//
//        Map<String,Long> countByDept = employees.stream().collect(Collectors.groupingBy(Employee::getDept,Collectors.counting()));
//
//        Map<String, Double> avgSalary = employees.stream().collect(Collectors.groupingBy(Employee::getDept,Collectors.averagingDouble()));
        List<Integer> nums = Arrays.asList(5,8,2,7,10,15);

        List<Integer> result = nums.stream()
                .filter(n->n%2==0)
                .map(n->n*n)
                .sorted()
                .collect(Collectors.toList());

        System.out.println(result);

    }
}
//class Employee{
//    String DeptName;
//    Employee(String DeptName){
//        this.DeptName=DeptName;
//    }
//    String getDept(){
//        return DeptName;
//    }
//
//
//}
