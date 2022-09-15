package org.example;

import java.util.Arrays;

public class AndrewSet<T> {
    private final T[] values;
    private final int[] indexes;
    private int i = 0;

    public AndrewSet(int n) {
        values = (T[]) new Object[n];
        indexes = new int[n];
        Arrays.fill(indexes, -1);
    }

    public void addVal(T val) {
        int idx = Math.abs((Integer)val) % values.length;
        addRecursive(val, idx);
    }

    private void addRecursive(T val, int idx) {
        if (values[idx] == null) {
            values[idx] = val;
        } else if (!values[idx].equals(val)) {
            if (indexes[idx] != -1) {
                addRecursive(val, indexes[idx]);
            } else {
                while(values[i] != null) {
                    ++i;
                }
                values[i] = val;
                indexes[idx] = i;
            }
        }

    }

    public boolean contains(T val) {
        int idx = Math.abs((Integer)val) % values.length;
        return containsRecursive(val, idx);
    }

    private boolean containsRecursive(T val, int idx) {
        if (values[idx] != null) {
            if (values[idx].equals(val)) {
                return true;
            }

            if (indexes[idx] != -1) {
                return containsRecursive(val, indexes[idx]);
            }
        }
        return false;
    }
}