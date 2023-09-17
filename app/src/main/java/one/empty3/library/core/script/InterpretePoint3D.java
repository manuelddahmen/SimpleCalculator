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
package one.empty3.library.core.script;

import one.empty3.library.*;

import java.util.ArrayList;

public class InterpretePoint3D implements Interprete {

    private String repertoire;

    private InterpreteConstants c;

    private int pos;

    @Override
    public InterpreteConstants constant() {
        return c;
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public Object interprete(String point, int pos) throws InterpreteException {
        try {
            InterpretesBase ib = new InterpretesBase();
            ArrayList<Integer> pattern = new ArrayList<>();
            pattern.add(ib.BLANK);
            pattern.add(ib.LEFTPARENTHESIS);
            pattern.add(ib.BLANK);
            pattern.add(ib.DECIMAL);
            pattern.add(ib.BLANK);
            pattern.add(ib.COMA);
            pattern.add(ib.BLANK);
            pattern.add(ib.DECIMAL);
            pattern.add(ib.BLANK);
            pattern.add(ib.COMA);
            pattern.add(ib.BLANK);
            pattern.add(ib.DECIMAL);
            pattern.add(ib.BLANK);
            pattern.add(ib.RIGHTPARENTHESIS);
            pattern.add(ib.BLANK);

            ib.compile(pattern);
            ArrayList<Object> os = null;
            os = ib.read(point, pos);
            this.pos = ib.getPosition();

            return new Point3D((Double) os.get(3), (Double) os.get(7),
                    (Double) os.get(11));
        } catch (Exception ex1) {
            throw new InterpreteException(ex1);
        }

    }

    @Override
    public void setConstant(InterpreteConstants c) {
        this.c = c;
    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }
}
