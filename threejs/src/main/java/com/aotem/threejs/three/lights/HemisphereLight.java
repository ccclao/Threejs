package com.aotem.threejs.three.lights;

import com.aotem.threejs.three.core.Object3D;
import com.aotem.threejs.three.math.Vector3;

public class HemisphereLight extends Light {
    public boolean castShadow;
    public Vector3 position = Object3D.DefaultUp.clone();
    public int groundColor;
    public int skyColor;

    public HemisphereLight(int groundColor, int skyColor, float intensity) {
        this.groundColor = groundColor;
        this.skyColor = skyColor;
        this.intensity = intensity;
    }

    public HemisphereLight copy(HemisphereLight light) {
        super.copy(light);
        groundColor = light.groundColor;
        skyColor = light.skyColor;
        return this;
    }
}
