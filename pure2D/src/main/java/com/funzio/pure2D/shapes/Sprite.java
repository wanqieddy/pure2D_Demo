/**
 * ****************************************************************************
 * Copyright (C) 2012-2014 GREE, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ****************************************************************************
 */
/**
 *
 */
package com.funzio.pure2D.shapes;

import com.funzio.pure2D.gl.gl10.textures.Texture;

/**
 * @author long
 */
public class Sprite extends Rectangular {
    private boolean mSizeToTexture = true;
    private boolean mSizeToFrame = true;

    protected float mOffsetX = 0;
    protected float mOffsetY = 0;

    public Sprite() {
        super();
    }

    @Override
    public void setTexture(final Texture texture) {
        super.setTexture(texture);

        // fit size to texture
        if (mSizeToTexture && texture != null) { // do not do: && texture.isLoaded(), because texture can be loaded and later expired
            setSize(texture.getSize());
        }
    }

    @Override
    protected void onTextureLoaded(final Texture texture) {
        super.onTextureLoaded(texture);

        // match size
        if (mSizeToTexture) {
            setSize(texture.getSize());
        }
    }

    public boolean isSizeToTexture() {
        return mSizeToTexture;
    }

    public void setSizeToTexture(final boolean sizeToTexture) {
        mSizeToTexture = sizeToTexture;

        // fit size to texture
        if (mSizeToTexture && mTexture != null && mTexture.isLoaded()) {
            setSize(mTexture.getSize());
        }
    }

    /*@Override
    public boolean shouldDraw(final RectF globalViewRect) {
        return super.shouldDraw(globalViewRect) && (mTexture != null && mTexture.isLoaded());
    }*/

    public boolean isSizeToFrame() {
        return mSizeToFrame;
    }

}
