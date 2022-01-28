//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.metadata;

import cn.liushilei.util.office.emf.ImageFormat;

import java.util.HashMap;
import java.util.Map;

public class Metadata {
    private boolean isReadable;
    private boolean isWritable;
    private int height;
    private int width;
    protected ImageFormat type;

    public Metadata() {
        this.type = ImageFormat.UNSUPPORTED_IMAGE;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isReadable() {
        return this.isReadable;
    }

    public boolean isWritable() {
        return this.isWritable;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setReadable(boolean readable) {
        this.isReadable = readable;
    }

    public void setWritable(boolean writable) {
        this.isWritable = writable;
    }

    public String toString() {
        Map<String,Object> result = new HashMap<>();
        this.addGenericParams(result);
        return result.toString();
    }

    protected void addGenericParams( Map<String,Object> result) {
        result.put("width", this.width);
        result.put("height", this.height);
        result.put("isReadable", this.isReadable);
        result.put("isWritable", this.isWritable);
    }

    public ImageFormat getImageMetadataType() {
        return this.type;
    }
}
