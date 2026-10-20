package com.kx.system.mapper;

import com.kx.system.domain.AnchorComplaintEvents;

import java.util.List;

public interface ComplaintInfoMapper {
    List<AnchorComplaintEvents> findByAnchorName(String anchorName);
    AnchorComplaintEvents findLatestByAnchorName(String anchorName);
    AnchorComplaintEvents findById(Long id);
    int insert(AnchorComplaintEvents anchorComplaintEvents);
}