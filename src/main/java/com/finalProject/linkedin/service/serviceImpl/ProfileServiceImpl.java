package com.finalProject.linkedin.service.serviceImpl;

import com.finalProject.linkedin.dto.request.profile.CreateProfileReq;
import com.finalProject.linkedin.dto.responce.profile.CreateProfileResp;
import com.finalProject.linkedin.entity.Profile;
import com.finalProject.linkedin.exception.NotFoundException;
import com.finalProject.linkedin.mapper.ProfileMapper;
import com.finalProject.linkedin.repository.ProfileRepository;
import com.finalProject.linkedin.service.serviceIR.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * description
 *
 * @author Alexander Isai on 10.10.2024.
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    @Override
    @Transactional
    public CreateProfileResp createProfile(CreateProfileReq createProfileReq) {
        Profile profile = profileMapper.toProfile(createProfileReq);

        profile = profileRepository.save(profile);

        return profileMapper.toCreateProfileResp(profile);
    }
    @Override
    public CreateProfileResp getProfileById(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("Profile not found with id " + profileId));
        return profileMapper.toCreateProfileResp(profile);
    }

    @Override
    public Page<CreateProfileResp> getAllProfiles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Profile> profilePage = profileRepository.findAll(pageable);
        return profilePage.map(profileMapper::toCreateProfileResp);
    }

    @Override
    public CreateProfileResp updateProfile(Long profileId, CreateProfileReq createProfileReq) {
        Profile existingProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("Profile not found with id " + profileId));

        existingProfile.setName(createProfileReq.name());
        existingProfile.setSurname(createProfileReq.surname());
        existingProfile.setBirthdate(createProfileReq.birthdate());
        existingProfile.setPosition(createProfileReq.position());
        existingProfile.setAddress(createProfileReq.address());
        existingProfile.setStatus(createProfileReq.status());

        Profile updatedProfile = profileRepository.save(existingProfile);
        return profileMapper.toCreateProfileResp(updatedProfile);
    }

    @Override
    public void deleteProfile(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("Profile not found with id " + profileId));

        profile.setDeletedAt(LocalDateTime.now());
        profileRepository.save(profile);
    }

}
