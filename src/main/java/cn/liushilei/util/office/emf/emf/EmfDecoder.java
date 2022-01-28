package cn.liushilei.util.office.emf.emf;

import cn.liushilei.util.office.emf.Decoder;
import cn.liushilei.util.office.emf.JDeliImage;
import cn.liushilei.util.office.emf.emf.data.DC;
import cn.liushilei.util.office.emf.emf.data.Design;
import cn.liushilei.util.office.emf.emf.data.EmfObject;
import cn.liushilei.util.office.emf.emf.data.FM;
import cn.liushilei.util.office.emf.emf.data.ImageSpace;
import cn.liushilei.util.office.emf.emf.data.Space;
import cn.liushilei.util.office.emf.emf.data.SvgSpace;
import cn.liushilei.util.office.emf.utility.DataByteLittle;
import cn.liushilei.util.office.emf.utility.DataFileLittle;
import cn.liushilei.util.office.emf.utility.DataReader;
import cn.liushilei.util.office.emf.utility.WriterByteLittle;
import cn.liushilei.util.office.emf.utility.WriterFileBig;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;

public class EmfDecoder extends JDeliImage implements Decoder {
    private static final int HEADER = 1;

    private static final int POLYBEZIER = 2;

    private static final int POLYGON = 3;

    private static final int POLYLINE = 4;

    private static final int POLYBEZIERTO = 5;

    private static final int POLYLINETO = 6;

    private static final int POLYPOLYLINE = 7;

    private static final int POLYPOLYGON = 8;

    private static final int SETWINDOWEXTEX = 9;

    private static final int SETWINDOWORGEX = 10;

    private static final int SETVIEWPORTEXTEX = 11;

    private static final int SETVIEWPORTORGEX = 12;

    private static final int SETBRUSHORGEX = 13;

    private static final int EOF = 14;

    private static final int SETPIXELV = 15;

    private static final int SETMAPPERFLAGS = 16;

    private static final int SETMAPMODE = 17;

    private static final int SETBKMODE = 18;

    private static final int SETPOLYFILLMODE = 19;

    private static final int SETROP2 = 20;

    private static final int SETSTRETCHBLTMODE = 21;

    private static final int SETTEXTALIGN = 22;

    private static final int SETCOLORADJUSTMENT = 23;

    private static final int SETTEXTCOLOR = 24;

    private static final int SETBKCOLOR = 25;

    private static final int OFFSETCLIPRGN = 26;

    private static final int MOVETOEX = 27;

    private static final int SETMETARGN = 28;

    private static final int EXCLUDECLIPRECT = 29;

    private static final int INTERSECTCLIPRECT = 30;

    private static final int SCALEVIEWPORTEXTEX = 31;

    private static final int SCALEWINDOWEXTEX = 32;

    private static final int SAVEDC = 33;

    private static final int RESTOREDC = 34;

    private static final int SETWORLDTRANSFORM = 35;

    private static final int MODIFYWORLDTRANSFORM = 36;

    private static final int SELECTOBJECT = 37;

    private static final int CREATEPEN = 38;

    private static final int CREATEBRUSHINDIRECT = 39;

    private static final int DELETEOBJECT = 40;

    private static final int ANGLEARC = 41;

    private static final int ELLIPSE = 42;

    private static final int RECTANGLE = 43;

    private static final int ROUNDRECT = 44;

    private static final int ARC = 45;

    private static final int CHORD = 46;

    private static final int PIE = 47;

    private static final int SELECTPALETTE = 48;

    private static final int CREATEPALETTE = 49;

    private static final int SETPALETTEENTRIES = 50;

    private static final int RESIZEPALETTE = 51;

    private static final int REALIZEPALETTE = 52;

    private static final int EXTFLOODFILL = 53;

    private static final int LINETO = 54;

    private static final int ARCTO = 55;

    private static final int POLYDRAW = 56;

    private static final int SETARCDIRECTION = 57;

    private static final int SETMITERLIMIT = 58;

    private static final int BEGINPATH = 59;

    private static final int ENDPATH = 60;

    private static final int CLOSEFIGURE = 61;

    private static final int FILLPATH = 62;

    private static final int STROKEANDFILLPATH = 63;

    private static final int STROKEPATH = 64;

    private static final int FLATTENPATH = 65;

    private static final int WIDENPATH = 66;

    private static final int SELECTCLIPPATH = 67;

    private static final int ABORTPATH = 68;

    private static final int COMMENT = 70;

    private static final int FILLRGN = 71;

    private static final int FRAMERGN = 72;

    private static final int INVERTRGN = 73;

    private static final int PAINTRGN = 74;

    private static final int EXTSELECTCLIPRGN = 75;

    private static final int BITBLT = 76;

    private static final int STRETCHBLT = 77;

    private static final int MASKBLT = 78;

    private static final int PLGBLT = 79;

    private static final int SETDIBITSTODEVICE = 80;

    private static final int STRETCHDIBITS = 81;

    private static final int EXTCREATEFONTINDIRECTW = 82;

    private static final int EXTTEXTOUTA = 83;

    private static final int EXTTEXTOUTW = 84;

    private static final int POLYBEZIER16 = 85;

