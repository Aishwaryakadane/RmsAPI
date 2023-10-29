package com.AK.Rms.Model.postDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddPostDto {
    private String postCaption;
    private Long recipeId;
}
