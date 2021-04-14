package com.aotem.threejs.three.objects;

import java.util.ArrayList;

import com.aotem.threejs.three.core.BufferAttribute;
import com.aotem.threejs.three.core.BufferGeometry;
import com.aotem.threejs.three.core.Geometry;
import com.aotem.threejs.three.materials.Material;
import com.aotem.threejs.three.math.Color;
import com.aotem.threejs.three.math.Matrix4;

public class InstancedMesh extends Mesh {

  public BufferAttribute instanceMatrix;
  public BufferAttribute instanceColor = null;
  public int count;
  public boolean frustumCulled = false;

  public InstancedMesh(BufferGeometry geometry, Material material, int count) {
    super(geometry, material);
    this.count = count;
    instanceMatrix = new BufferAttribute(new float[count*16], 16);
  }

  public Color getColorAt(int index, Color color) {
    return color.fromArray(instanceColor.arrayFloat, index*3);
  }

  public Matrix4 getMatrixAt(int index, Matrix4 matrix) {
    return matrix.fromArray(instanceMatrix.arrayFloat, index*16);
  }

  public void setColorAt(int index, Color color) {
    if (instanceColor == null) {
      instanceColor = new BufferAttribute(new float[count * 3], 3);
    }
    color.toArray(instanceColor.arrayFloat, index*3);
  }

  public void setMatrixAt(int index, Matrix4 matrix) {
    matrix.toArrayF(instanceMatrix.arrayFloat, index*16);
  }
}
