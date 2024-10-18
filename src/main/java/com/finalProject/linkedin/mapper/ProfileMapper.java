package com.finalProject.linkedin.mapper;

import com.finalProject.linkedin.dto.request.profile.CreateProfileReq;
import com.finalProject.linkedin.dto.responce.profile.CreateProfileResp;
import com.finalProject.linkedin.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * description
 *
 * @author Alexander Isai on 17.10.2024.
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Profile toProfile(CreateProfileReq createProfileReq);

    CreateProfileResp toCreateProfileResp(Profile profile);
}
