package com.aotem.threejsdemo;

import android.opengl.GLSurfaceView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import com.aotem.threejsdemo.renderer.CanvasTextureDemo;
import com.aotem.threejsdemo.renderer.InstancingDemo;
import com.aotem.threejsdemo.renderer.InstancingSegments;
import com.aotem.threejsdemo.renderer.LambertPhongLightRender;
import com.aotem.threejsdemo.renderer.RaycastCameraControlView;
import com.aotem.threejsdemo.renderer.SpriteDemo;
import com.aotem.threejsdemo.renderer.SpriteTextDemo;
import com.aotem.threejsdemo.renderer.TestPerformanceView;
import com.aotem.threejsdemo.renderer.TestTubeGeometry;
import com.aotem.threejsdemo.renderer.TextureDemo;
import com.aotem.threejs.three.objects.Sprite;

public class MainListItems {
    static ArrayList<Item> ITEMS = new ArrayList<Item>();
    static private HashMap<Class, Item> ITEM_MAP = new HashMap<>();
    static {
        addItem(new Item(RaycastCameraControlView.class, "光线碰撞，摄像机的轨迹球控制，法线材质，线框材质"));
        addItem(new Item(LambertPhongLightRender.class, "lambert材质, phong材质, 光照及阴影"));
        addItem(new Item(TextureDemo.class, "带纹理的立方体"));
        addItem(new Item(CanvasTextureDemo.class, "带canvas纹理的立方体"));
        addItem(new Item(SpriteDemo.class, "Sprite demo"));
        addItem(new Item(SpriteTextDemo.class, "Sprite text demo"));
        addItem(new Item(TestPerformanceView.class, "性能测试"));
        addItem(new Item(TestTubeGeometry.class, "管道几何体测试"));
        addItem(new Item(InstancingDemo.class, "实例化"));
        addItem(new Item(InstancingSegments.class, "实例化的线段"));
    }

    public static class Item {
        public Class className;
        public String content;

        public Item(Class className, String content) {
            this.className = className;
            this.content = content;
        }
        public String toString() {
            return content;
        }
    }

    static private void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.className, item);
    }

    static public int getIndex(Class className) {
        return ITEMS.indexOf(ITEM_MAP.get(className));
    }

    static public Class getClass(int index) {
        return ITEMS.get(index).className;
    }

    static public GLSurfaceView.Renderer getRenderer(Class className, GLSurfaceView context) {
        try {
            Constructor constructor = className.getConstructor(context.getClass());
            return (GLSurfaceView.Renderer) constructor.newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
