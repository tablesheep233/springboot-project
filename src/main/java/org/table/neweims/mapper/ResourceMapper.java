package org.table.neweims.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ResourceMapper {

    List<String> selectPermsByUsername(String name);
}