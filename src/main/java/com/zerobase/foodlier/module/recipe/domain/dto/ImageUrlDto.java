package com.zerobase.foodlier.module.recipe.domain.dto;

import com.zerobase.foodlier.module.recipe.domain.vo.RecipeDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageUrlDto {
    private String mainImageUrl;
    private List<RecipeDetail> recipeDetailList;

}