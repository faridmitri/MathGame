package com.am.mathhero.Activities;

import java.util.ArrayList;
import java.util.Random;

public class Operations {

    private static int firstNumber;
    private static int secondNumber;
    private static int result;
    private static String operator;
    private Random random;

    public Operations(){
        random = new Random();
    }




    public void addition(int number,int range){
        firstNumber = random.nextInt(number) + range;
        secondNumber = random.nextInt(number) + range;
        operator = "+";
        result = firstNumber + secondNumber;
    }

    public void subtraction(int number,int range) {
        firstNumber = random.nextInt(number) + range;
        secondNumber = random.nextInt(firstNumber);
        operator = " - ";
        result = firstNumber - secondNumber;

    }

    public void multiplication(int number,int range){
        firstNumber = random.nextInt(number) + range;
        secondNumber = random.nextInt(number ) + range;
        operator = " × ";

        result = firstNumber * secondNumber;

    }

    public void division(int number,int range){
        ArrayList<Integer> allDivisors = new ArrayList<>();
        int numberHelper;
        int i;
        operator = " / ";
        firstNumber = random.nextInt(number) + range;

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
    public static int getSecondNumber(){
        return secondNumber;
    }
    public static int getResult(){
        return result;
    }


    public static String getOperator() {
        return operator;
    }


}

