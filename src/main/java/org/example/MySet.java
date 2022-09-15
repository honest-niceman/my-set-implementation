package org.example;

public class MySet<T> {
    private final T[] values;
    private final int[] indexes;
    private int freePlaceIdx = 0;

    public MySet(int size) {
        values = (T[]) new Number[size];
        indexes = new int[size];
        for (int i = 0, len = indexes.length; i < len; i++)
            indexes[i] = -1;
    }

    public void add(T element) {
        if (contains(element)) {
            return;
        }
        int a = (Integer) element % values.length;
        int i = (a < 0) ? -a : a;
        int index = findIdxToPaste(i);
        values[index] = element;
    }

    public boolean contains(T element) {
        int a = (Integer) element % values.length;
        int index = (a < 0) ? -a : a;
        if (values[index] == null) {
            return false;
        }
        if (values[index].equals(element)) {
            return true;
        }
        return recursiveCheck(element, index);
    }

    private boolean recursiveCheck(T element, int index) {
        int nextIndex = indexes[index];
        if (nextIndex == -1) {
            return false;
        }
        if (element.equals(values[nextIndex])) {
            return true;
        }
        return recursiveCheck(element, nextIndex);
    }

    private int findIdxToPaste(int index) {
        if (values[index] == null) {
            return index;
        }
        if (indexes[index] == -1) {
            while (values[freePlaceIdx] != null) {
                freePlaceIdx++;
            }
            indexes[index] = freePlaceIdx;
            return freePlaceIdx;
        }
        int nextIndex = indexes[index];
        return findIdxToPaste(nextIndex);
    }
}