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

import one.empty3.library.Point3D;

/*__
 * Created by manue on 30-07-19.
 */
public class P3Matrix  {
    private Point3D[][] points;

    public P3Matrix()
    {

    }
    public P3Matrix(int dims, int dim1, int dim2)
    {
        if(dims==1)
        {
            points = new Point3D[1][dim1];
        }
        else if(dims==2)
        {
            points = new Point3D[dim2][dim1];
        }
    }

    public void insert(int pos1, int pos2, int dim)  {
        Point3D[][] copy = null;
        if(dim==1)
        {
            copy = new Point3D[points.length+1][points[0].length];
        }
        else if(dim==2)
        {
            copy = new Point3D[points.length][points[0].length+1];
        }
        else
        {}
        for(int i=0; i<points.length; i++)
        {
            for(int j=0; j<points[i].length; j++)
            {
                copy[i][j] = copy[i][j];
                if(pos1==i)
                    i++;
                else if(pos2==j)
                    j++;
            }
        }
        if(copy!=null)
        {
            points = copy;
        }
    }
}
