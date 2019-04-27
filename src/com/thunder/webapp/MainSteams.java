package com.thunder.webapp;

import java.util.*;
import java.util.stream.Collectors;

public class MainSteams {
    public static void main(String[] args) {
        int[] array = new Random().ints(10, 1, 10).toArray();
        List<Integer> list = new Random().ints(10, 1, 10).boxed().collect(Collectors.toList());

        System.out.println("array      = " + Arrays.toString(array));
        System.out.println("list       = " + list.toString());
        System.out.println("minValue   = " + minValue(array));
        System.out.println("oddOrEven1 = " + oddOrEven1(list));
        System.out.println("oddOrEven2 = " + oddOrEven2(list));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (x, y) -> x * 10 + y);
    }

    public static List<Integer> oddOrEven1(List<Integer> integers) {
        //long sum = integers.stream().mapToInt(Integer::intValue).sum();
        long sum = integers.stream().reduce(0, (x, y) -> x + y);
        return integers.stream().filter(x -> (x % 2) == (sum % 2)).collect(Collectors.toList());
    }

    public static List<Integer> oddOrEven2(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));
        return map.get(false).size() % 2 == 0 ?
                map.get(true).stream().collect(Collectors.toList()) :
                map.get(false).stream().collect(Collectors.toList());
    }

}
