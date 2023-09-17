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

package one.empty3.library.core.nurbs;

/*__
 * Created by manue on 28-05-19.
 */
public abstract class Fct2D_1D {
    public class Ix extends Fct2D_1D
    {

        public Ix()
        {

        }

        @Override
        public double result(double input, double input2) {
            return input;
        }
    }
    public class Iy extends Fct2D_1D
    {

        public Iy()
        {

        }

        @Override
        public double result(double input, double input2) {
            return input2;
        }
    }
    public abstract double result(double input1, double input2);
}
