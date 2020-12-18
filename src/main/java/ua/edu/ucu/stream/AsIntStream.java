package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AsIntStream implements IntStream {

    private List<Integer> elementsList = new ArrayList();
    private int size = 0;

    private AsIntStream() {
    }

    private AsIntStream(List<Integer> values) {
        elementsList = values;
        size = values.size();
    }

    private AsIntStream(int[] values) {
        for (Integer value : values) {
            elementsList.add(value);
        }
        size = values.length;
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values) {
        };
    }

    public void checkEmpty() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Double average() {
        return (double) sum() / count();
    }

    @Override
    public Integer max() {
        checkEmpty();
        int currMax = elementsList.get(0);
        for (int i = 1; i < size; i++) {
            currMax = Math.max(currMax, elementsList.get(i));
        }
        return currMax;
    }

    @Override
    public Integer min() {
        checkEmpty();
        int currMin = elementsList.get(0);
        for (int i = 1; i < size; i++) {
            currMin = Math.min(currMin, elementsList.get(i));
        }
        return currMin;
    }

    @Override
    public long count() {
        return size;
    }

    @Override
    public Integer sum() {
        checkEmpty();
        int elmSum = 0;
        for (Integer num : elementsList) {
            elmSum += num;
        }
        return elmSum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        List<Integer> tempElementsList = new ArrayList();
        for (Integer num : elementsList) {
            if (predicate.test(num)) {
                tempElementsList.add(num);
            }
        }
        return new AsIntStream(tempElementsList);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (Integer num : elementsList) {
            action.accept(num);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        List<Integer> tempElementsList = new ArrayList(elementsList);
        for (int i = 0; i < size; i++) {
            tempElementsList.set(i, mapper.apply(elementsList.get(i)));
        }
        return new AsIntStream(tempElementsList);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        List<Integer> tempElementsList = new ArrayList();
        for (int i = 0; i < size; i++) {
            int[] mappedArr = func.applyAsIntStream(elementsList.get(i)).toArray();
            for (int num : mappedArr) {
                tempElementsList.add(num);
            }
        }
        return new AsIntStream(tempElementsList);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        for (Integer num : elementsList) {
            identity = op.apply(identity, num);
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        int[] elementsArray = new int[size];
        for (int i = 0; i < size; i++) {
            elementsArray[i] = elementsList.get(i);
        }
        return elementsArray;
    }

}
