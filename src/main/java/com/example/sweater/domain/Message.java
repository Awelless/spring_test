package com.example.sweater.domain;

import com.example.sweater.domain.util.MessageHelper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please, fill the message")
    @Length(max = 2048, message = "Message is too long")
    private String text;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "message_tag", joinColumns = @JoinColumn(name = "message_id"))
    private List<String> tags;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;
    private String normalFilename;

    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = { @JoinColumn(name = "message_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> likes = new HashSet<>();

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }

    public String getTagsAsString() {
        if (tags == null || tags.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (String tag : tags) {
            builder.append('#');
            builder.append(tag);
            builder.append(' ');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public boolean isUserLiked(User user) {
        return likes.contains(user);
    }

    public int getLikesNumber() {
        return likes.size();
    }

    @Override
    public String toString() {
        return text;
    }
}
