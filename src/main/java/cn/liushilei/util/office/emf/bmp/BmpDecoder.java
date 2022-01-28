//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.bmp;

import cn.liushilei.util.office.emf.Decoder;
import cn.liushilei.util.office.emf.JDeliImage;
import cn.liushilei.util.office.emf.utility.BitReader;
import cn.liushilei.util.office.emf.utility.BmpInfo;
import cn.liushilei.util.office.emf.utility.DataByteLittle;
import cn.liushilei.util.office.emf.utility.DataFileLittle;
import cn.liushilei.util.office.emf.utility.DataReader;
import cn.liushilei.util.office.emf.utility.ToolARGB;
import cn.liushilei.util.office.emf.utility.ToolBinary;
import cn.liushilei.util.office.emf.utility.ToolGray;
import cn.liushilei.util.office.emf.utility.ToolIndex;
import cn.liushilei.util.office.emf.utility.ToolRGB;
import cn.liushilei.util.office.emf.utility.Tooler;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;

public class BmpDecoder extends JDeliImage implements Decoder {
    private static final int TYPE_BM = 19778;
    private static final int TYPE_BA = 16706;
    private static final int TYPE_CI = 18755;
    private static final int TYPE_CP = 20547;
    private static final int TYPE_IC = 17225;
    private static final int TYPE_PT = 21584;
    private static final int BI_RGB = 0;
    private static final int BI_RLE8 = 1;
    private static final int BI_RLE4 = 2;
    private static final int BI_BITFIELDS = 3;
    private static final int BI_JPEG = 4;
    private static final int BI_PNG = 5;
    private static final int BI_BI_ALPHABITFIELDS = 6;
    private static final int BI_CMYK = 11;
    private static final int BI_CMYKRLE8 = 12;
    private static final int BI_CMYKRLE4 = 13;

    public BmpDecoder() {
    }

    public BufferedImage read(byte[] bmpData) throws IOException {
        DataReader reader = new DataByteLittle(bmpData);
        BmpInfo info = getBmpInfo(reader);
        BufferedImage img;
        if (info.nPalColor == 0) {
            img = getImageNormal(info, reader);
        } else {
            img = getImagePalette(info, reader);
        }

        reader.close();
        return optimiseImage(img);
    }

    public BufferedImage read(File bmpFile) throws IOException {
        DataReader reader = new DataFileLittle(bmpFile);
        BmpInfo info = getBmpInfo(reader);
        BufferedImage img;
        if (info.nPalColor == 0) {
            img = getImageNormal(info, reader);
        } else {
            img = getImagePalette(info, reader);
        }

        reader.close();
        return optimiseImage(img);
    }

    public Rectangle readDimension(File bmpFile) throws IOException {
        DataReader reader = new DataFileLittle(bmpFile);
        BmpInfo info = getBmpInfo(reader);
        reader.close();
        return new Rectangle(info.width, info.height);
    }

    public Rectangle readDimension(byte[] bmpData) throws IOException {
        DataReader reader = new DataByteLittle(bmpData);
        BmpInfo info = getBmpInfo(reader);
        return new Rectangle(info.width, info.height);
    }

    private static BmpInfo getBmpInfo(DataReader reader) throws IOException {
        BmpInfo info = new BmpInfo();
        info.type = reader.getU16();
        if (info.type != 19778 && info.type != 16706 && info.type != 18755 && info.type != 20547 && info.type != 17225 && info.type != 21584) {
            throw new IOException("Invalid BMP File");
        } else {
            info.size = reader.getU32();
            reader.skip(4);
            info.offset = reader.getU32();
            info.headerSize = reader.getU32();
            info.width = reader.getU32();
            info.height = reader.getU32();
            info.nPlane = reader.getU16();
            info.bpp = reader.getU16();
            info.compress = reader.getU32();
            info.rawSize = reader.getU32();
            info.hRes = reader.getU32();
            info.vRes = reader.getU32();
            info.nPalColor = reader.getU32();
            info.nImpColor = reader.getU32();
            if (info.compress == 3) {
                info.maskR = reader.getU32();
                info.maskG = reader.getU32();
                info.maskB = reader.getU32();
                info.maskA = reader.getU32();
            }

            return info;
        }
    }

