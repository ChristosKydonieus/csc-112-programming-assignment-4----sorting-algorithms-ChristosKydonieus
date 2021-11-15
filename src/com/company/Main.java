package com.company;
import java.util.Scanner;
import java.util.Random;
/*Christos Kydonieus
CSC112 Fall 2021
Programming Assignment 2
November 15, 2021
This program asks the user for an array and then sorts it uses a bubble sort and a merge sort,
allowing the user to compare between the time and number of comparisons the user makes.
*/

public class Main {
    // this is to keep track of the number of merge sort comparisons merge sort will make.
    public static int numMergeComparison = 0;

    public static void main(String[] args) {
        // scanner and random
        Scanner scnr = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("*******************************");
	    // prompt the user for the size of the array of integers to be created
        System.out.println("How large of an array would you like to make?");
        int size = scnr.nextInt();

        // fill the array with random integers between 0 and 99
        int [] arr = new int[size];
        for (int i = 0; i < arr.length; i++){
            arr[i] = rand.nextInt(99);
        }

        // makes copies of the array
        int [] arrM = arr;
        int [] arrB = arr;

        System.out.println("*******************************");
        //print the array if the size is <= 20
        if (arr.length <= 20){
            System.out.println("Original:");
            printArr(arr);
        } else {
            System.out.println("Original size: " + arr.length);
        }

        // this is for asking the user which sort to use.
        while (true) {
            System.out.println("\nWhich sort do you want to do? Type 1 for bubblesort, 2 for mergesort, or 3 for both?");
            int sort = scnr.nextInt();

            // only bubble
            if (sort == 1) {
                //call bubblesort with the same array that was given to mergesort
                System.out.println("*******************************");
                System.out.println("Bubble Sort:");
                bubbleSort(arrB);
                break;
            } else if (sort == 2) {
                //call mergesort
                System.out.println("*******************************");
                System.out.println("Merge Sort:");

                // this starts the time keeping. Because this method has recursion, I figured it would be easier
                // to keep track outside of the method unlike the bubble sort.
                long startTime = System.nanoTime();
                // The true here is to let the method knwo to print out the left and right sides
                mergeSort(arrM, arrM.length, true);
                long endTime = System.nanoTime();
                long timeEllapM = endTime - startTime;

                // checks to print the array if small enough
                if (arr.length <= 20){
                    System.out.println("");
                    printArr(arrM);
                } else {
                    System.out.print("Merge: too long");
                }

                // reports the time to calculate and the number of comaprisons
                System.out.println("\nTime ellapsed: " + timeEllapM + " Nano seconds");
                System.out.println("Number of Comparisons: " + numMergeComparison);
                break;

            } else if (sort == 3){
                // this one does both sorts
                //call mergesort
                System.out.println("\n*******************************");
                System.out.println("Merge Sort:");

                // same as before, external time keeping
                long startTime = System.nanoTime();

                // sorts but does not print ut the left and right sides
                mergeSort(arrM, arrM.length, false);

                long endTime = System.nanoTime();
                long timeEllapM = endTime - startTime;

                // checks to print
                if (arr.length <= 20){
                    printArr(arrM);
                } else {
                    System.out.print("Merge: too long");
                }

                // reports
                System.out.println("\nTime ellapsed: " + timeEllapM + " Nano seconds");
                System.out.println("Number of Comparisons: " + numMergeComparison);


                //call bubblesort with the same array that was given to mergesort
                System.out.println("*******************************");
                System.out.println("Bubble Sort:");
                bubbleSort(arrB);
                break;
            }
        }
    }

    // this method prints the array which is passed as the parameter
    public static void printArr(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
    }

    // this bubble sorts an array by looping through and swapping values
    // the array is the only input, and the sorted array is the return value
    public static int[] bubbleSort(int[] arr) {
        // number of comparisons
        int numCompB = 0;

        // time keeping
        long startTime = System.nanoTime();

        // outer loops loops through array
        for (int i = 0; i < arr.length - 1; i++) {
            // inner loop checks for comaprisons
            for (int j = 0; j < arr.length - i - 1; j++){
                // increases comparisons
                numCompB++;
                // if the current value is greater, swap via temp value
                if (arr[j] > arr[j + 1]){
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

        }

        // total time
        long endTime = System.nanoTime();
        long timeEllapB = endTime - startTime;

        // check to print
        if (arr.length <= 20){
            printArr(arr);
        } else {
            System.out.print("Bubble: too long");
        }

        // report
        System.out.println("\nTime ellapsed: " + timeEllapB + " Nano seconds");
        System.out.println("Number of Comparisons: " + numCompB);
        return arr;
    }

    /* this is a recursive merge sort. This method has three inputs, the array, the length of the array,
    * and a boolean whether or not to print the left and right sides of the merge sort. Merge sort is a
    * divide and conquer sort that splits the array and then sorts each half by breaking it down until there is only
    * member left in each array and then merging them back together via the merge method.
     */
    public static void mergeSort(int [] arr, int length, boolean print){
        // base case, return if there is only one member left.
        if (length < 2){
            return;
        }

        // finds the middle
        int middle = length/2;
        // creates the half arrays
        int[] firstHalf = new int[middle];
        int[] secondHalf = new int[length - middle];
        // fills in the new arrays
        for (int i = 0; i < middle; i++){
            firstHalf[i] = arr[i];
        }
        for (int c = middle; c < length; c++){
            secondHalf[c - middle] = arr[c];
        }

        // if told to, print out the sides
        if (print){
            System.out.println("\nLeft:");
            printArr(firstHalf);
            System.out.println("\nRight:");
            printArr(secondHalf);
        }

        // recursion, break down each side again before merging back together
        mergeSort(firstHalf, middle, print);
        mergeSort(secondHalf, length - middle, print);
        // increase the comparisons
        numMergeComparison++;
        // merge back together
        merge(arr, firstHalf, secondHalf, middle, length - middle);
    }

    // this method does the actual merging. It needs and initial array, the left array, the right array,
    // and the lengths of each array. Basically, it looks through the left and right arrays and puts the
    // correct values in the inital array
    public static void merge(int[] initial, int[] left, int[] right, int lengthL, int lengthR) {
        // position keeping
        int i = 0;
        int j = 0;
        int c = 0;

        // this is for comparing the left and right arrays and then inserting them in the correct order of size
        // in the inital array.
        while (i < lengthL && j < lengthR) {
            if (left[i] <= right[j]) {
                initial[c++] = left[i++];
            }
            else {
                initial[c++] = right[j++];
            }
        }
        while (i < lengthL) {
            initial[c++] = left[i++];
        }
        while (j < lengthR) {
            initial[c++] = right[j++];
        }
    }
}
