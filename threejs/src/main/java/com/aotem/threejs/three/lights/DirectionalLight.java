package com.aotem.threejs.three.lights;

import com.aotem.threejs.three.core.Object3D;
import com.aotem.threejs.three.math.Vector3;

public class DirectionalLight extends Light {
    public Object3D target = new Object3D();
    public DirectionalLightShadow shadow = new DirectionalLightShadow();

    public DirectionalLight() {
        this(0xffffff);
    }
    public DirectionalLight(int color) {
        this.color = color;
        position.copy(Object3D.DefaultUp);
        updateMatrix();
    }

    public DirectionalLight setIntensity(float val) {
        intensity = val;
        return this;
    }

    @Override
    public Light copy(Light source) {
        super.copy(source);
        DirectionalLight light = (DirectionalLight) source;
        target = light.target.clone();
        shadow = (DirectionalLightShadow) light.shadow.clone();
        return this;
    }

    public LightShadow getShadow() {
        return shadow;
    }

}
