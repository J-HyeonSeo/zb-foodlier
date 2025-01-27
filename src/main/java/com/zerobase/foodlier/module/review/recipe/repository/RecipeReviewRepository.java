package com.zerobase.foodlier.module.review.recipe.repository;

import com.zerobase.foodlier.module.member.member.domain.model.Member;
import com.zerobase.foodlier.module.recipe.domain.model.Recipe;
import com.zerobase.foodlier.module.review.recipe.domain.model.RecipeReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeReviewRepository extends JpaRepository<RecipeReview, Long>, RecipeReviewRepositoryCustom {

    boolean existsByMemberAndRecipe(Member member, Recipe recipe);

    Optional<RecipeReview> findByIdAndMember(Long recipeReviewId, Member member);

    Optional<RecipeReview> findByMemberAndRecipe(Member member, Recipe recipe);

    Page<RecipeReview> findByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);

}
