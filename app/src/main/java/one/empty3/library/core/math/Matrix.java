/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.library.core.math;

import one.empty3.feature.PixM;

import java.util.Arrays;

public class Matrix extends PixM {
    public static final double DOUBLE_MIN = 0.0001;
    public static final String NUMBER_FORMAT = "%+12.5f";
    private static final double TOLERANCE = 0.000001;
    public Matrix(int lines, int columns, int countComp) {
        super(lines, columns);
        this.lines = lines;
        this.columns = columns;
        this.compCount = countComp;
        x = new double[lines * columns * compCount];
    }
    public Matrix(int lines, int columns) {
        super(lines, columns);
        this.lines = lines;
        this.columns = columns;
        this.compCount = 1;
        x = new double[lines * columns * compCount];
    }

    public Matrix(int lines, int columns, Producer producer) {
        this(lines, columns, 1);
        for (int i = 0; i < x.length; i++) {
            x[i] = producer.produce(i);

        }
    }

    public Matrix apply(ValueProducer producer) {
        Matrix result = new Matrix(lines, columns, 1);

        for (int i = 0; i < x.length; i++) {
            result.x[i] = producer.produce(i, x[i]);
        }

        return result;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        int index = 0;

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(String.format(NUMBER_FORMAT, x[index]));
                sb.append("\t");
                index++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Matrix multiply(Matrix m) {
        Matrix result = new Matrix(lines, m.columns, 1);

        assert columns == m.lines : "Cannot multiply";
        int index = 0;
        for (int row = 0; row < result.lines; row++) {
            for (int col = 0; col < result.columns; col++) {
                for (int c = 0; c < getCompCount(); c++) {
                    this.compNo = c;
                    m.setCompNo(c);
                    result.setCompNo(c);
                    for (int k = 0; k < columns; k++) {
                        result.set(row, col, result.get(row, col) +
                                get(row, k) * get(k, col));
                    }

                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix matrix = (Matrix) o;

        if (columns != matrix.columns) return false;
        if (lines != matrix.lines) return false;
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i] - matrix.x[i]) > TOLERANCE)
                return false;

        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = columns;
        result = 31 * result + lines;
        result = 31 * result + Arrays.hashCode(x);
        return result;
    }

    public double get(int row, int col) {
        return x[row*columns+col];
    }
    public void set(int row, int col, double value) {
        x[row*columns+col] = value;
    }
    public void set(int row, double value) {
        x[row] = value;
    }

    public double get(int index) {
        return x[index];
    }

    public Matrix softmax() {
        Matrix matrix = new Matrix(lines, columns, 1);
        double sumExp = 0.0;
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                sumExp += Math.exp(get(i,j));
            }
        }
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.set(i, j, Math.exp(get(i,j))/sumExp);
            }
        }
        return matrix;
    }

    public interface Producer {
        double produce(int index);
    }

    public interface ValueProducer {
        double produce(int index, double value);
    }
    public interface RowColProducer {
        double produce(int row, int col, double value);
    }

    public Matrix modify(RowColProducer producer) {
        int index=0;
        for (int row = 0; row < lines; row++) {
            for (int col = 0; col < columns; col++) {
                x[index] = producer.produce(row, col, x[index]);
                index++;
            }
        }
        return this;
    }


    public Matrix modify(ValueProducer producer) {
        for (int i = 0; i < x.length; i++) {
            x[i] = producer.produce(i, x[i]);
        }
        return this;
    }

    public interface IndexValueConsumer {
        public void consume(int index, double value);
    }
    public void forEach(IndexValueConsumer consumer) {
        for (int i = 0; i < x.length; i++) {
            consumer.consume(i, x[i]);
        }
    }
    public interface RowColIndexValueConsumer {
        public void consume(int row, int col, int index, double value);
    }
    public void forEach(RowColIndexValueConsumer consumer) {
        int index = 0;
            for (int row = 0; row < lines; row++) {
                for (int col = 0; col < columns; col++) {
                    consumer.consume(row, col, index, get(row, col));
                    index++;
                }
            }

    }
    public Matrix sumColumns() {
        Matrix matrix = new Matrix(1, columns, 1);
        forEach((row, col, index, value) -> matrix.set(0, col, matrix.get(0, col)+value));
        return matrix;
    }
    public Matrix sumlines() {
        Matrix matrix = new Matrix(lines, 1, 1);
        forEach((row, col, index, value) -> matrix.set(row, 0, matrix.get(row, 0)+value));
        return matrix;
    }


}
