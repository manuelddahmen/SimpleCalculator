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

package one.empty3.library.core.raytracer;

import java.awt.*;

public class RtColor {
    public double red, green, blue, alpha;    // Les trois composantes de la couleur
    RtColor c;

    // constructeurs et destructeur
    public RtColor() {
        red = 0;
        green = 0;
        blue = 0;
        alpha = 0;
    }

    public RtColor(double r, double g, double b) {
        red = r;
        green = g;
        blue = b;
        alpha = 0;

    }

    public RtColor(double r, double g, double b, double a) {
        red = r;
        green = g;
        blue = b;
        alpha = a;

    }

    public RtColor(Color color) {
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        alpha = color.getAlpha();

    }

    // operateurs
    public static RtColor mult(RtColor c1, RtColor c2) {
        return new RtColor(c1.getRed() * c2.getRed(), c1.getGreen() * c2.getGreen(), c1.getBlue() * c2.getBlue());
    }

    public static RtColor mult(RtColor c1, double multiple) {
        return new RtColor(c1.getRed() * multiple, c1.getGreen() * multiple, c1.getBlue() * multiple);
    }

    public static RtColor add(RtColor c1, RtColor c2) {
        return new RtColor(c1.getRed() + c2.getRed(),
                c1.getGreen() + c2.getGreen(),
                c1.getBlue() + c2.getBlue()
        );
    }

    public static RtColor plus(RtColor c1, RtColor c2) {
        return new RtColor(c1.getRed() + c2.getRed(), c1.getGreen() + c2.getGreen(), c1.getBlue() + c2.getBlue());
    }

    public static RtColor div(RtColor c1, float multiple) {
        return new RtColor(c1.getRed() / multiple, c1.getGreen() / multiple, c1.getBlue() / multiple);
    }


    public static RtColor normalizeColor(RtColor finalColor) {
        double max = Math.max(finalColor.getRed(), Math.max(finalColor.getGreen(), Math.max(finalColor.getBlue(), finalColor.getAlpha())));
        if (max > 1.0f || max < 0.0f) {
            finalColor = RtColor.mult(finalColor, 1 / max);
        }/*
        if (finalColor.getRed() > 1.0f)
            finalColor.red = 1.0f;
        if (finalColor.getGreen() > 1.0f)
            finalColor.green = 1.0f;
        if (finalColor.getBlue() > 1.0f)
            finalColor.blue = 1.0f;
        if (finalColor.getRed() < .0f)
            finalColor.red = .0f;
        if (finalColor.getGreen() < .0f)
            finalColor.green = .0f;
        if (finalColor.getBlue() < .0f)
            finalColor.blue = .0f;
        */
        return finalColor;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public double getAlpha() {
        return alpha;
    }

    public Color toColor() {
        RtColor c = normalizeColor(this);
        return new Color((float) c.getRed(), (float) c.getGreen(), (float) c.getBlue(), (float) c.getAlpha());
    }


}
