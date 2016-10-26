/**
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
 */
/**
 *
 */
package com.funzio.pure2D.ui;

import com.funzio.pure2D.Pure2DURI;
import com.funzio.pure2D.containers.Alignment;

/**
 * @author long.ngo
 */
public class UIConfig {
    private static final String TAG = UIConfig.class.getSimpleName();

    public static final boolean DEFAULT_ASYNC = true;

    public static final String TYPE_STRING = "string";
    public static final String TYPE_DRAWABLE = "drawable";

    public static final String LAYER_SCENE = "$SCENE";
    public static final String LAYER_PARENT = "$PARENT";

    // variables
    public static final String $CDN_URL = "$CDN_URL";
    public static final String $CACHE_DIR = "$CACHE_DIR";

    public static final String FILE_JSON = ".json";
    public static final String FILE_PNG = ".png";


    public static int getAlignment(final String align) {
        if ("top".equalsIgnoreCase(align)) {
            return Alignment.TOP;
        } else if ("bottom".equalsIgnoreCase(align)) {
            return Alignment.BOTTOM;
        } else if ("left".equalsIgnoreCase(align)) {
            return Alignment.LEFT;
        } else if ("right".equalsIgnoreCase(align)) {
            return Alignment.RIGHT;
        } else if ("hcenter".equalsIgnoreCase(align)) {
            return Alignment.HORIZONTAL_CENTER;
        } else if ("vcenter".equalsIgnoreCase(align)) {
            return Alignment.VERTICAL_CENTER;
        } else if ("center".equalsIgnoreCase(align)) {
            return Alignment.HORIZONTAL_CENTER | Alignment.VERTICAL_CENTER;
        }

        return Alignment.NONE;
    }

    public static boolean isUnknownUri(final String uri) {
        return !uri.startsWith(Pure2DURI.HTTP) //
                && !uri.startsWith(Pure2DURI.HTTPS) //
                && !uri.startsWith(Pure2DURI.DRAWABLE) //
                && !uri.startsWith(Pure2DURI.ASSET) //
                && !uri.startsWith(Pure2DURI.FILE) //
                && !uri.startsWith(Pure2DURI.STRING) //
                && !uri.startsWith(Pure2DURI.CACHE) //
                && !uri.startsWith(Pure2DURI.XML);
    }
}
