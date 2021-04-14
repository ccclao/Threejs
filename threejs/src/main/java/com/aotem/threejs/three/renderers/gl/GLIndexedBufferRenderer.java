package com.aotem.threejs.three.renderers.gl;

import android.opengl.GLES30;

import com.aotem.threejs.three.core.BufferGeometry;
import com.aotem.threejs.three.core.InstancedBufferGeometry;

public class GLIndexedBufferRenderer extends GLBufferRenderer {
    int type;
    int bytesPerElement;

    public GLIndexedBufferRenderer(GLInfo glInfo) {
        super(glInfo);
    }

    public void setIndex(GLAttributes.BufferItem value) {
        type = value.type;
        bytesPerElement = value.bytesPerElement;
    }

    public void render(int start, int count) {
        GLES30.glDrawElements(mode, count, type, start * bytesPerElement);
        info.update(count, mode, 0);
    }

    public void renderInstances(int start, int count, int primcount) {
        GLES30.glDrawElementsInstanced(mode, count, type, start * bytesPerElement, primcount);
        info.update(count, mode, primcount);
    }

    public void renderInstances(BufferGeometry geometry, int start, int count) {
        GLES30.glDrawElementsInstanced(mode, count, type, start * bytesPerElement, ((InstancedBufferGeometry)geometry).maxInstancedCount);
        info.update(count, mode, ((InstancedBufferGeometry)geometry).maxInstancedCount);
    }
}
