package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        int[] arr1 = new int[300_000];
        int[] arr2 = new int[300_000];
        Random random = new Random();
        System.out.println("Start");
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = random.nextInt();
//            arr1[i] = i;
        }
        System.out.println("Arr1 has been initialized");
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = random.nextInt();
//            arr2[i] = i;
        }
        System.out.println("Arr2 has been initialized");
        Main s = new Main();
        long t1 = System.currentTimeMillis();
        int[] result1 = s.findIntersection(arr1, arr2);
        long t2 = System.currentTimeMillis();
        System.out.println((t2 - t1) + ", " + result1.length);
        t1 = System.currentTimeMillis();
        int[] result2 = s.findIntersectionMySet(arr1, arr2);
        t2 = System.currentTimeMillis();
        System.out.println((t2 - t1) + ", " + result2.length);
    }

    public int[] findIntersection(int[] arr1, int[] arr2) {
        if (arr1.length > arr2.length) {
            return findIntersection(arr2, arr1);
        }
        Set<Integer> set = new HashSet<>();
        int[] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            set.add(arr1[i]);
        }
        int j = 0;
        for (int i = 0; i < arr2.length; i++) {
            if (set.contains(arr2[i])) {
                result[j++] = arr2[i];
            }
        }
        return Arrays.copyOf(result, j);
    }

    public int[] findIntersectionMySet(int[] arr1, int[] arr2) {
        if (arr1.length > arr2.length) {
            return findIntersectionMySet(arr2, arr1);
        }
        MySet<Integer> set = new MySet<>(arr1.length);
        int[] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            set.add(arr1[i]);
        }
        int j = 0;
        for (int i = 0; i < arr2.length; i++) {
            if (set.contains(arr2[i])) {
                result[j++] = arr2[i];
            }
        }
        return Arrays.copyOf(result, j);
    }

    public static class MySet<T> {
        private final T[] values;
        private final int[] indexes;

        public MySet(int size) {
            values = (T[]) new Object[size];
            indexes = new int[size];
            Arrays.fill(indexes, -1);
        }

        public void add(T element) {
            if (contains(element)) {
                return;
            }
            int i = Math.abs((Integer) element % values.length);
            int index = findIndToPaste(i);
            values[index] = element;
        }

        public boolean contains(T element) {
            int index = Math.abs((Integer) element % values.length);
            if (values[index] == null) {
                return false;
            }
            if (values[index].equals(element)) {
                return true;
            }
            int nextIndex = indexes[index];
            return recursiveCheck(element, values[index], nextIndex);
        }

        private boolean recursiveCheck(T element, T nextToCompare, int index) {
            if (element.equals(nextToCompare)) {
                return true;
            }
            if (index == -1) {
                return false;
            }
            int nextIndex = indexes[index];
            return recursiveCheck(element, values[index], nextIndex);
        }

        private int findIndToPaste(int index) {
            if (indexes[index] != -1) {
                int nextIndex = indexes[index];
                return findIndToPaste(nextIndex);
            }
            if (values[index] == null) {
                return index;
            }
            for (int i = 0; i < values.length; i++) {
                if (values[i] == null) {
                    indexes[index] = i;
                    return i;
                }
            }
            return -0;
        }

        @Override
        public String toString() {
            return "MySet{" +
                    "values=" + Arrays.toString(values) +
                    ", indexes=" + Arrays.toString(indexes) +
                    '}';
        }
    }
}