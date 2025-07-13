package com.evoting.evote_backend.dto;
import lombok.Data;
import java.util.List;

@Data
public class ElectionRequestDTO {
    private String title;
    private List<String> options;
    private List<String> voters;
}

