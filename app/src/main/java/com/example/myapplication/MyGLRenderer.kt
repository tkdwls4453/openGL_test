package com.example.myapplication

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet.Motion
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer : GLSurfaceView.Renderer {


    private lateinit var ground: Square
    private lateinit var mOctagon: Octagon

    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    private val loadCoords = arrayOf(
        floatArrayOf(
            127.757f,  107.388f, 0.0f,      // top left
            154.289f, 80.936f, 0.0f,      // bottom left
            154.974f, 82.786f, 0.0f,      // bottom right
            129.757f, 108.218f, 0.0f       // top right
        ),
        floatArrayOf(
            127.757f, 211.885f, 0.0f,      // top left
            127.757f,  107.388f, 0.0f,      // bottom left
            129.757f, 108.218f, 0.0f,       // bottom right
            129.607f, 211.118f, 0.0f       // top right
        ),
        floatArrayOf(
            154.208f, 238.336f, 0.0f,      // top left
            127.757f, 211.885f, 0.0f,      // bottom left
            129.607f, 211.118f, 0.0f,       // bottom right
            154.974f, 236.486f, 0.0f       // top right
        ),
        floatArrayOf(
            154.208f, 238.336f, 0.0f,      // top left
            154.974f, 236.486f, 0.0f,      // bottom left
            295.139f, 236.486f, 0.0f,       // bottom right
            295.824f, 238.336f, 0.0f       // top right
        ),
        floatArrayOf(
            295.824f, 238.336f, 0.0f,      // top left
            295.139f, 236.486f, 0.0f,      // bottom left
            320.507f, 211.118f, 0.0f,       // bottom right
            322.357f, 211.885f, 0.0f       // top right
        ),
        floatArrayOf(
            295.824f, 238.336f, 0.0f,      // top left
            295.139f, 236.486f, 0.0f,      // bottom left
            320.507f, 211.118f, 0.0f,       // bottom right
            322.357f, 211.885f, 0.0f       // top right
        ),
        floatArrayOf(
            320.507f, 211.118f, 0.0f,      // top left
            320.507f, 108.154f, 0.0f,      // bottom left
            322.357f, 107.469f, 0.0f,       // bottom right
            322.357f, 211.885f, 0.0f       // top right
        ),
        floatArrayOf(
            320.507f, 108.154f, 0.0f,      // top left
            295.139f, 82.786f, 0.0f,      // bottom left
            295.905f, 80.936f, 0.0f,       // bottom right
            322.357f, 107.469f, 0.0f       // top right
        ),
        floatArrayOf(
            154.974f, 82.786f, 0.0f,      // top left
            154.289f, 80.936f, 0.0f,      // bottom left
            295.905f, 80.936f, 0.0f,       // bottom right
            295.139f, 82.786f, 0.0f       // top right
        )

    )

    private val octagonCoords = floatArrayOf(
        90.551f,  87.437f, 0.0f,
        90.551f, 231.836f, 0.0f,
        134.257f, 275.542f, 0.0f,
        315.856f, 275.542f, 0.0f,
        359.562f, 231.836f, 0.0f,
        359.562f, 87.437f, 0.0f,
        315.856f, 43.731f, 0.0f,
        134.257f, 43.731f, 0.0f
    )

    private val groundCoords = floatArrayOf(
        172.557f, 193.636f, 0.0f,
        172.557f, 125.636f, 0.0f,
        277.557f, 125.636f, 0.0f,
        277.557f, 193.636f, 0.0f,
    )

    private val loadList = ArrayList<Square>()

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
//        mSquare = Square(element)
        for(element in loadCoords){
            loadList.add(Square(element));
        }
        mOctagon = Octagon(octagonCoords)
        ground = Square(groundCoords)
    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        GLES20.glViewport(0, 0, p1, p2)

        val ratio: Float = p1.toFloat() / p2.toFloat()

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 300000f)


    }

    override fun onDrawFrame(p0: GL10?) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 225.057f, 159.636f , 1000f - distance, 225.057f + moveX, 159.636f+ moveY, 0f, 0f, 1.0f, 0.0f)


        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        var octagonColor = floatArrayOf(0.9f, 0.9f, 0.9f, 1.0f)
        mOctagon.draw(vPMatrix,octagonColor)
        var groundColor = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
        ground.draw(vPMatrix, groundColor)
        var loadColor = floatArrayOf(0.6f, 0.6f, 0.6f, 1.0f)
        for(load in loadList){
            load.draw(vPMatrix, loadColor)
        }
        GLES20.glFlush()
    }
}