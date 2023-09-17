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

/*__
 * Global license :
 * <p>
 * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 ***/


package one.empty3.library.core.testing;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class Gimbal {
    public static final double CIRCLE = 2 * Math.PI;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int XYZ = 3;
    private final int dim;
    private double value;

    public Gimbal(int i) {
        this.dim = i;
    }

    public void changeValue(double value) {

        this.value = Math.IEEEremainder(2 * Math.PI, value);

    }

    @Override
    public String toString() {
        return "Gimball (\ndim:" + dim + " val:" + value + "\n)rad\n";
    }


}