    private static BufferedImage getImageNormal(BmpInfo info, DataReader reader) throws IOException {
        reader.moveTo(info.offset);
        switch(info.compress) {
            case 0:
                return getFromRGB(info, reader);
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 11:
            case 12:
            case 13:
                throw new IOException("Current compression mode not yet supported");
            case 3:
                return getFromBITFIELDS(info, reader);
            case 7:
            case 8:
            case 9:
            case 10:
            default:
                return null;
        }
    }

    private static BufferedImage getFromRGB(BmpInfo info, DataReader reader) throws IOException {
        switch(info.bpp) {
            case 1:
            case 2:
            case 4:
                return handleUpto8Bit(info, reader);
            case 8:
                return handle8Bit(info, reader);
            case 24:
                return handle24Bit(info, reader);
            case 32:
                return handle32Bit(info, reader);
            default:
                return null;
        }
    }

    private static BufferedImage handle32Bit(BmpInfo info, DataReader reader) throws IOException {
        Tooler tool = new ToolARGB(info.width, info.height);

        for(int y = info.height - 1; y >= 0; --y) {
            for(int x = 0; x < info.width; ++x) {
                int v = reader.getU32();
                int a = v >>> 24;
                int r = v >> 16 & 255;
                int g = v >> 8 & 255;
                int b = v & 255;
                tool.set(x, y, a << 24 | r << 16 | g << 8 | b);
            }
        }

        return tool.getBufferedImage();
    }

    private static BufferedImage handle24Bit(BmpInfo info, DataReader reader) throws IOException {
        Tooler tool = new ToolRGB(info.width, info.height);
        int iwx = (int)(Math.ceil((double)(info.width * 24) / 32.0D) * 4.0D) - info.width * 3;

        for(int y = info.height - 1; y >= 0; --y) {
            int i;
            for(i = 0; i < info.width; ++i) {
                int v = reader.getU24();
                int r = v >> 16;
                int g = v >> 8 & 255;
                int b = v & 255;
                tool.set(i, y, r << 16 | g << 8 | b);
            }

            if (iwx != 0) {
                for(i = 0; i < iwx; ++i) {
                    reader.getU8();
                }
            }
        }

        return tool.getBufferedImage();
    }

    private static BufferedImage handle8Bit(BmpInfo info, DataReader reader) throws IOException {
        Tooler tool = new ToolGray(info.width, info.height);

        for(int y = info.height - 1; y >= 0; --y) {
            for(int x = 0; x < info.width; ++x) {
                tool.set(x, y, reader.getU8());
            }
        }

        return tool.getBufferedImage();
    }

    private static BufferedImage handleUpto8Bit(BmpInfo info, DataReader reader) throws IOException {
        Tooler tool = new ToolBinary(info.width, info.height, info.bpp);
        int bitsPerLine = (info.width * info.bpp + 31) / 32 * 4;
        int dataLen = info.rawSize == 0 ? bitsPerLine * info.height : info.rawSize;
        byte[] small = new byte[dataLen];
        reader.read(small);
        int iwx = bitsPerLine * 8 - info.width * info.bpp;
        BitReader br = new BitReader(small);

        for(int y = info.height - 1; y >= 0; --y) {
            for(int x = 0; x < info.width; ++x) {
                tool.set(x, y, br.readBits(info.bpp));
            }

            if (iwx != 0) {
                br.readBits(iwx);
            }
        }

        return tool.getBufferedImage();
    }

    private static BufferedImage getFromBITFIELDS(BmpInfo info, DataReader reader) throws IOException {
        long[] mv = new long[]{(long)info.maskA, (long)info.maskR, (long)info.maskG, (long)info.maskB};
        long[] ls = new long[4];
        long[] rs = new long[4];

        for(int i = 0; i < 4; ++i) {
            long v = mv[i];
            if (v != 0L) {
                long var10002;
                while((v & 1L) == 0L) {
                    v >>>= 1;
                    var10002 = rs[i]++;
                }

                for(v = mv[i]; (v >> 31 & 1L) == 0L; var10002 = ls[i]++) {
                    v <<= 1;
                }

                ls[i] %= 8L;
            }
        }

        Tooler tool = null;
        switch(info.bpp) {
            case 16:
                tool = handle16bit(info, reader, ls, rs);
                break;
            case 24:
                tool = handle24Bit(info, reader, ls, rs);
                break;
            case 32:
                tool = handle32Bit(info, reader, ls, rs);
        }

        if (tool == null) {
            return null;
        } else {
            return tool.getBufferedImage();
        }
    }

