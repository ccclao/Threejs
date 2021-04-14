package com.aotem.threejs.three.renderers.gl;

import android.opengl.GLES30;

import com.aotem.threejs.three.core.BufferGeometry;
import com.aotem.threejs.three.core.InstancedBufferGeometry;

public class GLBufferRenderer {
    int mode;
    GLInfo info;

    public GLBufferRenderer(GLInfo glInfo) {
        info = glInfo;
    }

    public void setMode(int value) {
        mode = value;
    }

    public void render(int start, int count) {
        GLES30.glDrawArrays(mode, start, count);
        info.update(count, mode, 0);
    }

    public void renderInstances(int start, int count, int primcount) {
        GLES30.glDrawArraysInstanced(mode, start, count, primcount);
        info.update(count, mode, primcount);
    }

    public void renderInstances(BufferGeometry geometry, int start, int count) {
        GLES30.glDrawArraysInstanced(mode, start, count, ((InstancedBufferGeometry)geometry).maxInstancedCount);
        info.update(count, mode, ((InstancedBufferGeometry)geometry).maxInstancedCount);
    }
}
