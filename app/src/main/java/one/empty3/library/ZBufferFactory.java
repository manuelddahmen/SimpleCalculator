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

package one.empty3.library;

public class ZBufferFactory {

    private static ZBufferImpl insta = null;
    private static int la = -1, ha = -1;

    public static ZBufferImpl instance(int x, int y) {
        if (la == x && ha == y && insta != null) {
            return insta;
        }
        la = x;
        ha = y;
        insta = new ZBufferImpl(x, y);
        return insta;
    }

    public static ZBufferImpl instance(int x, int y, boolean D3) {
        if (la == x && ha == y && insta != null)//&& (D3 && insta instanceof ZBuffer3D || !D3))
        {
            return insta;
        }
        la = x;
        ha = y;
        if (D3) {
            // insta = new ZBuffer3DImpl(coordArr, y);
        } else {
            insta = new ZBufferImpl(x, y);
        }

        return insta;
    }

    public static ZBufferImpl instance(int x, int y, Scene s) {

        ZBufferImpl z = new ZBufferImpl(x, y);
        z.scene(s);
        return z;
    }

    public static ZBufferImpl newInstance(int resx, int resy) {
        return new ZBufferImpl(resx, resy);
    }
}
