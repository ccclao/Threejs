package com.aotem.threejs.three.lights;

import com.aotem.threejs.three.core.Object3D;

public class Light extends Object3D {
    public int color;
    public float intensity = 1;

    public Light copy(Light light) {
        color = light.color;
        intensity = light.intensity;
        return this;
    }

    public LightShadow getShadow() {
        return null;
    }
}
