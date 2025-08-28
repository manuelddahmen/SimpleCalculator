package one.empty3.apps.simplecalculator

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLES20 // Or GLES30, GLES31 etc.
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MyActivity : AppCompatActivity() {

    private lateinit var gLView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        gLView = GLSurfaceView(this)
        gLView.setEGLContextClientVersion(2) // Use OpenGL ES 2.0
        gLView.setRenderer(MyGLRenderer()) // Set your renderer
        setContentView(gLView) // Set the view for your activity
    }
}

// The renderer is where all the drawing happens
class MyGLRenderer : GLSurfaceView.Renderer {

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // Called once to set up the view's OpenGL ES environment.
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f) // Black background
    }

    override fun onDrawFrame(gl: GL10?) {
        // Called for each redraw of the view.
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // Your drawing code (drawing shapes, textures) goes here
        // It MUST use GLES20.* or GLES30.* calls, not JOGL.
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        // Called if the geometry of the view changes,
        // such as when the device's screen orientation changes.
        GLES20.glViewport(0, 0, width, height)
    }
}