    private static final int POLYGON16 = 86;

    private static final int POLYLINE16 = 87;

    private static final int POLYBEZIERTO16 = 88;

    private static final int POLYLINETO16 = 89;

    private static final int POLYPOLYLINE16 = 90;

    private static final int POLYPOLYGON16 = 91;

    private static final int POLYDRAW16 = 92;

    private static final int CREATEMONOBRUSH = 93;

    private static final int CREATEDIBPATTERNBRUSHPT = 94;

    private static final int EXTCREATEPEN = 95;

    private static final int POLYTEXTOUTA = 96;

    private static final int POLYTEXTOUTW = 97;

    private static final int SETICMMODE = 98;

    private static final int CREATECOLORSPACE = 99;

    private static final int SETCOLORSPACE = 100;

    private static final int DELETECOLORSPACE = 101;

    private static final int GLSRECORD = 102;

    private static final int GLSBOUNDEDRECORD = 103;

    private static final int PIXELFORMAT = 104;

    private static final int DRAWESCAPE = 105;

    private static final int EXTESCAPE = 106;

    private static final int SMALLTEXTOUT = 108;

    private static final int FORCEUFIMAPPING = 109;

    private static final int NAMEDESCAPE = 110;

    private static final int COLORCORRECTPALETTE = 111;

    private static final int SETICMPROFILEA = 112;

    private static final int SETICMPROFILEW = 113;

    private static final int ALPHABLEND = 114;

    private static final int SETLAYOUT = 115;

    private static final int TRANSPARENTBLT = 116;

    private static final int GRADIENTFILL = 118;

    private static final int SETLINKEDUFIS = 119;

    private static final int SETTEXTJUSTIFICATION = 120;

    private static final int COLORMATCHTOTARGETW = 121;

    private static final int CREATECOLORSPACEW = 122;

    private static final int WHITE_BRUSH = -2147483648;

    private static final int LTGRAY_BRUSH = -2147483647;

    private static final int GRAY_BRUSH = -2147483646;

    private static final int DKGRAY_BRUSH = -2147483645;

    private static final int BLACK_BRUSH = -2147483644;

    private static final int NULL_BRUSH = -2147483643;

    private static final int WHITE_PEN = -2147483642;

    private static final int BLACK_PEN = -2147483641;

    private static final int NULL_PEN = -2147483640;

    private static final int DC_BRUSH = -2147483630;

    private static final int DC_PEN = -2147483629;

    public static String getCommand(int command) {
        switch (command) {
            case 1:
                return "header";
            case 2:
                return "polybezier";
            case 3:
                return "polygon";
            case 4:
                return "polyline";
            case 5:
                return "polybezierto";
            case 6:
                return "polylineto";
            case 7:
                return "polypolyline";
            case 8:
                return "polypolygon";
            case 9:
                return "setwindowextex";
            case 10:
                return "setwindoworgex";
            case 11:
                return "setviewportextex";
            case 12:
                return "setviewportorgex";
            case 15:
                return "setpixelv";
            case 16:
                return "setmapperflags";
            case 17:
                return "setmapmode";
            case 18:
                return "setbkmode";
            case 19:
                return "setpolyfillmode";
            case 20:
                return "setrop2";
            case 21:
                return "setstretchbltmode";
            case 22:
                return "settextalign";
            case 23:
                return "setcoloradjustment";
            case 24:
                return "settextcolor";
            case 25:
                return "setbkcolor";
            case 26:
                return "offsetcliprgn";
            case 27:
                return "movetoex";
            case 28:
                return "setmetargn";
            case 29:
                return "excludecliprect";
            case 30:
                return "intersectcliprect";
            case 31:
                return "scaleviewportextex";
            case 32:
                return "scalewindowextex";
            case 33:
                return "savedc";
            case 34:
                return "restoredc";
            case 35:
                return "setworldtransform";
            case 36:
                return "modifyworldtransform";
            case 37:
                return "selectobject";
            case 38:
                return "createpen";
            case 39:
                return "createbrushindirect";
            case 40:
                return "deleteobject";
            case 41:
                return "anglearc";
            case 42:
                return "ellipse";
            case 43:
                return "rectangle";
            case 44:
                return "roundrect";
            case 45:
                return "arc";
            case 46:
                return "chord";
            case 47:
                return "pie";
            case 48:
                return "selectpalette";
            case 49:
                return "createpalette";
            case 50:
                return "selectpaletteentries";
            case 51:
                return "resizepalette";
            case 52:
                return "realizepalette";
            case 53:
                return "extfloodfill";
            case 54:
                return "lineto";
            case 55:
                return "arcto";
            case 56:
                return "polydraw";
            case 57:
                return "searchdirection";
            case 58:
                return "setmiterlimit";
            case 59:
                return "beginpath";
            case 60:
                return "endpath";
            case 61:
                return "closefigure";
            case 62:
                return "fillpath";
            case 63:
                return "strokeandfillpath";
            case 64:
                return "strokepath";
            case 65:
                return "flattenpath";
            case 66:
                return "widenpath";
            case 67:
                return "selectclippath";
            case 68:
                return "abortpath";
            case 70:
                return "comment";
            case 71:
                return "fillrgn";
            case 72:
                return "framergn";
            case 73:
                return "invertrgn";
            case 74:
                return "paintrgn";
            case 75:
                return "extselectcliprgn";
            case 76:
                return "bitblt";
            case 77:
                return "stretchblt";
            case 78:
                return "maskblt";
            case 79:
                return "plgblt";
            case 80:
                return "setdibitstodevice";
            case 81:
                return "stretchdibits";
            case 82:
                return "extcreatefontindirectw";
            case 83:
                return "exttextouta";
            case 84:
                return "exttextoutw";
            case 85:
                return "polybezier16";
            case 86:
                return "polygon16";
            case 87:
                return "polyline61";
            case 88:
                return "polybezierto16";
            case 89:
                return "polylineto16";
            case 90:
                return "polypolyline16";
            case 91:
                return "polypolygon16";
            case 92:
                return "polydraw16";
            case 93:
                return "createmonobrush";
            case 94:
                return "createdibpatternbrushpt";
            case 95:
                return "extcreatepen";
            case 96:
                return "polytextouta";
            case 97:
                return "polytextoutw";
            case 98:
                return "seticmmode";
            case 99:
                return "createcolorspace";
            case 100:
                return "setcolorspace";
            case 101:
                return "deletecolorspace";
            case 102:
                return "glsrecord";
            case 103:
                return "glsboundrecord";
            case 104:
                return "pixelformat";
            case 105:
                return "drawescape";
            case 106:
                return "extescape";
            case 108:
                return "smalltextout";
            case 109:
                return "forceufimapping";
            case 110:
                return "namedescape";
            case 111:
                return "colorcorrectpalette";
            case 112:
                return "seticmprofilea";
            case 113:
                return "seticmprofilew";
            case 114:
                return "alphablend";
            case 115:
                return "setlayout";
            case 116:
                return "transparentblt";
            case 118:
                return "gradientfill";
            case 119:
                return "setlinkedufis";
            case 120:
                return "settextjustification";
            case 121:
                return "colormatchtotargetw";
            case 122:
                return "createcolorspacew";
            case 14:
                return "eof";
        }
        return "unknown type " + Integer.toHexString(command);
    }

