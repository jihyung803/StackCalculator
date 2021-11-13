/*
Name: Jihyung Park
Date: 11/04/2021
Section: 
Instructor:

Purpose of program: Get an expression from users and convert in the form of post fix. 
Also using post fix calculation, print result. It can handle negative numbers and double.
 */
package assignment;

import java.util.Scanner;
import java.util.Stack;

public class StackCalculator {
	public static String InfixToPostfix(String input) {
		//set empty post fix string
		String postFix = "";

		//set empty stack(character)
		Stack<Character> stack = new Stack<>();
		int length = input.length();
		int index = 0;
		boolean lastOp = true;
		
		while (index < length) {
			
			char operator = input.charAt(index);
			
			//if higher priority operator is already in stack, pop it and add to post fix string
			if (isDigit(operator)) {
				while (index < length && isDigit(input.charAt(index))) 
				{
					postFix += input.charAt(index);
					index++;
				}
				postFix += ' ';
				lastOp = false;
			}
			else 
			{
				if (operator == '(') //lowest priority
				{
					stack.push(operator);
					index++;
				} 
				else if (operator == ')') //highest priority
				{
					while (stack.peek() != '(') 
					{
						postFix += stack.pop();
						postFix += ' ';
					}
					stack.pop();
					index++;
				}
				else if (isOperator(operator)) 
				{
					if (lastOp && operator == '-')
					{
						postFix += '-';
						index++;
						continue;
					}
					else if (!stack.isEmpty() && priority(stack.peek()) >= priority(operator)) 
					{
						postFix += stack.pop();
						postFix += ' ';
					}
					stack.push(operator);
					index++;
				}
				else
				{
					throw new IllegalArgumentException("wrong input");
				}
				lastOp = true;
			}
		}

		while (!stack.isEmpty()) //after we pop every operator, then pop rest things
		{
			postFix += stack.pop();
			postFix += " ";
		}
		return postFix;
	}

	//method that check is digit or not
	public static boolean isDigit(char ch) {
		double tmp = ch - '0';
		return ((tmp >= 0 && tmp <= 9) || ch == '.' ? true : false);
	}

	//method that check it is one of operators
	public static boolean isOperator(char operator) {
		return (operator == '-' || operator == '+' || operator == '*' || operator == '/');
	}

	//setting priority level
	public static int priority(char operator) {
		if (operator == '(') 
		{
			return 0;
		}
		else if (operator == '-' || operator == '+') 
		{
			return 1;
		}
		else if (operator == '*' || operator == '/') 
		{
			return 2;
		}
		return -1;
	}

	//simple calculator
	public static double tempCal(double temp1, double temp2, char operator) {
		if (operator == '+') 
		{
			return (temp1 + temp2);
		}
		else if (operator == '-') 
		{
			return (temp1 - temp2);
		}
		else if (operator == '*') 
		{
			return (temp1 * temp2);
		}
		else 
		{
			return (temp1 / temp2);
		}
	}

	//post fix stack calculator
	public static double cal(String postFix) {
		
		
		Stack<Double> stack = new Stack<>();
		String[] splitPostFix = postFix.split(" ");
		
		double temp1, temp2;
		
		for (int i = 0; i < splitPostFix.length; i++) 
		{
			if (isOperator(splitPostFix[i].charAt(0)) && splitPostFix[i].length() == 1)//check length to prevent isOperator method think negative number is operator 
			{
				
				temp1 = stack.pop();
				temp2 = stack.pop();
				stack.push(tempCal(temp1, temp2, splitPostFix[i].charAt(0)));//num1, num2, and operator
			}
			else 
			{
				stack.push(Double.valueOf(splitPostFix[i]));
			}
		}
		return stack.pop();
	}
	
	//main
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Type the expression(space and equal sign is not allowed): ");
		String inputStr = sc.next();
		System.out.println("Infix expression: " + inputStr);
		System.out.println("Postfix expression: " + InfixToPostfix(inputStr));
		System.out.println("Result: " + cal(InfixToPostfix(inputStr)));
	}
}
