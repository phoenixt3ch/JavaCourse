package ru.vsu.cs.kostryukov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ICalculator {
    Logger logger = LogManager.getLogger();
    static int getFour() {
        return 2 + 2;
    }

    static double divide(double a, double b) {
        if (b == 0) {
            ArithmeticException e = new ArithmeticException("division by zero");
            logger.info(e.getMessage());
            throw e;
        }
        return a / b;
    }

    static double add(double a, double b) {
        return a + b;
    }

    static double subtract(double a, double b) {
        return a - b;
    }

    static double multiply(double a, double b) {
        return a * b;
    }
}
