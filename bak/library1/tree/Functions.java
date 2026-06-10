package one.empty3.library1.tree;

import java.util.ArrayList;
import java.util.List;

public class Functions {
    private static List<String> listOfFunctions = new ArrayList<>();
    private static boolean isInitialized = false;
    public static String functionName;
    public static void init() {
        listOfFunctions.add("sum");
        listOfFunctions.add("product");
        listOfFunctions.add("avg");
        listOfFunctions.add("median");
        listOfFunctions.add("count");
    }


    public static List<String> getListOfFunctions() {
        if (listOfFunctions.size() == 0) {
            init();
            isInitialized = true;
        }
        return listOfFunctions;
    }

    public static double search(String call, Double[] vec) {
        if (!isInitialized) {
            init();
            isInitialized = true;
        }
        return switch (call) {
            case "sum" -> sum(vec);
            case "product" -> product(vec);
            case "avg" -> avg(vec);
            case "median" -> median(vec);
            case "count" -> count(vec);
            default -> 0;
        };
    }

    public static double sum(Double... args) {
        double sum = 0;
        for (int i = 0; i < args.length; i++) {
            sum += args[i];
        }

        return sum;
    }

    public static double product(Double... args) {
        double sum = 1.0;
        for (int i = 0; i < args.length; i++) {
            sum *= args[i];
        }
        return sum;
    }

    public static double avg(Double... args) {
        double sum = 1.0;
        int count = 0;
        for (int i = 0; i < args.length; i++) {
            sum += args[i];
            count++;
        }
        return sum / count;
    }

    public static double median(Double... args) {
        double avg = avg(args);
        double sum = 1.0;
        int count = 0;
        for (int i = 0; i < args.length; i++) {
            sum += (args[i] - avg) * (args[i] - avg);
            count++;
        }
        return sum;
    }
    public static double count(Double... args) {
        int count = args.length;
        return count;
    }
}