    private static Tooler handle32Bit(BmpInfo info, DataReader reader, long[] ls, long[] rs) throws IOException {
        Tooler tool = new ToolARGB(info.width, info.height);

        for(int y = info.height - 1; y >= 0; --y) {
            for(int x = 0; x < info.width; ++x) {
                int v = reader.getU32();
                int r = (v & info.maskR) >>> (int)rs[1] << (int)ls[1];
                int g = (v & info.maskG) >>> (int)rs[2] << (int)ls[2];
                int b = (v & info.maskB) >>> (int)rs[3] << (int)ls[3];
                int a = (v & info.maskA) >>> (int)rs[0] << (int)ls[0];
                tool.set(x, y, a << 24 | r << 16 | g << 8 | b);
            }
        }

        return tool;
    }

    private static Tooler handle24Bit(BmpInfo info, DataReader reader, long[] ls, long[] rs) throws IOException {
        Tooler tool = new ToolRGB(info.width, info.height);

        for(int y = info.height - 1; y >= 0; --y) {
            for(int x = 0; x < info.width; ++x) {
                int v = reader.getU24();
                int r = (v & info.maskR) >>> (int)rs[1] << (int)ls[1];
                int g = (v & info.maskG) >>> (int)rs[2] << (int)ls[2];
                int b = (v & info.maskB) >>> (int)rs[3] << (int)ls[3];
                int a = (v & info.maskA) >>> (int)rs[0] << (int)ls[0];
                tool.set(x, y, a << 24 | r << 16 | g << 8 | b);
            }
        }

        return tool;
    }

    private static Tooler handle16bit(BmpInfo info, DataReader reader, long[] ls, long[] rs) throws IOException {
        Tooler tool = new ToolGray(info.width, info.height);

        for(int y = info.height - 1; y >= 0; --y) {
            for(int x = 0; x < info.width; ++x) {
                int v = reader.getU16();
                int r = (v & info.maskR) >>> (int)rs[1] << (int)ls[1];
                int g = (v & info.maskG) >>> (int)rs[2] << (int)ls[2];
                int b = (v & info.maskB) >>> (int)rs[3] << (int)ls[3];
                int a = (v & info.maskA) >>> (int)rs[0] << (int)ls[0];
                tool.set(x, y, a << 24 | r << 16 | g << 8 | b);
            }
        }

        return tool;
    }

    private static BufferedImage getImagePalette(BmpInfo info, DataReader reader) throws IOException {
        int nCol = (int)Math.pow(2.0D, (double)info.bpp);
        byte[] rr = new byte[nCol];
        byte[] gg = new byte[nCol];
        byte[] bb = new byte[nCol];

        int stripe;
        for(stripe = 0; stripe < info.nPalColor; ++stripe) {
            bb[stripe] = (byte)reader.getU8();
            gg[stripe] = (byte)reader.getU8();
            rr[stripe] = (byte)reader.getU8();
            reader.getU8();
        }

        reader.moveTo(info.offset);
        stripe = (info.width * info.bpp + 31) / 32 * 4;
        int buf8 = (info.width * info.bpp + 7) / 8;
        switch(info.bpp) {
            case 1:
            case 2:
            case 4:
                IndexColorModel cm = new IndexColorModel(info.bpp, 1 << info.bpp, rr, gg, bb);
                BufferedImage img = new BufferedImage(info.width, info.height, 12, cm);
                byte[] data = ((DataBufferByte)img.getRaster().getDataBuffer()).getData();
                int dataLen = info.rawSize == 0 ? stripe * info.height : info.rawSize;
                byte[] small = new byte[dataLen];
                reader.read(small);
                int rawIdx = 0;

                for(int y = info.height - 1; y >= 0; --y) {
                    int imgIdx = y * buf8;
                    System.arraycopy(small, rawIdx, data, imgIdx, buf8);
                    rawIdx += stripe;
                }

                return img;
            case 3:
            default:
                Tooler tool = new ToolIndex(info.width, info.height, info.bpp, rr, gg, bb);

                for(int y = info.height - 1; y >= 0; --y) {
                    for(int x = 0; x < info.width; ++x) {
                        tool.set(x, y, reader.getU8());
                    }
                }

                return tool.getBufferedImage();
        }
    }
}
