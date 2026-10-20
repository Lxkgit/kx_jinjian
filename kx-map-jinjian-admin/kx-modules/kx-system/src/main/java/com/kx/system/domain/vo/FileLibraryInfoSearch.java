package com.kx.system.domain.vo;

import com.kx.system.domain.FileLibraryInfo;
import lombok.Data;

import java.util.List;

@Data
public class FileLibraryInfoSearch {

    private Long id;
    private String fileName;
    private List<Long> ids;

}
