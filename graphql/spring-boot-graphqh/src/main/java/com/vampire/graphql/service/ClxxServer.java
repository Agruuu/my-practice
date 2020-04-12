package com.vampire.graphql.service;

import com.vampire.graphql.entity.ClxxVoAdd;
import com.vampire.graphql.entity.ResultScalar;
import com.vampire.graphql.mapper.ClxxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClxxServer {

    @Autowired
    ClxxMapper clxxMapper;

    @Transactional
    public ResultScalar addClxx(ClxxVoAdd clxxVoAdd) {
        clxxMapper.addClxx(clxxVoAdd);
        return ResultScalar.success("车辆信息数据以插入成功!");
    }
}
