package com.kx.system.service;

import com.kx.system.domain.AnchorComplaintEvents;

import java.util.List;

public interface ComplaintInfoService {
    List<AnchorComplaintEvents> findByAnchorName(String anchorName);
    AnchorComplaintEvents findLatestByAnchorName(String anchorName);
    AnchorComplaintEvents findById(Long id);
    int addComplaint(AnchorComplaintEvents anchorComplaintEvents);
}