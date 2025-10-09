// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.review.dto;

import com.core.base.corebase.domain.review.code.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionReq {
    private String question;
    private QuestionType type;
    private List<ChoiceReq> choices;
    private Integer limit;
    private Integer orderNum;
    private Boolean useScore;
    private Boolean useMultiSelect;
}