    private EmfObject[] objects = new EmfObject[1000];

    private int[] pCmds = new int[32768];

    private int[] path;

    private int pc;

    private final ArrayDeque<DC> dcStack = new ArrayDeque<>();

    private int curX;

    private int curY;

    private int boundX2;

    private int boundY2;

    public BufferedImage read(File file) throws IOException {
        DataFileLittle dataFileLittle = new DataFileLittle(file);
        parseHeader((DataReader)dataFileLittle);
        dataFileLittle.moveTo(0);
        BufferedImage image = new BufferedImage(this.boundX2, this.boundY2, 2);
        ImageSpace imageSpace = new ImageSpace(image);
        parseRecords((DataReader)dataFileLittle, (Space)imageSpace);
        imageSpace.close();
        return image;
    }

    public BufferedImage read(byte[] data) throws IOException {
        DataByteLittle dataByteLittle = new DataByteLittle(data);
        parseHeader((DataReader)dataByteLittle);
        dataByteLittle.moveTo(0);
        BufferedImage image = new BufferedImage(this.boundX2, this.boundY2, 2);
        ImageSpace imageSpace = new ImageSpace(image);
        parseRecords((DataReader)dataByteLittle, (Space)imageSpace);
        imageSpace.close();
        return image;
    }

    public Rectangle readDimension(File file) throws Exception {
        DataFileLittle dataFileLittle = new DataFileLittle(file);
        parseHeader((DataReader)dataFileLittle);
        dataFileLittle.close();
        return new Rectangle(this.boundX2, this.boundY2);
    }

    public Rectangle readDimension(byte[] emfRawData) throws Exception {
        DataByteLittle dataByteLittle = new DataByteLittle(emfRawData);
        parseHeader((DataReader)dataByteLittle);
        return new Rectangle(this.boundX2, this.boundY2);
    }

    public static void toSVG(File emfFile, File svgOutputFile) throws IOException {
        DataFileLittle dataFileLittle = new DataFileLittle(emfFile);
        StringBuilder sb = new StringBuilder();
        SvgSpace svgSpace = new SvgSpace(sb);
        EmfDecoder emfDecoder = new EmfDecoder();
        emfDecoder.parseHeader((DataReader)dataFileLittle);
        dataFileLittle.moveTo(0);
        emfDecoder.parseRecords((DataReader)dataFileLittle, (Space)svgSpace);
        svgSpace.close();
        WriterFileBig fw = new WriterFileBig(svgOutputFile);
        fw.write(sb.toString().getBytes());
        fw.close();
    }

