import java.util.*;

public class operationFunction {
    private static void pushString(Queue<Character> operatorQueue, LinkedList<Double> operandList, char[] toSearch, String expression) {
        String previousNumber = "";
        Boolean previousNumberInOperation = false;
        ArrayList<Character> validDigits = new ArrayList<>(Arrays.asList(new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'}));
        
        for (int current = 0; current < expression.length(); current++) {
            char currentChar = expression.charAt(current);

            if (currentChar == toSearch[0] || currentChar == toSearch[1]) {
                operandList.addLast(Double.valueOf(previousNumber));
                previousNumber = "";
                
                operatorQueue.add(currentChar);

                previousNumberInOperation = true;
            } else {
                if (!validDigits.contains(currentChar)) {
                    if (previousNumberInOperation == true) {
                        operandList.addLast(Double.valueOf(previousNumber));

                        previousNumberInOperation = false;
                    }

                    previousNumber = "";
                } else {
                    previousNumber += currentChar;
                }
            }
            if (current == expression.length() - 1 && previousNumberInOperation == true) {
                operandList.addLast(Double.valueOf(previousNumber));

                previousNumber = "";
            }
        }
        
    }

    public static void traverseAndReplace(LinkedList<String> expressionList) {

    }

    public static double evaluateExpression(String expression) {
        Queue<Character> operatorQueue = new LinkedList<>();
        LinkedList<Double> operandList = new LinkedList<>();

        for (int pemdas = 0; pemdas < 3; pemdas++) {
            char[] toSearch = new char[2];

            switch(pemdas) {
                case 0: { // Exponent
                    toSearch[0] = '^';
                    toSearch[1] = '#';
                } break; 
                case 1: {
                    toSearch[0] = '*';
                    toSearch[1] = '/';
                } break;
                case 2: { // Addition, Subtraction
                    toSearch[0] = '+';
                    toSearch[1] = '-';
                } break;
            }

            pushString(operatorQueue, operandList, toSearch, expression);
        }

        while (!operatorQueue.isEmpty()) {
            double firstNum = operandList.peek(); operandList.removeFirst();
            double secondNum = operandList.peek(); operandList.removeFirst();

            double result = 0.0;

            switch(operatorQueue.peek()) {
                case '^': {
                    result = Math.pow(firstNum, secondNum);
                } break;

                case '*': {
                    result = firstNum * secondNum;
                } break;

                case '/': {
                    result = firstNum / secondNum;
                } break;
                
                case '+': {
                    result = firstNum + secondNum;
                } break;

                case '-': {
                    result = firstNum - secondNum;
                }
            }

            operatorQueue.remove();
            operandList.addFirst(result);
        }

        return operandList.peek();
    }
}
