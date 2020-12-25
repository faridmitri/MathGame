package com.am.mathhero.Acttivities;

import java.util.ArrayList;
import java.util.Random;

public class Operations {

    private static int firstNumber;
    private static int secondNumber;
    private static int result;
    private static String operator;
    private Random random;

    Operations(){
        random = new Random();
    }




    void addition (int number){
        firstNumber = random.nextInt(number);
        secondNumber = random.nextInt(number);
        operator = "+";
        result = firstNumber + secondNumber;
    }

    void subtraction (int number) {
        firstNumber = random.nextInt(number) + 1;
        secondNumber = random.nextInt(firstNumber);
        operator = "-";
        result = firstNumber - secondNumber;

    }

    void multiplication (int number){
        firstNumber = random.nextInt(number) + 1;
        secondNumber = random.nextInt(number ) + 1;
        operator = "*";

        result = firstNumber * secondNumber;

    }

    void division (int number){
        ArrayList<Integer> allDivisors = new ArrayList<>();
        int numberHelper;
        int i;
        operator = "/";
        firstNumber = random.nextInt(number) + 1;

        for (i = 1; i <= firstNumber; i++) {
            if (firstNumber % i == 0) {
                allDivisors.add(i);
            }
        }


        numberHelper = random.nextInt(allDivisors.size()) + 1;
        secondNumber = allDivisors.get(numberHelper - 1);

        result = firstNumber / secondNumber;
    }



    public static int getFirstNumber(){
        return firstNumber;
    }
    static int getSecondNumber(){
        return secondNumber;
    }
    static int getResult(){
        return result;
    }


    public static String getOperator() {
        return operator;
    }


}

