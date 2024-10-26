package task;

public class TaskSum extends TaskImpl {

    @Override
    public void execute() {
        int number = Integer.parseInt(input);
        result = String.valueOf(sum(number));
    }

    private int sum(int number) {
        if (number <= 0) return 0;
        return number + sum(number - 1);
    }
}