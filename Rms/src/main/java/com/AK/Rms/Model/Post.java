package com.AK.Rms.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String postCaption;

    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime postCreationTimestamp;
     // @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime postUpdationTimeStamp;

    @ManyToOne
    @JoinColumn(name = "fk-userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_recipeId")
    private Recipe recipe;


}

