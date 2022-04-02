package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.media.CreateMediaDatabase;
import it.unipd.dei.wa2122.wadteam.resources.Media;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.Collection;

public class UploadMediaServlet extends AbstractDatabaseServlet {
    private final int THUMB_SIZE = 200;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeResource(req, resp, "/jsp/upload.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String filename = filePart.getSubmittedFileName();
        Collection<String> headers = filePart.getHeaderNames();
        String mimetype = filePart.getContentType();
        long fileSize = filePart.getSize();
        InputStream filePartInputStream = filePart.getInputStream();
        byte[] bytes = filePartInputStream.readAllBytes();

        try {

            Media media = new CreateMediaDatabase(getDataSource().getConnection(), filename, mimetype, bytes,mimetype.startsWith("image") ? scaleImage(bytes, THUMB_SIZE) : null).createMedia();

            if(media != null) {
                Message m = new Message("Upload completed successfully.",
                        media.getId());
                writeResource(request,response, "/jsp/message.jsp",m);
            } else {
                Message m = new Message("Upload not completed.", "EU01", "There was a problem with upload");
                writeError(request, response, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (SQLException e) {
            Message m = new Message("Upload not completed.", "EU01", "There was a problem with upload");
            writeError(request, response, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }

    }

    private byte[] scaleImage(byte[] source, int size) {
        try {

            BufferedImage sourceImage = ImageIO.read(new ByteArrayInputStream(source));
            int width = sourceImage.getWidth();
            int height = sourceImage.getHeight();
            float dimMin = Math.min(width, height);
            float dimMax = Math.max(width, height);
            float extraSize = dimMin - size;
            float percent = dimMax - (dimMax  * extraSize / dimMin);
            int nWidth = width > height ? (int) percent : size;
            int nHeight = width > height ? size : (int) percent;
            BufferedImage img = new BufferedImage(nWidth, nHeight, typeBufferedImage(sourceImage));
            Image scaledImage = sourceImage.getScaledInstance(nWidth, nHeight, Image.SCALE_SMOOTH);
            img.createGraphics().drawImage(scaledImage, 0, 0, null);

            int nX = width > height ? (int) ((percent - size) / 2) : 0;
            int nY = width > height ? 0 : (int) ((percent - size) / 2);
            BufferedImage squareImg = img.getSubimage(nX, nY, size, size);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ImageIO.write(squareImg, "png", bos);
            return bos.toByteArray();


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private int typeBufferedImage(BufferedImage image) {
        return  (image.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    }

}