    public static void toSVG(InputStream emfInputStream, OutputStream svgOutputFile) throws IOException {
        File emfFile = File.createTempFile("idr", "emf");
        BufferedInputStream from = new BufferedInputStream(emfInputStream);
        try {
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(emfFile));
            try {
                byte[] data = new byte[emfInputStream.available()];
                from.read(data);
                fos.write(data);
                fos.close();
            } catch (Throwable throwable) {
                try {
                    fos.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
            from.close();
        } catch (Throwable throwable) {
            try {
                from.close();
            } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
            }
            throw throwable;
        }
        DataFileLittle dataFileLittle = new DataFileLittle(emfFile);
        StringBuilder sb = new StringBuilder();
        SvgSpace svgSpace = new SvgSpace(sb);
        EmfDecoder emfDecoder = new EmfDecoder();
        emfDecoder.parseHeader((DataReader)dataFileLittle);
        dataFileLittle.moveTo(0);
        emfDecoder.parseRecords((DataReader)dataFileLittle, (Space)svgSpace);
        svgSpace.close();
        WriterFileBig fw = new WriterFileBig(svgOutputFile);
        fw.write(sb.toString().getBytes());
        fw.close();
        emfFile.delete();
    }

    private void parseHeader(DataReader reader) throws IOException {
        boolean canRead = true;
        while (canRead && reader.getPosition() < reader.getLength()) {
            int offset = reader.getPosition();
            int type = reader.getU32();
            int size = reader.getU32();
            if (type == 1) {
                readHeader(reader);
                canRead = false;
            }
            reader.moveTo(offset + size);
        }
    }

    private void parseRecords(DataReader reader, Space space) throws IOException {
        boolean canRead = true;
        while (canRead && reader.getPosition() < reader.getLength()) {
            int id;
            if (this.pc > this.pCmds.length / 2) {
                int[] temp = new int[this.pCmds.length << 1];
                System.arraycopy(this.pCmds, 0, temp, 0, this.pc);
                this.pCmds = temp;
            }
            int offset = reader.getPosition();
            int type = reader.getU32();
            int size = reader.getU32();
            switch (type) {
                case 14:
                    canRead = false;
                    break;
                case 13:
                    brushOrigin(reader);
                    break;
                case 35:
                    setWorldTransform(reader, space);
                    break;
                case 36:
                    modifyWorldTransform(reader, space);
                    break;
                case 38:
                case 39:
                case 82:
                case 93:
                case 94:
                case 95:
                    id = reader.getU32();
                    createObject(type, offset, size, id);
                    break;
                case 49:
                case 99:
                case 122:
                    createObject(type, offset, size);
                    break;
                case 40:
                case 101:
                    deleteObject(reader);
                    break;
                case 37:
                case 48:
                case 100:
                    selectObject(reader, space);
                    break;
                case 24:
                    setTextColor(reader, space);
                    break;
                case 20:
                case 64:
                    strokePath(space);
                    break;
                case 61:
                    closeFigure();
                    break;
                case 54:
                    lineTo(reader);
                    break;
                case 27:
                    moveTO(reader);
                    break;
                case 59:
                    beginPath();
                    break;
                case 62:
                    fillPath(space);
                    break;
                case 63:
                    strokeFillPath(space);
                    break;
                case 5:
                case 88:
                    polyBezierTo(reader, (type == 88));
                    break;
                case 3:
                case 86:
                    polygon(reader, space, (type == 86));
                    break;
                case 6:
                case 89:
                    polyLineTo(reader, (type == 89));
                    break;
                case 4:
                case 87:
                    polyline(reader, space, (type == 87));
                    break;
                case 83:
                case 84:
                    textOut(reader, space, (type == 84));
                    break;
                case 108:
                    smalltextout(reader, space);
                    break;
                case 43:
                    rectangle(reader, space);
                    break;
                case 44:
                    roundRectangle(reader, space);
                    break;
                case 7:
                case 90:
                    polyPolyline(reader, space, (type == 90));
                    break;
                case 8:
                case 91:
                    polyPolygon(reader, space, (type == 91));
                    break;
                case 2:
                case 85:
                    polybezier(reader, space, (type == 85));
                    break;
                case 42:
                    ellipse(reader, space);
                    break;
                case 15:
                    setpixelv(reader, space);
                    break;
                case 33:
                    saveDC(space);
                    break;
                case 34:
                    restoreDC(reader, space);
                    break;
                case 77:
                    stretchblt(reader, space);
                    break;
                case 81:
                    stretchdibits(reader, space);
                    break;
            }
            reader.moveTo(offset + size);
        }
    }

    private void createObject(int type, int offset, int size) {
        int op = 1;
        while (this.objects[op] != null)
            op++;
        this.objects[op] = new EmfObject(type, offset, size);
    }

    private void createObject(int type, int offset, int size, int id) {
        this.objects[id] = new EmfObject(type, offset, size);
    }

    private void deleteObject(DataReader reader) throws IOException {
        int pos = reader.getU32();
        this.objects[pos] = null;
    }

    private void lineTo(DataReader reader) throws IOException {
        this.curX = reader.getU32();
        this.curY = reader.getU32();
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = this.curX;
        this.pCmds[this.pc++] = this.curY;
    }

    private void moveTO(DataReader reader) throws IOException {
        this.curX = reader.getU32();
        this.curY = reader.getU32();
        this.pCmds[this.pc++] = 0;
        this.pCmds[this.pc++] = this.curX;
        this.pCmds[this.pc++] = this.curY;
    }

