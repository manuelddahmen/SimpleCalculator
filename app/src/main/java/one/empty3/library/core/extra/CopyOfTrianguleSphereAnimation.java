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

/*

 Vous êtes libre de :

 */
package one.empty3.library.core.extra;

/*__
 * @author MANUEL DAHMEN
 * <p>
 * dev
 * <p>
 * 28 déc. 2011
 */
/*
 public class CopyOfTrianguleSphereAnimation extends Animation {
 private TRIObject o;
 int n = 2;

 public CopyOfTrianguleSphereAnimation(Scene s) {
 super(s);
 }

 public void modifier() {
 o = new TRIObject();
		
 Point3D lumiere = new Point3D(0,0,1);
		
		
		
 double a = 0, b = 0, R = 10;
 n++;
 Point3D[][] pcurrent = null;
 if (n > 1) {
 pcurrent = new Point3D[n][n];

 int i = 0;
 for (b = 0; b < 2*Math.PI; b += 2*Math.PI / n) {
 int j = 0;
 for (a = -Math.PI; a < Math.PI; a += Math.PI / n) {
 if (i < n && j < n)
 pcurrent[i][j] = new Point3D(R * Math.cos(a)
 * Math.cos(b), R * Math.cos(a) * Math.sin(b), R
 * Math.sin(a));
 j++;

 }
 i++;
 }
 }

 for (int i1 = 0; i1 < n; i1++)
 for (int j = 0; j < n; j++) {
 o.add(new TRI(pcurrent[(i1) % n][(j) % n],
 pcurrent[(i1) % n][(j + 1) % n],
 pcurrent[(i1 + 1) % n][(j + 1) % n], Color.blue));
				
 o.add(new TRI(pcurrent[(i1 + 1) % n][(j) % n],
 pcurrent[(i1 + 1) % n][(j) % n],
 pcurrent[(i1 + 1) % n][(j + 1) % n], Color.red));
 }
 Scene s = new Scene();
 s.add(o);
 scene(s);
 }

 }
 */
