/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.library1.shader;

import one.empty3.library.StructureMatrix;

public class Mat {
    int l, c;
    private StructureMatrix<Double> values = new StructureMatrix<>(2, Double.class);

    public Mat(int i, int j) {
        this.l = i;
        this.c = j;
    }

    public Mat(double[][] values) {
        l = values.length;
        c = values[0].length;
        for (int i = 0; i < l; i++)
            for (int j = 0; j < c; j++)
                set(i, j, values[i][j]);
    }

    public Mat slice(int l1, int c1, int l2, int c2) {
        int size = Math.max(Math.abs(l1 - l2), Math.abs(c2 - c1));
        Mat v = new Mat(l2 - l1 + 1, c2 - c1 + 1);
        double[] val = new double[size + 1];
        int incr1 = 0;
        int incr2 = 0;
        incr1 = (int) Math.signum(l2 - l1);
        incr2 = (int) Math.signum(c2 - c1);

        int i = 0, j = 0;
        for (int l = l1; l < l2; l += incr1) {
            for (int c = c1; c < c2; c += incr2) {
                v.set(i, j, get(l, c));
                i++;
            }
            j++;
        }
        return v;
    }


    public Mat product(Mat mat) {
        Mat res = new Mat(this.l, mat.c);
        if (c != mat.l)
            return new Mat(-1, -1);
        for (int j = 0; j < this.l; j++)
            for (int k = 0; k < mat.c; k++) {
                for (int i = 0; i < this.c; i++) {
                    res.set(i, k, res.get(i, k) + get(j, i) * mat.get(i, j));
                }
            }
        return res;
    }


    public double determinant() {
        Mat mat = this;
        if (c < l) {
            mat = new Mat(c, c);
            for (int i = 0; i < c; i++)
                for (int j = 0; j < c; j++)
                    mat.set(i, j, get(i, j));
        } else if (l < c) {
            mat = new Mat(l, l);
            for (int i = 0; i < l; i++)
                for (int j = 0; j < l; j++)
                    mat.set(i, j, get(i, j));
        }
        return determinantOfMatrix(mat);
    }

    public double get(int i, int j) {
        return values.getElem(i, j) != null ? values.getElem(i, j) : 0;
    }

    public double get(int i) {
        return values.getElem(i);
    }

    public double get() {
        return values.getElem();
    }


    public double set(int i, int j, double v) {
        values.setElem(v, i, j);
        return v;
    }


// Function to get cofactor of mat[p][q] in temp[][]. n is current
// dimension of mat[][] 

    void getCofactor(double[][] temp, int p, int q, int n) {

        int i = 0, j = 0;


        // Looping for each element of the matrix

        for (int row = 0; row < n; row++) {

            for (int col = 0; col < n; col++) {

                //  Copying into temporary matrix only those element

                //  which are not in given row and column

                if (row != p && col != q) {

                    temp[i][j++] = get(row, col);


                    // Row is filled, so increase row index and

                    // reset col index

                    if (j == n - 1) {

                        j = 0;

                        i++;

                    }

                }

            }

        }
    }


    final int Nmax = 7;

    double determinantOfMatrix(Mat mat) {
        if (mat == null)
            mat = this;

        int N = Nmax;
        int n = l;
        int D = 0; // Initialize result


        //  Base case : if matrix contains single element

        if (n == 1)

            return get(0, 0);


        double[][] temp = new double[N][N]; // To store cofactors


        int sign = 1;  // To store sign multiplier


        // Iterate for each element of first row

        for (int f = 0; f < n; f++) {

            // Getting Cofactor of mat[0][f]

            getCofactor(temp, 0, f, n);

            Mat mat1 = new Mat(temp);

            D += sign * get(0, f) * mat1.determinant();


            // terms are to be added with alternate sign

            sign = -sign;

        }


        return D;
    }
}