    private void rectangle(DataReader reader, Space space) throws IOException {
        int b0 = reader.getU32();
        int b1 = reader.getU32();
        int b2 = reader.getU32();
        int b3 = reader.getU32();
        this.pCmds[this.pc++] = 0;
        this.pCmds[this.pc++] = b0;
        this.pCmds[this.pc++] = b1;
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = b2;
        this.pCmds[this.pc++] = b1;
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = b2;
        this.pCmds[this.pc++] = b3;
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = b0;
        this.pCmds[this.pc++] = b3;
        this.pCmds[this.pc++] = 4;
        this.path = new int[this.pc];
        System.arraycopy(this.pCmds, 0, this.path, 0, this.pc);
        this.pc = 0;
        space.fPath(this.path);
        space.sPath(this.path);
    }

    private void setpixelv(DataReader reader, Space space) throws IOException {
        int b0 = reader.getU32();
        int b1 = reader.getU32();
        int b2 = b0 + 1;
        int b3 = b1 + 1;
        int oldColor = space.getFC();
        int color = 0xFF000000 | reader.getU8() << 16 | reader.getU8() << 8 | reader.getU8();
        space.setFC(color);
        this.pCmds[this.pc++] = 0;
        this.pCmds[this.pc++] = b0;
        this.pCmds[this.pc++] = b1;
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = b2;
        this.pCmds[this.pc++] = b1;
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = b2;
        this.pCmds[this.pc++] = b3;
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = b0;
        this.pCmds[this.pc++] = b3;
        this.pCmds[this.pc++] = 4;
        this.path = new int[this.pc];
        System.arraycopy(this.pCmds, 0, this.path, 0, this.pc);
        this.pc = 0;
        space.fPath(this.path);
        space.setFC(oldColor);
    }

    private static void ellipse(DataReader reader, Space space) throws IOException {
        int b0 = reader.getU32();
        int b1 = reader.getU32();
        int b2 = reader.getU32();
        int b3 = reader.getU32();
        space.ellipse(b0, b1, b2 - b0, b3 - b1);
    }

    private void roundRectangle(DataReader reader, Space space) throws IOException {
        int b0 = reader.getU32();
        int b1 = reader.getU32();
        int b2 = reader.getU32();
        int b3 = reader.getU32();
        this.pCmds[this.pc++] = 0;
        this.pCmds[this.pc++] = b0;
        this.pCmds[this.pc++] = b1;
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = b2;
        this.pCmds[this.pc++] = b1;
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = b2;
        this.pCmds[this.pc++] = b3;
        this.pCmds[this.pc++] = 1;
        this.pCmds[this.pc++] = b0;
        this.pCmds[this.pc++] = b3;
        this.pCmds[this.pc++] = 4;
        this.path = new int[this.pc];
        System.arraycopy(this.pCmds, 0, this.path, 0, this.pc);
        this.pc = 0;
        space.fPath(this.path);
        space.sPath(this.path);
    }

    private void polyLineTo(DataReader reader, boolean is16) throws IOException {
        reader.skip(16);
        int count = reader.getU32();
        int i = 0;
        while (i < count) {
            this.pCmds[this.pc++] = 1;
            if (is16) {
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
            } else {
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
            }
            i++;
        }
    }

    private void polyBezierTo(DataReader reader, boolean is16) throws IOException {
        reader.skip(16);
        int count = reader.getU32();
        int i = 0;
        while (i + 3 <= count) {
            this.pCmds[this.pc++] = 2;
            if (is16) {
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
            } else {
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
            }
            i += 3;
        }
    }

    private void polyline(DataReader reader, Space space, boolean is16) throws IOException {
        reader.skip(16);
        int count = reader.getU32();
        for (int i = 0; i < count; i++) {
            int x, y;
            if (is16) {
                x = reader.getU16();
                y = reader.getU16();
            } else {
                x = reader.getU32();
                y = reader.getU32();
            }
            this.pCmds[this.pc++] = (i == 0) ? 0 : 1;
            this.pCmds[this.pc++] = x;
            this.pCmds[this.pc++] = y;
        }
        strokePath(space);
    }

    private void polybezier(DataReader reader, Space space, boolean is16) throws IOException {
        reader.skip(16);
        int count = reader.getU32();
        int i = 0;
        while (i + 3 <= count) {
            this.pCmds[this.pc++] = 2;
            if (is16) {
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
                this.pCmds[this.pc++] = reader.getU16();
            } else {
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
                this.pCmds[this.pc++] = reader.getU32();
            }
            i += 3;
        }
        strokePath(space);
    }

    private void polygon(DataReader reader, Space space, boolean is16) throws IOException {
        reader.skip(16);
        int count = reader.getU32();
        for (int i = 0; i < count; i++) {
            int x, y;
            if (is16) {
                x = reader.getU16();
                y = reader.getU16();
            } else {
                x = reader.getU32();
                y = reader.getU32();
            }
            this.pCmds[this.pc++] = (i == 0) ? 0 : 1;
            this.pCmds[this.pc++] = x;
            this.pCmds[this.pc++] = y;
        }
        closeFigure();
        strokeFillPath(space);
    }

