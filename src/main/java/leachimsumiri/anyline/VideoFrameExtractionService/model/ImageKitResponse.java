package leachimsumiri.anyline.VideoFrameExtractionService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageKitResponse {
    private boolean isSuccessful;
    private String message;
    private String fileId;
    private String name;
    private String url;
    private int height;
    private int width;
    private long size;
    private String filePath;
    private String fileType;
}
