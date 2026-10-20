package com.kx.system.mapper;

import com.kx.system.domain.FileLibraryInfo;
import com.kx.system.domain.vo.FileLibraryInfoSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileLibraryInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileLibraryInfo record);

    int insertSelective(FileLibraryInfo record);

    FileLibraryInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileLibraryInfo record);

    int updateByPrimaryKey(FileLibraryInfo record);
    List<FileLibraryInfo> selectFileLibraryPage(FileLibraryInfoSearch record);
    int deleteBatchFileLibrary(@Param("ids") List<Long> ids);
}
