package com.example.sweater.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @NotBlank(message = "Please, fill the message")
    @Length(max = 2048, message = "Message is too long")
    private String text;
    @NonNull
    @NotBlank(message = "Please, fill the tag")
    @Length(max = 255, message = "Tag is too long")
    private String tag;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public String getNormalFilename() {
        return filename.substring(37);
    }
}
