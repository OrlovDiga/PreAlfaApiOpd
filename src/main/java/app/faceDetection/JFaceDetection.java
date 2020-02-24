package app.faceDetection;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class JFaceDetection {
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String imgFile = "src/main/resources/images/dJgDqTSs94c.jpg";
        Mat src = Imgcodecs.imread(imgFile);

        String xmlFile = "src/main/xml/lbpcascade_frontalface.xml";
        CascadeClassifier cc = new CascadeClassifier(xmlFile);

        MatOfRect faceDetection = new MatOfRect();
        cc.detectMultiScale(src, faceDetection);
        System.out.println(String.format("Detected faces: %d", faceDetection.toArray().length));

        for (Rect rect: faceDetection.toArray()) {
            Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 3);
        }

        Imgcodecs.imwrite("src/main/resources/images/dJgDqTSs94c_out.jpg", src);
        System.out.println("Image Detection Finished");
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("Done");



    }
}
