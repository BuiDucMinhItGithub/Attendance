package com.mstudent.mapper;

import com.mstudent.model.dto.response.Cost.CostResponse;
import com.mstudent.model.entity.Cost;
import java.util.List;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {RoomMapper.class, StudentMapper.class})
public interface CostMapper {
  CostMapper INSTANCE = Mappers.getMapper(CostMapper.class);

  @Mapping(target = "room", qualifiedByName = "entityToCostResponse")
  @Mapping(target = "student", qualifiedByName = "entityToShortResponse")
  CostResponse entityToResponse(Cost cost);

  List<CostResponse> listEntityToResponse(List<Cost> costs);

}
