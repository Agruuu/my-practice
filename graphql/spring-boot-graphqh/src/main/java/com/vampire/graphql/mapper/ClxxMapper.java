package com.vampire.graphql.mapper;

import com.vampire.graphql.entity.ClxxVoAdd;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClxxMapper {

    @Insert("INSERT INTO graphql.clxx (cldm,cph,cpys,vin) " +
            "VALUES(#{cldm,jdbcType=VARCHAR},#{cph,jdbcType=VARCHAR},#{cpys,jdbcType=INTEGER},#{vin,jdbcType=VARCHAR})")
    int addClxx(ClxxVoAdd clxxVoAdd);
}
