package uz.ichange.billing.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "localization")
@Getter
@Setter
public class MultiLanguageMessage {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String en;
    private String ru;
    private String uz;
    @Column(unique = true)
    private int code;
    private String original;

    public MultiLanguageMessage(int code, String uz, String ru, String en) {
        this.en = en;
        this.ru = ru;
        this.uz = uz;
        this.code = code;
    }

    public MultiLanguageMessage() {

    }
}
