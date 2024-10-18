package com.finalProject.linkedin.service.serviceIR;

import com.finalProject.linkedin.dto.request.profile.CreateProfileReq;
import com.finalProject.linkedin.dto.responce.profile.CreateProfileResp;
import org.springframework.data.domain.Page;

/**
 * description
 *
 * @author Alexander Isai on 10.10.2024.
 */
public interface ProfileService {
    CreateProfileResp createProfile(CreateProfileReq createProfileReq);
    CreateProfileResp getProfileById(Long profileId);
    Page<CreateProfileResp> getAllProfiles(int page, int size);
    CreateProfileResp updateProfile(Long profileId, CreateProfileReq createProfileReq);
    void deleteProfile(Long profileId);
}
