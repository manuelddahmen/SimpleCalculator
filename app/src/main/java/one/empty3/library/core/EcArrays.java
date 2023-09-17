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

package one.empty3.library.core;

import java.util.Arrays;

/*__
 * Created by manue on 24-07-19.
 */
public class EcArrays<T>
{
    public T[] deleteRowAtDim1(T[] array, int pos)
    {
        T[] ts = Arrays.copyOf(array, array.length-1);
        int i0=0;
        for(int i=0; i<ts.length; i++) {
            if(pos!=i)
            {
                ts[i] = array[i0];
                i0++;
            }
        }
        return ts;
    }
    public T[][] deleteRowAtDim2(T[][] array, int pos)
    {
        T[][] ts = Arrays.copyOf(array, array.length-1);
        int i0=0;
        for(int i=0; i<ts.length; i++) {
            if(pos!=i)
            {
                ts[i] = array[i0];
                i0++;
            }
        }
        return ts;
    }
    public T[][] deleteColAtDim2(T[][] array, int pos)
    {
        T[][] ts = Arrays.copyOf(array, array.length);
        for(int i=0; i<array.length;i++)
        {
            ts[i] = deleteRowAtDim1(array[i], pos);
        }
        return ts;
    }

    public T[] insertRowAtDim1(T[] array, int pos, T value)
    {
        T[] ts = Arrays.copyOf(array, array.length+1);
        int i0=0;
        for(int i=0; i<ts.length; i++) {
            if(pos!=i)
            {
                ts[i] = array[i0];
                i0++;
            }
            else {
                ts[i] = value;
            }
        }
        return ts;
    }
    public T[][] insertRowAtDim2(T[][] array, int pos, int rowSize)
    {
        T[][] ts = Arrays.copyOf(array, array.length+1);
        int i0= 0;
        for(int i=0; i<ts.length;i++)
        {
            if(i!=pos) {
                ts[i] = array[i0];
                i0++;
            }
            else {
                ts[i] = Arrays.copyOf(array[0], array[0].length);
            }
        }
        return ts;
    }
    public T[][] insertColAtDim2(T[][] array, int pos)
    {
        T[][] ts = Arrays.copyOf(array, array.length);

        for(int i=0; i<array.length; i++)
        {
            ts[i] = insertRowAtDim1(array[i], pos, null);
        }
        return ts;
    }


}
