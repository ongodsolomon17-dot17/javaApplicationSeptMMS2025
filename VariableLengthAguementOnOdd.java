public class ClassWork20{
    public static void main(String[] args){
        int countOfOdd = oddNumbers(5,2,4,6,7,9,2,4,5,1,3,4,9,3,1,2,5);
        Systm.out.printf("The count of odd numbers is %d%n",countOfOdd);
    }
    public static int oddNumbers(int... numbers){
        int countOfOdd = 0;
        for(int number : numbers){
            if(number % 2 == 1){
                countOfOdd++;
            }
        }
        return countOfOdd;
    }
}