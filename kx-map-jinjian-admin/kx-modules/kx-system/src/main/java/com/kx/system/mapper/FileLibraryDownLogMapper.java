package com.kx.system.mapper;

import com.kx.system.domain.FileLibraryDownLog;

public interface FileLibraryDownLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileLibraryDownLog record);

    int insertSelective(FileLibraryDownLog record);

    FileLibraryDownLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileLibraryDownLog record);

    int updateByPrimaryKey(FileLibraryDownLog record);

    int selectCount(Long fileLibraryId);


}
