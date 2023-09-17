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

package one.empty3.library.objloader;

import com.jogamp.opengl.GL2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * Image loading class that converts BufferedImages into a data structure that
 * can be easily passed to OpenGL.
 * 
 * @author Pepijn Van Eeckhoudt Downloaded from:
 *         http://www.felixgers.de/teaching/jogl/
 */

// Uses the class GLModel from JautOGL to load and display obj files.
public class ModelLoaderOBJ {
	
	/*
	public void init(GL2 gl) {
		String path1 = "W:\\nauka\\msc\\gk\\asemalaituri\\spot.obj";
		try {

			FileInputStream r_path1 = new FileInputStream(path1);
			BufferedReader b_read1 = new BufferedReader(new InputStreamReader(
					r_path1));
			model1 = new GLModel(b_read1, true,
					"W:\\nauka\\msc\\gk\\asemalaituri\\spot.mtl", gl);
			r_path1.close();
			b_read1.close();

		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.INFO, "LOADING ERROR" + e);
		}

		Logger.getAnonymousLogger().log(Level.INFO, "ModelLoaderOBJ init() done"); // ddd
	}

	public void draw(GL2 gl) {
		gl.glPushMatrix();
		model1.opengldraw(gl);
		gl.glPopMatrix();

	}*/
	
	public static GLModel LoadModel(String objPath, String mtlPath, GL2 gl)
	{
		GLModel model = null;
		try {
			FileInputStream r_path1 = new FileInputStream(objPath);
			BufferedReader b_read1 = new BufferedReader(new InputStreamReader(
					r_path1));
			model = new GLModel(b_read1, true,
					mtlPath, gl);
			r_path1.close();
			b_read1.close();

		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.INFO, "LOADING ERROR" + e);
		}

		Logger.getAnonymousLogger().log(Level.INFO, "ModelLoaderOBJ init() done"); // ddd
		return model;
	}
	public static E3Model LoadModelE3(String objPath, String mtlPath)
	{
		E3Model model = null;
		try {
			FileInputStream r_path1 = new FileInputStream(objPath);
			BufferedReader b_read1 = new BufferedReader(new InputStreamReader(
					r_path1));
			model = new E3Model(b_read1, true,
					mtlPath);
			r_path1.close();
			b_read1.close();

		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.INFO, "LOADING ERROR" + e);
			e.printStackTrace();
		}
		Logger.getAnonymousLogger().log(Level.INFO, "ModelLoaderOBJ init() done"); // ddd
		return model;
	}
}
