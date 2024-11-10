/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package javaAnd.awt;

public class Color /*android.graphics.Color*/ {

    public static final int TRANSLUCENT = 0;

    public Color(android.graphics.Color color) {
    }



    public static android.graphics.Color color(int colorAt) {
        return android.graphics.Color.valueOf(colorAt);
    }

    public static android.graphics.Color color(double v, double v1, double v2) {
        return android.graphics.Color.valueOf((float) v, (float) v1, (float) v2);

    }

    public static android.graphics.Color random() {
        android.graphics.Color random = color((float) Math.random(), (float) Math.random(), (float) Math.random());
        return random;
    }

    public static android.graphics.Color color(float r, float r1, float r2) {
        return android.graphics.Color.valueOf(r, r1, r2);
    }

    public static android.graphics.Color color(int r, int g, int b) {

        return android.graphics.Color.valueOf(r, g, b);
    }


    public static float[] getColorComponents(android.graphics.Color rgba) {
        float[] aRgba = new float[]{rgba.red(), rgba.green(), rgba.blue(), rgba.alpha()};
        return aRgba;
    }

    public static int intConv(android.graphics.Color color) {
        return color.toArgb();
    }

    public static float[] intConvToFloatArray(int color) {
        android.graphics.Color color1 = android.graphics.Color.valueOf(color);
        return new float[]{color1.red(), color1.green(), color1.blue()};
    }

    public static int floatArrayConvToIntcolorComponents(float[] colorComponents) {
        return android.graphics.Color.valueOf(colorComponents[0], colorComponents[1], colorComponents[2]).toArgb();
    }

    public android.graphics.Color Color(int rgb) {
        return android.graphics.Color.valueOf(rgb);
    }

}
