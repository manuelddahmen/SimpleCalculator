package one.empty3.feature20220726;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import one.empty3.library.ColorTexture;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.Polygon;
import one.empty3.library.Serialisable;
import one.empty3.library.StructureMatrix;

public class GoogleFaceDetection
        implements Parcelable, Serializable, Serialisable {
    static GoogleFaceDetection instance;
    private FaceData.Surface selectedSurface;
    public static double[] TRANSPARENT = Lumiere.getDoubles(Color.BLACK);
    private List<FaceData> dataFaces;

    public static GoogleFaceDetection getInstance(boolean newInstance) {
        if(instance==null || newInstance)
            instance = new GoogleFaceDetection();
        return instance;
    }
    public static boolean isInstance() {
        if(instance!=null)
            return true;
        else
            return false;
    }

    protected GoogleFaceDetection(Parcel in) {
        in.readTypedObject(new Creator<Object>() {
            @Override
            public Object createFromParcel(Parcel parcel) {
                GoogleFaceDetection googleFaceDetection = GoogleFaceDetection.getInstance(false);
                int numFaces = parcel.readInt();
                for (int face = 0; face < numFaces; face++) {
                    int id = parcel.readInt();

                    FaceData faceData = new FaceData();

                    int numPoly = parcel.readInt();

                    for (int i = 0; i < numPoly; i++) {
                        Polygon polygon = Polygon.CREATOR.createFromParcel(parcel);
                        PixM contours = PixM.CREATOR.createFromParcel(parcel);
                        int colorFill = parcel.readInt();
                        int colorContours = parcel.readInt();
                        int colorTransparent = parcel.readInt();

                        faceData.getFaceSurfaces().add(new FaceData.Surface(id, polygon, contours, colorFill, colorContours,
                                colorTransparent));

                    }
                    googleFaceDetection.getDataFaces().add(faceData);
                }
                return googleFaceDetection;
            }

            @Override
            public Object[] newArray(int i) {
                return new Object[0];
            }
        });
    }

    public static final Creator<GoogleFaceDetection> CREATOR = new Creator<GoogleFaceDetection>() {
        @Override
        public GoogleFaceDetection createFromParcel(Parcel in) {
            return new GoogleFaceDetection(in);
        }

        @Override
        public GoogleFaceDetection[] newArray(int size) {
            return new GoogleFaceDetection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(getDataFaces().size());
        for (int face = 0; face < getDataFaces().size(); face++) {
            parcel.writeInt(getDataFaces().size());

            FaceData faceData =getDataFaces().get(face);

            parcel.writeInt(faceData.getFaceSurfaces().size());

            for (int j = 0; j < faceData.getFaceSurfaces().size(); j++) {
                FaceData.Surface surface = faceData.getFaceSurfaces().get(j);
                parcel.writeParcelable(surface.polygon, 1);
                parcel.writeParcelable(surface.contours, 1);
                parcel.writeInt(surface.colorFill);
                parcel.writeInt(surface.colorContours);
                parcel.writeInt(surface.colorTransparent);

            }
        }
    }


    public static class FaceData implements Serializable {

        public static class Surface implements Serializable, Serialisable{
            public Surface() {

            }

            @Override
            public Serialisable decode(DataInputStream in) {
                try {
                    colorFill = in.readInt();
                    colorContours = in.readInt();
                    colorTransparent = in.readInt();
                    surfaceId = in.readInt();
                    polygon.decode(in);
                    contours.decode(in);
                    filledContours.decode(in);
                    actualDrawing.decode(in);

                    return (Surface)this;
                } catch (Exception exception) {
                    return null;
                }
            }

            @Override
            public int encode(DataOutputStream out) {
                try {
                    out.write(colorFill);
                    out.write(colorContours);
                    out.write(colorTransparent);
                    out.write(surfaceId);
                    polygon.encode(out);
                    contours.encode(out);
                    filledContours.encode(out);
                    actualDrawing.encode(out);

                } catch (Exception exception) {
                    return -1;
                }
                return 0;
            }

            @Override
            public int type() {
                return 5;
            }
            private int colorFill;
            private int colorContours;
            private int colorTransparent;
            private int surfaceId;
            private Polygon polygon;
            private PixM contours;
            private PixM filledContours;
            @Nullable
            public PixM actualDrawing;

            public Surface(int surfaceId, Polygon polygon, PixM contours, int colorFill, int colorContours, int colorTransparent) {
                this.surfaceId = surfaceId;
                this.polygon = polygon;
                this.contours = contours;
                this.colorFill = colorFill;
                this.colorContours = colorContours;
                this.colorTransparent = colorTransparent;
            }

            public int getSurfaceId() {
                return surfaceId;
            }

            public void setSurfaceId(int surfaceId) {
                this.surfaceId = surfaceId;
            }

            public Polygon getPolygon() {
                return polygon;
            }

            public void setPolygon(Polygon polygon) {
                this.polygon = polygon;
            }

            public PixM getContours() {
                return contours;
            }

            public void setContours(PixM contours) {
                this.contours = contours;
            }

            public int getColorFill() {
                return colorFill;
            }

            public void setColorFill(int colorFill) {
                this.colorFill = colorFill;
            }

            public int getColorContours() {
                return colorContours;
            }

            public void setColorContours(int colorContours) {
                this.colorContours = colorContours;
            }

            public int getColorTransparent() {
                return colorTransparent;
            }

            public void setColorTransparent(int colorTransparent) {
                this.colorTransparent = colorTransparent;
            }

            public void computeFilledSurface(PointF position, double scale) {
                polygon.texture(new ColorTexture(colorFill));
                filledContours = polygon.fillPolygon2D(this, null, contours.getBitmap(),
                        colorTransparent, 0, position, scale);
            }

            public boolean isContaning(Point pInPicture) {
                StructureMatrix<Point3D> boundRect2d = polygon.getBoundRect2d();
                double[] values = contours.getValues((int) (double) (pInPicture.x - boundRect2d.getElem(0).get(0)),
                        (int) (double) (pInPicture.y - boundRect2d.getElem(0).get(1)));
                return values.equals(TRANSPARENT);
            }

            @Override
            public String toString() {
                return "Surface{" +
                        "colorFill=" + colorFill +
                        ", colorContours=" + colorContours +
                        ", colorTransparent=" + colorTransparent +
                        ", surfaceId=" + surfaceId +
                        ", polygon=" + polygon +
                        ", contours=" + contours +
                        ", actualDrawing=" + actualDrawing +
                        '}';
            }

            public void setFilledContours(PixM filledContours) {
                this.filledContours = filledContours;
            }

            public PixM getFilledContours() {
                return filledContours;
            }

            @Nullable
            public PixM getActualDrawing() {
                return actualDrawing;
            }

            public void setActualDrawing(@Nullable PixM actualDrawing) {
                this.actualDrawing = actualDrawing;
            }
        }

        private List<Surface> faceSurfaces;

        public List<Surface> getFaceSurfaces() {
            return faceSurfaces;
        }

        public void setFaceSurfaces(List<Surface> faceSurfaces) {
            this.faceSurfaces = faceSurfaces;
        }

        public FaceData(List<Surface> faceSurfaces) {
            this.faceSurfaces = faceSurfaces;
            if(faceSurfaces==null)
                this.faceSurfaces = new ArrayList<>();
        }
        public FaceData() {
            this.faceSurfaces = new ArrayList<>();
        }
    }
    public GoogleFaceDetection(List<FaceData> dataFaces) {
        this.dataFaces = dataFaces;
    }
    public GoogleFaceDetection() {
        dataFaces = new ArrayList<>();
    }

    public List<FaceData> getDataFaces() {
        return dataFaces;
    }

    public void setDataFaces(List<FaceData> dataFaces) {
        this.dataFaces = dataFaces;
    }

    public FaceData.Surface getSurface(Point pInPicture) {
        final FaceData.Surface[] surface = {null};
        for (FaceData dataFace : dataFaces) {
            dataFace.getFaceSurfaces().forEach(new Consumer<FaceData.Surface>() {
                @Override
                public void accept(FaceData.Surface surface1) {
                    if(surface1.isContaning(pInPicture))
                        surface[0] = surface1;
                }
            });
        }
        if(surface[0]!=null)
            return surface[0];
        else
            return null;
    }

    public FaceData.Surface getSelectedSurface() {
        return selectedSurface;
    }

    public void setSelectedSurface(FaceData.Surface selectedSurface) {
        this.selectedSurface = selectedSurface;
    }

    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public Serialisable decode(DataInputStream in) {
        try {
            GoogleFaceDetection faceDetection =  new GoogleFaceDetection();
            int countFaces= in.readInt();
            int count = 0;
            for(int c = 0; c<countFaces; c++) {
                    FaceData faceData = new FaceData();
                faceDetection.getDataFaces().add(faceData);
                    int count2 = in.readInt();
                    for (int j = 0; j < count2; j++) {
                        Serialisable decode = new FaceData.Surface().decode(in);
                        faceDetection.getDataFaces().get(c)
                                .getFaceSurfaces().add((FaceData.Surface) decode);
                    }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
        @Override
    public int encode(DataOutputStream out) {
        try {
            out.writeInt(dataFaces.size());
            dataFaces.forEach(faceData -> {
                try {
                    out.writeInt(faceData.getFaceSurfaces().size());
                    faceData.faceSurfaces.forEach(surface -> {
                        surface.encode(out);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (RuntimeException ex) {
            ex.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public int type() {
        return 0;
    }

}
