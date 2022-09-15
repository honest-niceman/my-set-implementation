package org.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Warmup(iterations = 3)
@Measurement(iterations = 8)
public class Main {

    public static final int SIZE = 10_000_000;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Main.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    private int[] arr1 = new int[SIZE];
    private int[] arr2 = new int[SIZE];

    @Setup
    public void setup() {
        Random random = new Random();
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = random.nextInt();
        }
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = random.nextInt();
        }
    }

    @Benchmark
    public void JavaHashSet() {
        findIntersection(arr1, arr2);
    }

    @Benchmark
    public void SetWithoutRecursion() {
        findIntersectionWithoutRecursion(arr1, arr2);
    }

    @Benchmark
    public void MyHashSet() {
        findIntersectionMySet(arr1, arr2);
    }

    @Benchmark
    public void AndrewHashSet() {
        findIntersectionAndrewSet(arr1, arr2);
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

    public int[] findIntersectionAndrewSet(int[] arr1, int[] arr2) {
        if (arr1.length > arr2.length) {
            return findIntersectionMySet(arr2, arr1);
        }
        AndrewSet<Integer> set = new AndrewSet<>(arr1.length);
        int[] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            set.addVal(arr1[i]);
        }
        int j = 0;
        for (int i = 0; i < arr2.length; i++) {
            if (set.contains(arr2[i])) {
                result[j++] = arr2[i];
            }
        }
        return Arrays.copyOf(result, j);
    }

    public int[] findIntersectionWithoutRecursion(int[] arr1, int[] arr2) {
        if (arr1.length > arr2.length) {
            return findIntersectionMySet(arr2, arr1);
        }
        SetWithoutRecursion<Integer> set = new SetWithoutRecursion<>(arr1.length);
        int[] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            set.addVal(arr1[i]);
        }
        int j = 0;
        for (int i = 0; i < arr2.length; i++) {
            if (set.contains(arr2[i])) {
                result[j++] = arr2[i];
            }
        }
        return Arrays.copyOf(result, j);
    }
}