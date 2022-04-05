package leachimsumiri.anyline.VideoFrameExtractionService.utils;

import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.containers.mp4.demuxer.MP4Demuxer;
import org.jcodec.scale.AWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static String generateHashFromByteArray(byte[] bytes) throws IOException {
        return SHAsum(bytes);
    }

    //https://stackoverflow.com/questions/1515489/compute-sha-1-of-byte-array
    public static String SHAsum(byte[] convertme) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return byteArray2Hex(md.digest(convertme));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("SHA-1 algorithm couldn't be retrieved from MessageDigest:");
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static byte[] pictureToByteArray(Picture frame) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedImage bi = AWTUtil.toBufferedImage(frame);
            ImageIO.write(bi, "png", byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("exception at Picture to byte[] conversion:");
            e.printStackTrace();
            throw e;
        }
    }

    public static int getFrameLength(MultipartFile file) throws IOException {
        try {
            FileChannelWrapper channelWrapper = NIOUtils.readableChannel(convertMultiPartToFile(file));
            MP4Demuxer demuxer = MP4Demuxer.createMP4Demuxer(channelWrapper);
            DemuxerTrack video_track = demuxer.getVideoTrack();

            return video_track.getMeta().getTotalFrames();
        } catch (IOException e) {
            LOGGER.error("exception at getting frameLength from MultipartFile:");
            e.printStackTrace();
            throw e;
        }
    }

    public static File convertMultiPartToFile(MultipartFile file ) throws IOException {
        try {
            File convFile = new File(file.getOriginalFilename() != null ? file.getOriginalFilename() : "tempFile");
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            return convFile;
        } catch (IOException e) {
            LOGGER.error("exception at converting MultipartFile to File:");
            e.printStackTrace();
            throw e;
        }
    }

    public static void fileCleanup(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        try {
            if (fileName != null) Files.deleteIfExists(Path.of(fileName));
        } catch (IOException e) {
            LOGGER.error("exception at File cleanup:");
            e.printStackTrace();
            throw e;
        }
    }
}
