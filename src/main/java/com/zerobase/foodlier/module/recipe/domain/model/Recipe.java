package com.zerobase.foodlier.module.recipe.domain.model;


import com.zerobase.foodlier.common.jpa.audit.Audit;
import com.zerobase.foodlier.module.comment.comment.domain.model.Comment;
import com.zerobase.foodlier.module.heart.domain.model.Heart;
import com.zerobase.foodlier.module.member.member.domain.model.Member;
import com.zerobase.foodlier.module.recipe.domain.type.Difficulty;
import com.zerobase.foodlier.module.recipe.domain.vo.RecipeDetail;
import com.zerobase.foodlier.module.recipe.domain.vo.RecipeIngredient;
import com.zerobase.foodlier.module.recipe.domain.vo.RecipeStatistics;
import com.zerobase.foodlier.module.recipe.domain.vo.Summary;
import com.zerobase.foodlier.module.review.recipe.domain.model.RecipeReview;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Summary summary;

    private String mainImageUrl;

    private int expectedTime;

    private int heartCount;

    @Embedded
    private RecipeStatistics recipeStatistics;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private Boolean isPublic;

    @Builder.Default
    private Boolean isQuotation = false;

    @Builder.Default
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    @Builder.Default
    private List<RecipeReview> recipeReviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Heart> heartList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "recipe_detail")
    @Builder.Default
    private List<RecipeDetail> recipeDetailList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "recipe_ingredient")
    @Builder.Default
    private List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

}
