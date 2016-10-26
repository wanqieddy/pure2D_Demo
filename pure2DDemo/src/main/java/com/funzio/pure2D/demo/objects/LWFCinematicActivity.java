/*******************************************************************************
 * Copyright (C) 2012-2014 GREE, Inc.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package com.funzio.pure2D.demo.objects;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.funzio.pure2D.Scene;
import com.funzio.pure2D.demo.activities.StageActivity;
import com.funzio.pure2D.gl.gl10.GLState;
import com.funzio.pure2D.gl.gl10.textures.Texture;
import com.funzio.pure2D.lwf.LWF;
import com.funzio.pure2D.lwf.LWFData;
import com.funzio.pure2D.lwf.LWFManager;
import com.funzio.pure2D.lwf.LWFObject;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class LWFCinematicActivity extends StageActivity implements Scene.Listener {
    private static final String TAG = LWFCinematicActivity.class.getSimpleName();

    private LWFManager mLWFManager;
    private LWFObject mLWFObject;
    private LWFData mLWFData;
    private LWF mLWF;

    private static final String[] paths = new String[]{"lwf/castle4/", "lwf/dragon/", "lwf/castle0/", "lwf/Ring/", "lwf/evolve/", "lwf/wocao/", "lwf/ball/", "lwf/paopao/"};
    private static final String[] names = new String[]{"castle4.lwf", "dragon.lwf", "castle.lwf", "Ring.lwf", "evolve.lwf", "wocao.lwf", "ball.lwf", "paopao.lwf"};

    private int index = 0;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!LWF.loadLibrary()) {
            Log.e(TAG, "ERROR: loadLibrary");
        }

        mLWFManager = new LWFManager();
        mLWFObject = new LWFObject();
        mScene.addChild(mLWFObject);
        mScene.setColor(COLOR_WHITE);
        mScene.setListener(this);
    }

    @Override
    protected void switchBtn() {
        super.switchBtn();
        runOnDraw(new Runnable() {
            @Override
            public void run() {
                index++;
                if (index > paths.length - 1) index = 0;

                mLWFObject.dispose();
                mLWFManager.dispose();
                mScene.removeChild(mLWFObject);
                mScene.getTextureManager().removeAllTextures();

                mLWFObject = new LWFObject();
                mScene.addChild(mLWFObject);

                addOneLWF(paths[index], names[index]);
            }
        });
    }

    @Override
    public void onSurfaceCreated(GLState glState, boolean firstTime) {
        runAll(mRunOnDraw);
        if (firstTime) {
            addOneLWF(paths[index], names[index]);
        }
    }

    private void addOneLWF(String path, String name) {
        try {
            InputStream stream = getAssets().open(path + name);
            //InputStream stream = getAssets().open("lwf/Ring/Ring.lwf");

            mLWFData = mLWFManager.createLWFData(stream);
        } catch (Exception e) {
            Log.e(TAG, "ERROR: " + e);
        }

        int textureNum = mLWFData.getTextureNum();
        Texture[] textures = new Texture[textureNum];
        for (int i = 0; i < textureNum; ++i) {
            String cname = mLWFData.getTextureName(i);
            textures[i] = mScene.getTextureManager().createAssetTexture(path + cname, null);
            //textures[i] = mScene.getTextureManager().createAssetTexture("lwf/Ring/" + cname, null);

        }
        mLWFData.setTextures(textures);

        attachLWF(mDisplaySizeDiv2.x, mDisplaySizeDiv2.y);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLWFManager.dispose();
    }

    private void attachLWF(final float x, final float y) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

        // attach lwf
        mLWF = mLWFObject.attachLWF(mLWFData);


        float lwfWidth = mLWF.getSize().x;
        float lwfHeight = mLWF.getSize().y;

        float screenWidth = x * 2;
        float screenHeight = y * 2;
        float maxScale = 0;

        maxScale = Math.min(lwfHeight, lwfWidth) / Math.max(screenHeight, screenWidth);

        //计算x轴，y轴的偏移位置
        float moveX = (screenWidth - lwfWidth) / 2;
        float moveY = (screenHeight - lwfHeight) / 2;


        mLWF.moveTo("_root", moveX, -lwfHeight - moveY);

        //动画的缩放比例需要根据横竖屏动态计算
//        if (screenHeight > screenWidth) {    //竖屏
        //    maxScale = lwfWidth / screenWidth > lwfHeight / screenHeight ? lwfWidth / screenWidth : lwfHeight / screenHeight;
        //  mLWF.moveTo("_root", moveX, -y * 15 / 10);
//            mLWF.moveTo("_root", moveX, -lwfHeight - moveY);
//        } else {      //横屏
        //   maxScale = lwfWidth / screenHeight > lwfHeight / screenWidth ? lwfWidth / screenHeight : lwfHeight / screenWidth;
        //     mLWF.moveTo("_root", moveX, -y * 2);
//            mLWF.moveTo("_root", moveX, -lwfHeight - moveY);
//        }


        if (maxScale >= 1) {   //需要缩放
            mLWF.scale("_root", maxScale, maxScale);
        }
        Log.i("attachLWF", "______width:" + width + "_____height:" + height + "________mLWF.getSize().x:" + mLWF.getSize().x + "______mLWF.getSize().y:" + mLWF.getSize().y + ", scale value: " + maxScale);



      /*
      if (x < y && (x * 2) <= mLWF.getSize().x) {//坚屏时，当前屏幕的宽小于动画的宽，
            Log.i("ss", "_____________________________________________________1");
            float scaleX = (x * 2) / mLWF.getSize().x;
            mLWF.moveTo("_root", 0, -y * 2);
            mLWF.scale("_root", scaleX, scaleX);
        } else if (x < y && (x * 2) >= mLWF.getSize().x) {//坚屏时，当前屏幕的宽大于动画的宽，

            Log.i("ss", "_____________________________________________________2_____-y*2:" + (-y * 2));
            float scaleX = (x * 2) / mLWF.getSize().x;

            mLWF.moveTo("_root", 0, -y * 2);
            mLWF.scale("_root", scaleX, scaleX);

        } else if (x > y && (x * 2) >= mLWF.getSize().x) {//横屏时，当前屏幕的宽大于动画的宽，

            float scaleY = (y * 2) / mLWF.getSize().y;
            Log.i("ss", "_____________________________________________________3___scale:" + scaleY);

            mLWF.scale("_root", scaleY, scaleY);
            mLWF.moveTo("_root", ((x * 2) - mLWF.getSize().x * scaleY) / 2, -y * 2);

        } else if (x > y && (x * 2) <= mLWF.getSize().x) {//横屏时，当前屏幕的宽小于动画的宽，

            Log.i("ss", "_____________________________________________________4");
            mLWF.moveTo("_root", ((x * 2) - mLWF.getSize().x) / 2, -y * 2);

        }
        */
        // handler
        mLWF.addEventHandler("done", new LWF.Handler() {
            @Override
            public void call() {
                Log.v(TAG, "done");
            }
        });
        mLWF.addEventHandler("STOP_DRAWING_BASE_UNITS", new LWF.Handler() {
            @Override
            public void call() {
                Log.v(TAG, "STOP_DRAWING_BASE_UNITS");
            }
        });
        mLWF.addEventHandler("START_DRAWING_FINAL_UNIT", new LWF.Handler() {
            @Override
            public void call() {
                Log.v(TAG, "START_DRAWING_FINAL_UNIT");
            }
        });
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mLWF != null) {
                mLWF.gotoAndPlay("_root", "start");
            }
        }
        return true;
    }

    private final Queue<Runnable> mRunOnDraw = new LinkedList<Runnable>();

    private void runOnDraw(final Runnable runnable) {
        synchronized (mRunOnDraw) {
            mRunOnDraw.add(runnable);
        }
    }

    private void runAll(Queue<Runnable> queue) {
        synchronized (queue) {
            while (!queue.isEmpty()) {
                queue.poll().run();
            }
        }
    }
}
