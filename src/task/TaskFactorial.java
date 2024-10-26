package task;

public class TaskFactorial extends TaskImpl {

    @Override
    public void execute() {
        // Split the input to get the number
        int number = Integer.parseInt(input);
        // Calculate the factorial
        result = String.valueOf(factorial(number));
    }

    // Method to calculate factorial
    private long factorial(int num) {
        if (num == 0) {
            return 1;
        }
        long result = 1;
        for (int i = 1; i <= num; i++) {
            result *= i;
        }
        return result;
    }
}