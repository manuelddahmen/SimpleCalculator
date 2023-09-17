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

/*__
 * Created by manuel Dahmen manuel.dahmen@gmail.com on 08-10-15.
 */

/*__
 * Destinée à concevoir des actions de dessin spécifiques à certaines formes d'un type général Representable.
 * <p/>
 * Par exemple : au lieu de tracer un maillage de surface (action par défaut) dessiner des points aléatoirement
 * sur la surface.
 */
public class Painter {
    private final ZBuffer z;
    private Scene scene;
    private PaintingAct pa;
    private Representable r;
    private Class c;

    public Painter(ZBuffer z, Scene scene) {
        this.z = z;

        this.scene = scene;
    }

    public Painter(ZBuffer z, Scene scene, Representable r) {
        this.z = z;
        this.r = r;
        this.c = r.getClass();
        this.scene = scene;
    }

    public Painter(ZBuffer z, Scene scene, Class<Representable> c) {
        this.z = z;
        this.c = c;
        this.scene = scene;
    }

    public void addAction(PaintingAct pa) {
        this.pa = pa;
        pa.setObjet(r);
        pa.setZBuffer(z);
        pa.setScene(scene);
    }

    public PaintingAct getPaintingAct() {
        return pa;
    }
}