    private void polyPolygon(DataReader reader, Space space, boolean is16) throws IOException {
        reader.skip(16);
        int nPolygons = reader.getU32();
        reader.getU32();
        int[] ppCount = new int[nPolygons];
        for (int i = 0; i < nPolygons; i++)
            ppCount[i] = reader.getU32();
        for (int j = 0; j < nPolygons; j++) {
            int c = 0;
            for (int k = 0; k < ppCount[j]; k++) {
                int x, y;
                if (is16) {
                    x = reader.getU16();
                    y = reader.getU16();
                } else {
                    x = reader.getU32();
                    y = reader.getU32();
                }
                this.pCmds[this.pc++] = (c == 0) ? 0 : 1;
                this.pCmds[this.pc++] = x;
                this.pCmds[this.pc++] = y;
                c++;
            }
            closeFigure();
            strokeFillPath(space);
        }
    }

    private void polyPolyline(DataReader reader, Space space, boolean is16) throws IOException {
        reader.skip(16);
        int nPolygons = reader.getU32();
        reader.getU32();
        int[] ppCount = new int[nPolygons];
        for (int i = 0; i < nPolygons; i++)
            ppCount[i] = reader.getU32();
        for (int j = 0; j < nPolygons; j++) {
            int c = 0;
            for (int k = 0; k < ppCount[j]; k++) {
                int x, y;
                if (is16) {
                    x = reader.getU16();
                    y = reader.getU16();
                } else {
                    x = reader.getU32();
                    y = reader.getU32();
                }
                this.pCmds[this.pc++] = (c == 0) ? 0 : 1;
                this.pCmds[this.pc++] = x;
                this.pCmds[this.pc++] = y;
                c++;
            }
            strokePath(space);
        }
    }

    private void closeFigure() {
        if (this.pc > 2)
            this.pCmds[this.pc++] = 4;
    }

    private void beginPath() {
        this.path = null;
        this.pc = 0;
    }

    private void fillPath(Space space) {
        this.path = new int[this.pc];
        System.arraycopy(this.pCmds, 0, this.path, 0, this.pc);
        this.pc = 0;
        space.fPath(this.path);
    }

    private void strokePath(Space space) {
        this.path = new int[this.pc];
        System.arraycopy(this.pCmds, 0, this.path, 0, this.pc);
        this.pc = 0;
        space.sPath(this.path);
    }

    private void strokeFillPath(Space space) {
        this.path = new int[this.pc];
        System.arraycopy(this.pCmds, 0, this.path, 0, this.pc);
        this.pc = 0;
        space.sfPath(this.path);
    }

    private static void setTextColor(DataReader reader, Space space) throws IOException {
        int fontColor = 0xFF000000 | reader.getU8() << 16 | reader.getU8() << 8 | reader.getU8();
        space.setFontC(fontColor);
    }

    private void brushOrigin(DataReader reader) throws IOException {
        this.curX = reader.getU32();
        this.curY = reader.getU32();
    }

    private void saveDC(Space space) {
        DC dc = new DC();
        dc.fc = space.getFC();
        dc.sc = space.getSC();
        dc.fontC = space.getFontC();
        dc.mm = space.getMatrix();
        dc.fontName = space.getFontName();
        dc.fontSize = space.getFontSize();
        dc.design = space.getDesign();
        this.dcStack.push(dc);
    }

    private void restoreDC(DataReader reader, Space space) throws IOException {
        if (!this.dcStack.isEmpty()) {
            int loc = reader.getU32();
            DC dc = this.dcStack.pop();
            while (loc < -1) {
                dc = this.dcStack.pop();
                loc++;
            }
            space.setFC(dc.fc);
            space.setSC(dc.sc);
            space.setFontC(dc.fontC);
            space.setMatrix(dc.mm);
            space.setFontName(dc.fontName);
            space.setFontSize(dc.fontSize);
            space.setDesign(dc.design);
        }
    }

    private void selectObject(DataReader reader, Space space) throws IOException {
        int cp = reader.getU32();
        if (cp >>> 31 == 1) {
            selectStockObject(cp, space);
        } else {
            EmfObject sel = this.objects[cp];
            if (sel != null) {
                int penWidth, penStyle, penColor, brushStyle, r, g, b, brushColor, fontSize, orn, weight, fontWeight;
                StringBuilder sb;
                int i;
                String name;
                reader.moveTo(sel.offset + 8);
                reader.getU32();
                switch (sel.type) {
                    case 93:
                    case 94:
                        createdippatternbrushpt(reader, space);
                        break;
                    case 39:
                        brushStyle = reader.getU32();
                        r = reader.getU8();
                        g = reader.getU8();
                        b = reader.getU8();
                        reader.getU8();
                        brushColor = r << 16 | g << 8 | b;
                        space.setFC(0xFF000000 | brushColor);
                        if (brushStyle == 1)
                            space.setFC(0);
                        reader.getU32();
                        break;
                    case 38:
                    case 95:
                        if (sel.type == 95)
                            reader.skip(16);
                        penStyle = reader.getU32();
                        penWidth = reader.getU32();
                        space.setSW(penWidth);
                        space.sStyle(penStyle);
                        reader.skip(4);
                        penColor = 0xFF000000 | reader.getU8() << 16 | reader.getU8() << 8 | reader.getU8();
                        reader.getU8();
                        space.setSC(penColor);
                        break;
                    case 82:
                        fontSize = reader.getU32();
                        reader.skip(8);
                        orn = reader.getU32();
                        weight = reader.getU32();
                        fontWeight = (weight == 700) ? 1 : 0;
                        reader.skip(8);
                        sb = new StringBuilder();
                        for (i = 0; i < 66; i++) {
                            int c = reader.getU16();
                            if (c == 0)
                                break;
                            sb.append((char)c);
                        }
                        name = sb.toString();
                        if (!name.isEmpty())
                            space.font(name, Math.abs(fontSize), fontWeight);
                        break;
                }
            }
        }
    }

