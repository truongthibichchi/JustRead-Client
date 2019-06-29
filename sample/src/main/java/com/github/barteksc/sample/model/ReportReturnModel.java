package com.github.barteksc.sample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportReturnModel {
    int kt;
    int ndc;
    int stn;
    int tshk;
    int tlkns;
    int vh;
    int user;
}
