package leachimsumiri.anyline.VideoFrameExtractionService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("id")
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videoId")
    private Video video;
}
