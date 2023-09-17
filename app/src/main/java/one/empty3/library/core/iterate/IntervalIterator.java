/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
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

package one.empty3.library.core.iterate;

/*__
 * Created by Win on 10-01-19.
 */
public class IntervalIterator {
    public int dim;
    public double[] xmin;
    public double[] xmax;
    public int[] incr;
    public int currentDim;
    public double[] currentCordinates;

    public IntervalIterator() {
    }

    /*__
     * Réfléchir surface et caméra en plus
     * pas 1 -1 et lsystem
     * @param step
     */
    public void iterate11(int[] step) {

    }

    public boolean next() throws InvalidObjectConfiguration {
        currentCordinates[currentDim] += incr[currentDim];
        if (currentCordinates[currentDim] > xmax[currentDim]) {
            currentCordinates[currentDim] = xmin[currentDim];
            currentDim++;
        }
        if (currentDim >= currentCordinates.length) {
            currentDim = 0;
            return false;
        } else {
            return true;

        }
    }
}
