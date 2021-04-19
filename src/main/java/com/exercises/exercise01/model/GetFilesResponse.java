package com.exercises.exercise01.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetFilesResponse implements Serializable {

    @JsonProperty("data")
    private List<FileDetailModel> filesDetails;

    @JsonProperty("page")
    private Integer pageNumber;

    @JsonProperty("totalRows")
    private Integer totalRows;

    @JsonProperty("pageSize")
    private Integer pageSize;

    public GetFilesResponse() {
    }

    public GetFilesResponse(List<FileDetailModel> filesDetails, Integer pageNumber, Integer totalRows, Integer pageSize) {
        this.filesDetails = filesDetails;
        this.pageNumber = pageNumber;
        this.totalRows = totalRows;
        this.pageSize = pageSize;
    }

    public List<FileDetailModel> getFilesDetails() {
        return filesDetails;
    }

    public void setFilesDetails(List<FileDetailModel> filesDetails) {
        this.filesDetails = filesDetails;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "GetFilesResponse{" +
                "filesDetails=" + filesDetails +
                ", pageNumber=" + pageNumber +
                ", totalRows=" + totalRows +
                ", pageSize=" + pageSize +
                '}';
    }
}