    private static void selectStockObject(int type, Space space) {
        switch (type) {
            case -2147483648:
                space.setFC(-1);
                break;
            case -2147483647:
                space.setFC(-4144960);
                break;
            case -2147483646:
                space.setFC(-8355712);
                break;
            case -2147483645:
                space.setFC(-12566464);
                break;
            case -2147483644:
                space.setFC(-16777216);
                break;
            case -2147483643:
                space.setFC(0);
                break;
            case -2147483642:
                space.setSC(-1);
                space.sStyle(0);
                space.setSW(1);
                break;
            case -2147483641:
                space.setSC(-16777216);
                space.sStyle(0);
                space.setSW(1);
                break;
            case -2147483640:
                space.setSC(0);
                space.sStyle(0);
                space.setSW(1);
                break;
        }
    }

    private void readHeader(DataReader reader) throws IOException {
        int boundX = reader.getU32();
        int boundY = reader.getU32();
        this.boundX2 = reader.getU32();
        this.boundY2 = reader.getU32();
        int frameX = reader.getU32();
        int frameY = reader.getU32();
        int frameX2 = reader.getU32();
        int frameY2 = reader.getU32();
        int sign = reader.getU32();
        if (sign != 1179469088)
            throw new IOException("not a valid EMF file");
        reader.skip(4);
        reader.getU32();
        int nRecords = reader.getU32();
        this.objects = new EmfObject[nRecords + 1];
    }

    private static void setWorldTransform(DataReader reader, Space space) throws IOException {
        int m11 = reader.getU32();
        int m12 = reader.getU32();
        int m21 = reader.getU32();
        int m22 = reader.getU32();
        int DX = reader.getU32();
        int DY = reader.getU32();
        float[][] mm = new float[3][3];
        mm[0][0] = toFloat(m11);
        mm[0][1] = toFloat(m12);
        mm[1][0] = toFloat(m21);
        mm[1][1] = toFloat(m22);
        mm[2][0] = toFloat(DX);
        mm[2][1] = toFloat(DY);
        mm[2][2] = 1.0F;
        space.setMatrix(mm);
    }

    private static void modifyWorldTransform(DataReader reader, Space space) throws IOException {
        int m11 = reader.getU32();
        int m12 = reader.getU32();
        int m21 = reader.getU32();
        int m22 = reader.getU32();
        int DX = reader.getU32();
        int DY = reader.getU32();
        float[][] mm = new float[3][3];
        mm[0][0] = toFloat(m11);
        mm[0][1] = toFloat(m12);
        mm[1][0] = toFloat(m21);
        mm[1][1] = toFloat(m22);
        mm[2][0] = toFloat(DX);
        mm[2][1] = toFloat(DY);
        mm[2][2] = 1.0F;
        int mode = reader.getU32();
        switch (mode) {
            case 1:
                space.setMatrix(FM.getIdentity());
            case 2:
                mm = FM.multiply(space.getMatrix(), mm);
                space.setMatrix(mm);
                break;
            case 3:
                mm = FM.multiply(mm, space.getMatrix());
                space.setMatrix(mm);
                break;
            case 4:
                space.setMatrix(mm);
                break;
        }
    }

    private static float toFloat(int v) {
        return Float.intBitsToFloat(v);
    }

    private static void createdippatternbrushpt(DataReader reader, Space space) throws IOException {
        int start = reader.getPosition() - 12;
        reader.getU32();
        int offBmiSrc = reader.getU32();
        int cbBmiSrc = reader.getU32();
        int offBitsSrc = reader.getU32();
        int cbBitsSrc = reader.getU32();
        reader.moveTo(start + offBmiSrc);
        byte[] bHeader = new byte[cbBmiSrc];
        reader.read(bHeader);
        reader.moveTo(start + offBitsSrc);
        byte[] bData = new byte[cbBitsSrc];
        reader.read(bData);
        byte[] output = new byte[14 + cbBmiSrc + cbBitsSrc];
        WriterByteLittle writerByteLittle = new WriterByteLittle(output);
        writerByteLittle.putU16(19778);
        writerByteLittle.putU32(output.length);
        writerByteLittle.putU32(0);
        writerByteLittle.putU32(14 + cbBmiSrc);
        writerByteLittle.write(bHeader);
        writerByteLittle.write(bData);
        space.setDesign(new Design(output));
    }

