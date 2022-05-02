package jh.hw;

public class CalculatorSoapImpl implements CalculatorSoap {
    @Override
    public int add(int intA, int intB) {
        return intA + intB;
    }

    @Override
    public int subtract(int intA, int intB) {
        return intA - intB;
    }

    @Override
    public int divide(int intA, int intB) {
        return intA / intB;
    }

    @Override
    public int multiply(int intA, int intB) {
        return intA * intB;
    }
}