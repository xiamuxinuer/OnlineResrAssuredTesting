package com.automation.tests.Re_BootCampTests;

public class Class_Object {
    public static void main(String[] args) {
        Class_Object object=new Class_Object();
        object.print(15);
    }

public void print(int num){

    for (int i = 2; i <=num; i++) {
        if (isPrime(i) )
        System.out.println(i);
    }
}

public static  boolean isPrime(int num){
    for (int i = 2; i <num; i++) {
        if (num%i==0)
            return false;
    }
        return true;
}








}
