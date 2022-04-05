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

public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static byte[] pictureToByteArray(Picture frame) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedImage bi = AWTUtil.toBufferedImage(frame);
            ImageIO.write(bi, "png", byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("exception at Picture to byte[] conversion");
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
            LOGGER.error("exception at getting frameLength from MultipartFile");
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
            LOGGER.error("exception at converting MultipartFile to File");
            throw e;
        }
    }
}
