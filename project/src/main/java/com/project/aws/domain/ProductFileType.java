package com.project.aws.domain;


public enum ProductFileType {

    CSV("aws-billing-csv"),
    LINE_ITEMS("aws-billing-detailed-line-items"),
    DETAILED_LINE_ITEMS("aws-billing-detailed-line-items-with-resources-and-tags"),
    COST_ALLOCATION("aws-cost-allocation");

    private final String fileString;

    ProductFileType(String fileString) {
        this.fileString = fileString;
    }

    public String toFileString() {
        return fileString;
    }



}
