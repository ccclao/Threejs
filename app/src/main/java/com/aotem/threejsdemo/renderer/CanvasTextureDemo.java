package com.aotem.threejsdemo.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.aotem.threejs.R;
import com.aotem.threejs.three.buffergeometries.BoxBufferGeometry;
import com.aotem.threejs.three.cameras.PerspectiveCamera;
import com.aotem.threejs.three.constant.Constants;
import com.aotem.threejs.three.control.Screen;
import com.aotem.threejs.three.control.TrackballControls;
import com.aotem.threejs.three.core.BufferGeometry;
import com.aotem.threejs.three.geometries.param.BoxParam;
import com.aotem.threejs.three.helpers.AxesHelper;
import com.aotem.threejs.three.loaders.TextureLoader;
import com.aotem.threejs.three.materials.Material;
import com.aotem.threejs.three.materials.MeshBasicMaterial;
import com.aotem.threejs.three.math.Vector3;
import com.aotem.threejs.three.objects.Mesh;
import com.aotem.threejs.three.renderers.GLRenderer;
import com.aotem.threejs.three.scenes.Scene;
import com.aotem.threejs.three.textures.Texture;

import static com.aotem.threejs.three.constant.Constants.RGBAFormat;
import static com.aotem.threejs.three.constant.Constants.RGBFormat;

public class CanvasTextureDemo extends BaseRender {
  private String TAG = getClass().getSimpleName();
  private PerspectiveCamera camera;
  private Scene scene = new Scene();

  private static final float Z_NEAR = 0.1f;
  private static final float Z_FAR = 1000f;

  class PointXY {
    float x, y;

    public PointXY(float x, float y) {
      this.x = x;
      this.y = y;
    }
  }
  //
  public CanvasTextureDemo(GLSurfaceView view) {
    super(view);
  }

  Bitmap drawLocationMarker(float radius, float lineWidth, int color) {
    int width, height;
    width = height = (int) ((radius + lineWidth) * 4);
    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    int hw = width / 2;
    PointXY p0 = new PointXY(radius+lineWidth*2, hw);
    PointXY p1 = new PointXY(3*radius+lineWidth*2, hw);
    PointXY p2 = new PointXY(hw, lineWidth);
    Path path = new Path();
    path.moveTo(p0.x, p0.y);
    path.lineTo(p1.x, p1.y);
    path.lineTo(p2.x, p2.y);
    path.close();
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setDither(true);
    paint.setColor(color);
    paint.setStyle(Paint.Style.FILL);
    canvas.drawPath(path, paint);

    canvas.drawCircle(hw, hw, radius, paint);
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(Color.WHITE);
    paint.setStrokeWidth(lineWidth);
    canvas.drawCircle(hw, hw, radius, paint);
    return bitmap;
  }
  ///
  // Initialize the shader and program object
  //
  public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    mWidth = mView.getWidth();
    mHeight = mView.getHeight();
    aspect = (float) mWidth / mHeight;

    scene = new Scene();
    GLRenderer.Param param = new GLRenderer.Param();
    param.antialias = true;
    renderer = new GLRenderer(param, mWidth, mHeight);
    renderer.setClearColor(0x000000, 1);

    Bitmap bitmap = drawLocationMarker(25, 10, 0xff365cbb);
    Texture texture = new Texture();
    texture.setImage(bitmap);
//    texture.format = RGBAFormat;  //RGBFormat

//    Texture texture = new TextureLoader().loadTexture(mView.getContext(), R.raw.t_crate);
//
    BufferGeometry geometry = new BoxBufferGeometry(new BoxParam(6, 6, 6));
    Material material = new MeshBasicMaterial();
    material.map = texture;
    material.transparent = true;
    material.side = Constants.DoubleSide;

    Mesh mesh = new Mesh(geometry, material);
    scene.add(mesh);

    camera = new PerspectiveCamera(45, aspect, Z_NEAR, Z_FAR);
    camera.position = new Vector3(0, 0, 30);
    camera.lookAt(scene.position);
    controls = new TrackballControls(new Screen(0, 0, mWidth, mHeight), camera);

    AxesHelper axesHelper = new AxesHelper(20);
    scene.add(axesHelper);
  }

  // /
  // Draw a triangle using the shader pair created in onSurfaceCreated()
  //
  public void onDrawFrame(GL10 glUnused) {
    renderer.render(scene, camera);
    ((TrackballControls) controls).update();
  }

  public void onSurfaceChanged(GL10 glUnused, int width, int height) {
    mWidth = width;
    mHeight = height;
    aspect = (float) mWidth / mHeight;
    camera.aspect = aspect;
    camera.updateProjectionMatrix();
    renderer.setSize(mWidth, mHeight);
  }
}
