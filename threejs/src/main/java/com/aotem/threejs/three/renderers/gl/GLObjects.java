package com.aotem.threejs.three.renderers.gl;

import android.opengl.GLES20;
import android.opengl.GLES30;

import java.util.HashMap;

import com.aotem.threejs.three.core.BufferGeometry;
import com.aotem.threejs.three.core.EventDispatcher;
import com.aotem.threejs.three.core.Geometry;
import com.aotem.threejs.three.core.Object3D;
import com.aotem.threejs.three.objects.InstancedMesh;

public class GLObjects {
    GLGeometries geometries;
    GLInfo info;
    HashMap<Long, Integer> updateList = new HashMap<>();
    GLAttributes attributes;

    public GLObjects(GLGeometries glGeometries, GLAttributes attributes, GLInfo glInfo) {
        geometries = glGeometries;
        this.attributes = attributes;
        info = glInfo;
    }

    public BufferGeometry update(Object3D object) {
        int frame = info.frame;
        EventDispatcher geometry = object.geometry;
        BufferGeometry bufferGeometry = geometries.get(object, geometry);

        // Update once per frame
        if (!updateList.containsKey(bufferGeometry.id) || updateList.get(bufferGeometry.id) != frame) {
            if (geometry instanceof Geometry) {
                bufferGeometry.updateFromObject(object);
            }
            geometries.update(bufferGeometry);
            updateList.put(bufferGeometry.id, frame);
        }

        if (object instanceof InstancedMesh) {
            InstancedMesh iMesh = (InstancedMesh) object;
            attributes.update(iMesh.instanceMatrix, GLES20.GL_ARRAY_BUFFER);
            if (((InstancedMesh) object).instanceColor != null) {
                attributes.update(iMesh.instanceColor, GLES20.GL_ARRAY_BUFFER);
            }
        }
        return bufferGeometry;
    }

    public void dispose() {
        updateList.clear();
    }
}
