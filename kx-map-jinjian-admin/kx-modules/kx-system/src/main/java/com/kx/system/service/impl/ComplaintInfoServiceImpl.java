package com.kx.system.service.impl;

import com.kx.system.domain.AnchorComplaintEvents;
import com.kx.system.mapper.ComplaintInfoMapper;
import com.kx.system.service.ComplaintInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintInfoServiceImpl implements ComplaintInfoService {

    private final ComplaintInfoMapper complaintInfoMapper;

    @Autowired
    public ComplaintInfoServiceImpl(ComplaintInfoMapper complaintInfoMapper) {
        this.complaintInfoMapper = complaintInfoMapper;
    }

    @Override
    public List<AnchorComplaintEvents> findByAnchorName(String anchorName) {
        return complaintInfoMapper.findByAnchorName(anchorName);
    }

    @Override
    public AnchorComplaintEvents findLatestByAnchorName(String anchorName) {
        return complaintInfoMapper.findLatestByAnchorName(anchorName);
    }

    @Override
    public AnchorComplaintEvents findById(Long id) {
        return complaintInfoMapper.findById(id);
    }

    @Override
    public int addComplaint(AnchorComplaintEvents anchorComplaintEvents) {
        anchorComplaintEvents.setUpdateTime(LocalDateTime.now());
        anchorComplaintEvents.setCreateTime(LocalDateTime.now());

        return complaintInfoMapper.insert(anchorComplaintEvents);
    }
}