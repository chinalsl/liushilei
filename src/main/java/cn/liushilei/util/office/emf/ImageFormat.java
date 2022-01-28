//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf;

public enum ImageFormat {
    BMP_IMAGE,
    DICOM_IMAGE,
    EMF_IMAGE,
    GIF_IMAGE,
    HEIC_IMAGE,
    JPEG_IMAGE,
    JPEG2000_IMAGE,
    PNG_IMAGE,
    PSD_IMAGE,
    SGI_IMAGE,
    TIFF_IMAGE,
    WEBP_IMAGE,
    WMF_IMAGE,
    UNSUPPORTED_IMAGE;

    private ImageFormat() {
    }

    public String toString() {
        return super.toString().toLowerCase();
    }
}
