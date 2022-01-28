package cn.liushilei.util.office.emf;

import cn.liushilei.util.office.emf.metadata.Metadata;
import java.awt.image.BufferedImage;
import java.io.File;

public interface Decoder {
    BufferedImage read(byte[] paramArrayOfbyte) throws Exception;

    BufferedImage read(File paramFile) throws Exception;

    default int getImageCount(File inputFile) throws Exception {
        return 1;
    }

    default int getImageCount(byte[] input) throws Exception {
        return 1;
    }

    default BufferedImage readImageAt(int i, File inputFile) throws Exception {
        throw new UnsupportedOperationException("readImageAt is not yet supported for this file Format");
    }

    default BufferedImage readImageAt(int i, byte[] input) throws Exception {
        throw new UnsupportedOperationException("readImageAt is not yet supported for this file Format");
    }

    default void setMetadata(Metadata metadata) {}

    default void readImageSpecificMetaData(File file, Metadata metadata) throws Exception {}

    default BufferedImage readEmbeddedThumbnail(File imageFile) throws Exception {
        return null;
    }
}
