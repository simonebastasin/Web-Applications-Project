package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.media.CreateMediaDatabase;
import it.unipd.dei.wa2122.wadteam.dao.media.GetMediaByteFromMediaDatabase;
import it.unipd.dei.wa2122.wadteam.dao.media.GetMediaDatabase;
import it.unipd.dei.wa2122.wadteam.dao.media.ListMediaDatabase;
import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
import it.unipd.dei.wa2122.wadteam.resources.Media;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MediaServlet extends AbstractDatabaseServlet {
    private final int THUMB_SIZE = 200;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo() != null ? request.getPathInfo().substring(1).lastIndexOf('/') != -1 ? request.getPathInfo().substring(1,request.getPathInfo().lastIndexOf('/')) : request.getPathInfo().substring(1) : "";
        String param = request.getPathInfo() != null ? request.getPathInfo().substring(1).lastIndexOf('/') != -1 ? request.getPathInfo().substring(request.getPathInfo().lastIndexOf('/')+1) : "" : "";


        switch (path) {
            case "list" -> {
                try {
                    List<Media> mediaList = new ListMediaDatabase(getDataSource().getConnection()).getMedia();

                    writeResource(request,response, "/jsp/media.jsp", false, mediaList.toArray(Resource[]::new));
                } catch (SQLException e) {
                    writeError(request, response, new ErrorMessage.SqlInternalError(e.getMessage()));
                }
            }
            case "upload" -> {
                writeJsp(request, response, "/jsp/upload.jsp");
            }
            case "thumb" -> {
                if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
                    writeImage(request, response, param, true);
                }
            }
            case "view" -> {
                if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
                    writeImage(request, response, param, false);
                }
            }
            default -> writeError(request, response, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo() != null ? request.getPathInfo().substring(1).lastIndexOf('/') != -1 ? request.getPathInfo().substring(1,request.getPathInfo().lastIndexOf('/')) : request.getPathInfo().substring(1) : "";
        String param = request.getPathInfo() != null ? request.getPathInfo().substring(1).lastIndexOf('/') != -1 ? request.getPathInfo().substring(request.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "upload" -> uploadImage(request, response);
            default -> doGet(request, response);
        }
    }

    private void writeImage(HttpServletRequest request, HttpServletResponse response, String path, boolean thumb) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(path);

            Media media = new GetMediaDatabase(getDataSource().getConnection(), id).getMedia();
            if(media != null) {
                byte[] blob = new GetMediaByteFromMediaDatabase(getDataSource().getConnection(), id, thumb).getMedia();

                if (blob != null && blob.length > 0) {
                    writeBlob(response, blob, media.getMimetype(), thumb ? media.getFilename() + "_thumb.png" : media.getFilename(), true);
                } else {
                    writeError(request,response, new ErrorMessage.EmptyMediaError("The media is empty"));
                }

            } else {
                writeError(request, response, new ErrorMessage.MediaNotFoundError("The media wasn't found"));
            }

        } catch (SQLException e) {
            writeError(request, response, new ErrorMessage.SqlInternalError(e.getMessage()));
        } catch (NumberFormatException e) {
            writeError(request, response, new ErrorMessage.IncorrectlyFormattedPathError("The media ID is not in the correct format"));
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

    private void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        String filename = filePart.getSubmittedFileName();
        String mimetype = filePart.getContentType();
        InputStream filePartInputStream = filePart.getInputStream();
        byte[] bytes = filePartInputStream.readAllBytes();

        try {

            Media media = new CreateMediaDatabase(getDataSource().getConnection(), filename, mimetype, bytes,mimetype.startsWith("image") ? scaleImage(bytes, THUMB_SIZE) : null).createMedia();

            if(media != null) {
                Message m = new Message("Upload completed successfully.",
                        media.getId());
                writeResource(request,response, "/jsp/message.jsp",true, m);
            } else {
                writeError(request, response, new ErrorMessage.UploadError("There was a problem with upload"));
            }

        } catch (SQLException e) {
            writeError(request, response, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

}