    private static void stretchblt(DataReader reader, Space space) throws IOException {
        int start = reader.getPosition() - 8;
        reader.skip(16);
        int xDest = reader.getU32();
        int yDest = reader.getU32();
        int cxDest = reader.getU32();
        int cyDest = reader.getU32();
        int bbrs = reader.getU32();
        int xSrc = reader.getU32();
        int ySrc = reader.getU32();
        int m11 = reader.getU32();
        int m12 = reader.getU32();
        int m21 = reader.getU32();
        int m22 = reader.getU32();
        int DX = reader.getU32();
        int DY = reader.getU32();
        float[][] xform = new float[3][3];
        xform[0][0] = toFloat(m11);
        xform[0][1] = toFloat(m12);
        xform[1][0] = toFloat(m21);
        xform[1][1] = toFloat(m22);
        xform[2][0] = toFloat(DX);
        xform[2][1] = toFloat(DY);
        xform[2][2] = 1.0F;
        int bgColorSrc = 0xFF000000 | reader.getU8() << 16 | reader.getU8() << 8 | reader.getU8();
        reader.getU8();
        int usageSrc = reader.getU32();
        int offBmiSrc = reader.getU32();
        int cbBmiSrc = reader.getU32();
        int offBitsSrc = reader.getU32();
        int cbBitsSrc = reader.getU32();
        int cxSrc = reader.getU32();
        int cySrc = reader.getU32();
        reader.moveTo(start + offBmiSrc);
        byte[] bHeader = new byte[cbBmiSrc];
        reader.read(bHeader);
        reader.moveTo(start + offBitsSrc);
        byte[] bData = new byte[cbBitsSrc];
        reader.read(bData);
        byte[] output = new byte[14 + cbBmiSrc + cbBitsSrc];
        WriterByteLittle writerByteLittle = new WriterByteLittle(output);
        writerByteLittle.putU16(19778);
        writerByteLittle.putU32(output.length);
        writerByteLittle.putU32(0);
        writerByteLittle.putU32(14 + cbBmiSrc);
        writerByteLittle.write(bHeader);
        writerByteLittle.write(bData);
        float[][] sm = space.getMatrix();
        space.setMatrix(FM.concatenate(sm, xform));
        space.bmpImage(xDest, yDest, output);
        space.setMatrix(sm);
    }

    private static void stretchdibits(DataReader reader, Space space) throws IOException {
        int start = reader.getPosition() - 8;
        reader.skip(16);
        int xDest = reader.getU32();
        int yDest = reader.getU32();
        int xSrc = reader.getU32();
        int ySrc = reader.getU32();
        int cxSrc = reader.getU32();
        int cySrc = reader.getU32();
        int offBmiSrc = reader.getU32();
        int cbBmiSrc = reader.getU32();
        int offBitsSrc = reader.getU32();
        int cbBitsSrc = reader.getU32();
        int usageSrc = reader.getU32();
        int bop = reader.getU32();
        int cxDest = reader.getU32();
        int cyDest = reader.getU32();
        float[][] cm = space.getMatrix();
        float[][] im = FM.getIdentity();
        if (xDest != 0)
            im[0][0] = cxDest * 1.0F / cxSrc;
        if (yDest != 0)
            im[1][1] = cyDest * 1.0F / cySrc;
        space.setMatrix(FM.concatenate(cm, im));
        reader.moveTo(start + offBmiSrc);
        byte[] bHeader = new byte[cbBmiSrc];
        reader.read(bHeader);
        reader.moveTo(start + offBitsSrc);
        byte[] bData = new byte[cbBitsSrc];
        reader.read(bData);
        byte[] output = new byte[14 + cbBmiSrc + cbBitsSrc];
        WriterByteLittle writerByteLittle = new WriterByteLittle(output);
        writerByteLittle.putU16(19778);
        writerByteLittle.putU32(output.length);
        writerByteLittle.putU32(0);
        writerByteLittle.putU32(14 + cbBmiSrc);
        writerByteLittle.write(bHeader);
        writerByteLittle.write(bData);
        space.bmpImage(xDest, yDest, output);
        space.setMatrix(cm);
    }

    private static void textOut(DataReader reader, Space space, boolean is16) throws IOException {
        reader.skip(16);
        int gMode = reader.getU32();
        int xSCale = reader.getU32();
        int yScale = reader.getU32();
        int refX = reader.getU32();
        int refY = reader.getU32();
        int nChars = reader.getU32();
        int offString = reader.getU32();
        int options = reader.getU32();
        reader.skip(16);
        int offDX = reader.getU32();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nChars; i++) {
            char c = is16 ? (char)reader.getU16() : (char)reader.getU8();
            sb.append(c);
        }
        space.text(refX, refY, sb.toString());
    }

    private static void smalltextout(DataReader reader, Space space) throws IOException {
        int x = reader.getU32();
        int y = reader.getU32();
        int cChars = reader.getU32();
        int fuOptions = reader.getU32();
        int iGraphicsMode = reader.getU32();
        int exScale = reader.getU32();
        int eyScale = reader.getU32();
        reader.skip(16);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cChars; i++) {
            char c = (fuOptions == 512) ? (char)reader.getU16() : (char)reader.getU8();
            sb.append(c);
        }
        space.text(x, y, sb.toString());
    }
}
