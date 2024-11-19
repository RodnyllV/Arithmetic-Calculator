import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main implements ActionListener {
    static JButton equalButton;
    static JTextField calculationOutput;

    public static JFrame calcWindow() {
        JFrame window = new JFrame();
        window.setTitle("Calculator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(275, 350);
        window.setLocationRelativeTo(null);
        window.setLayout(null);

        return window;
    }

    public static JTextField outputBox(JFrame window) {
        JTextField calculationOutput = new JTextField();
        calculationOutput.setBounds(25, 50, 210, 30);
        //calculationOutput.setPreferredSize(new Dimension(350, 50));
        calculationOutput.setHorizontalAlignment(JLabel.RIGHT);
        calculationOutput.setFont(new Font("Consolas", Font.PLAIN, 15));
        calculationOutput.setCaretColor(Color.black);
        calculationOutput.setForeground(Color.white);
        calculationOutput.setBackground(Color.gray);
        calculationOutput.setEditable(false);

        window.add(calculationOutput);

        return calculationOutput;
    }

    public static void main(String[] args) {
        // Calculator Window
        JFrame window = calcWindow();
        
        // Calculator Text Box
        calculationOutput = outputBox(window);

        // Calculator Digits
        JPanel calculatorDigitPanel = new JPanel();
        calculatorDigitPanel.setLayout(new GridLayout(5, 3, 3, 3));
        calculatorDigitPanel.setBounds(25, 100, 150, 200);
        
        for (int i = 9; i >= 0; i--) {
            Element numberElement = new Element(String.valueOf(i), calculationOutput, null);
            numberElement.setToPanel(calculatorDigitPanel);
        }

        Element openParenthesis = new Element("(", calculationOutput, null); openParenthesis.setToPanel(calculatorDigitPanel);
        Element closedParenthesis = new Element(")", calculationOutput, null); closedParenthesis.setToPanel(calculatorDigitPanel);

        Element decimalPlace = new Element(".", calculationOutput, null); decimalPlace.setToPanel(calculatorDigitPanel);
        Element negativeSign = new Element("(-)", calculationOutput, "-"); negativeSign.setToPanel(calculatorDigitPanel);

        // Operators
        JPanel arithmeticOperatorPanel = new JPanel();
        arithmeticOperatorPanel.setLayout(new GridLayout(5, 1, 0, 3));
        arithmeticOperatorPanel.setBounds(185, 100, 50, 200);

        ArrayList<Character> operatorOrder = new ArrayList<Character>(Arrays.asList(new Character[]{'^', '*', '/', '+', '-'}));

        for (char operator : operatorOrder) {
            Element operatorButton = new Element(String.valueOf(operator), calculationOutput, null); operatorButton.setToPanel(arithmeticOperatorPanel);
        }

        // Clear / All Clear

        JPanel clearPanel = new JPanel();
        clearPanel.setBounds(25, 10, 150, 25);
        clearPanel.setLayout(new GridLayout(1, 2, 2, 0));

        JButton clearButton = new JButton();
        clearButton.setText("C");
        clearButton.setFocusable(false);

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String subtractedText = calculationOutput.getText();

                if (subtractedText.length() > 0) {
                    subtractedText = subtractedText.substring(0, subtractedText.length() - 1);

                    calculationOutput.setText(subtractedText);
                }
            }
        });
        
        JButton allClearButton = new JButton();
        allClearButton.setText("AC");
        allClearButton.setFocusable(false);

        allClearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculationOutput.setText("");
            }
        });

        clearPanel.add(clearButton);
        clearPanel.add(allClearButton);

        // Equal Operation
        equalButton = new JButton();
        equalButton.setPreferredSize(new Dimension(400, 200));
        equalButton.setText("=");
        equalButton.setFocusable(false);

        equalButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String calculationText = calculationOutput.getText();

                do {
                    LinkedList<String> expressionOrder = new LinkedList<>();
                    Stack<Character> parenthesisStack = new Stack<>();
                    
                    boolean addNewExpression = true;

                    for (char item : calculationText.toCharArray()) {
                        switch(item) {
                            case '(': {
                                parenthesisStack.push('(');

                                addNewExpression = true;
                            } break;

                            case ')': {
                                parenthesisStack.pop();
                            } break;

                            default: {
                                String itemString = Character.toString(item);

                                if (addNewExpression == true) {
                                    expressionOrder.addFirst(itemString);

                                    addNewExpression = false;
                                } else {
                                    expressionOrder.set(0, expressionOrder.get(0) + itemString);
                                }
                            }
                        }
                    }

                    for (String expression : expressionOrder) {
                        if (operatorOrder.contains(expression.charAt(expression.length() - 1))) {
                            break;
                        }

                        double output = operationFunction.evaluateExpression(expression);

                        String surroundingParenthesis = "(" + expression + ")";

                        System.out.println(expression);

                        if (calculationText.contains(surroundingParenthesis)) {
                            calculationText = calculationText.replace(surroundingParenthesis, String.valueOf(output));
                        } else {
                            calculationText = calculationText.replace(expression, String.valueOf(output));
                        }

                        calculationOutput.setText(calculationText);
                    }

                } while (!calculationText.matches("-?\\d+(\\.\\d+)?"));

                if (calculationText.substring(calculationText.length() - 2, calculationText.length()).contains(".0")) {
                    calculationText = calculationText.substring(0, calculationText.length() - 2);

                    calculationOutput.setText(calculationText);
                }
            } 
        });

        calculatorDigitPanel.add(equalButton);

        // Add all elements to the window
        window.add(clearPanel);
        window.add(calculatorDigitPanel);
        window.add(arithmeticOperatorPanel);
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}