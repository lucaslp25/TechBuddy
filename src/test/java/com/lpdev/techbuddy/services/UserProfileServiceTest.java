package com.lpdev.techbuddy.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpdev.techbuddy.dto.UpdateDevProfileDTO;
import com.lpdev.techbuddy.dto.UpdateMentorProfileDTO;
import com.lpdev.techbuddy.model.entities.DevProfile;
import com.lpdev.techbuddy.model.entities.MentorProfile;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.enums.UserRole;
import com.lpdev.techbuddy.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class UserProfileServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User devUser, mentorUser;

    @BeforeEach
    void setUp() {
        //sempre bom limpar o rep antes
        userRepository.deleteAll();

        // colocar o user no banco de dados
        devUser = new User();
        devUser.setName("Dev User");
        devUser.setEmail("dev@test.com");
        devUser.setPassword(passwordEncoder.encode("123456"));
        devUser.setRole(UserRole.DEV_BUDDY);

        // associa o perfil DEV
        DevProfile devProfile = new DevProfile();
        devProfile.setUser(devUser);
        devUser.setUserProfile(devProfile);

        //  usuário MENTOR
        mentorUser = new User();
        mentorUser.setName("Mentor User");
        mentorUser.setEmail("mentor@test.com");
        mentorUser.setPassword(passwordEncoder.encode("123456"));
        mentorUser.setRole(UserRole.MENTOR_BUDDY);

        MentorProfile mentorProfile = new MentorProfile();

        mentorProfile.setUser(mentorUser);
        mentorUser.setUserProfile(mentorProfile);

        userRepository.saveAll(List.of(mentorUser, devUser));
    }

    @Test
    @DisplayName("Deve atualizar o perfil DEV com sucesso quando autenticado e com dados válidos")
    @WithMockUser(username = "dev@test.com", roles = "DEV_BUDDY") //para o security essa config
    void updateDevProfile_deveAtualizar_quandoDadosSaoValidos() throws Exception {

        //cria o dto de parametro
        UpdateDevProfileDTO dto = new UpdateDevProfileDTO();
        dto.setProfileName("lp.dev25");
        dto.setHeadline("Especialista em Java e Spring");
        dto.setLearningGoals("Aprender sobre arquitetura de microserviços");
        dto.setCurrentSkillsLevel("Junior");
        dto.setProfileGithubUrl("https://github.com/devsenior");
        dto.setProfileStacks(Set.of("Java", "Spring Boot", "Docker"));

        //converter esse dto para JSON.
        String dtoJSON = objectMapper.writeValueAsString(dto);

        //agora o mockMvc faz as requisições
        mockMvc.perform(put("/api/profiles/dev/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJSON)) //dto que transformamos em JSON
                .andExpect(status().isNoContent());

        //buscar o usuario no banco para ver se realmente foi salvo.
        User devUser = userRepository.findUserByEmail("dev@test.com").orElseThrow();
        DevProfile updatedProfile = (DevProfile) devUser.getUserProfile();

        //agora ver se os dados batem
        assertThat(updatedProfile.getProfileName()).isEqualTo("lp.dev25");
        assertThat(updatedProfile.getHeadline()).isEqualTo("Especialista em Java e Spring");
        assertThat(updatedProfile.getLearningGoals()).isEqualTo("Aprender sobre arquitetura de microserviços");
        assertThat(updatedProfile.getProfileStacks()).containsExactlyInAnyOrder("Java", "Spring Boot", "Docker");
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando um MENTOR tenta atualizar qualquer perfil com dados inválidos")
    @WithMockUser(username = "mentor@test.com", roles = "MENTOR_BUDDY") // simulando um mentor conectado.
    void updateDevProfile_deveThrowsMinhaExcecao_quandoAgsInvalidos() throws Exception {

        UpdateDevProfileDTO dto = new UpdateDevProfileDTO(); //vazio para testar validações

        String dtoJSON = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/profiles/dev/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJSON))
                .andExpect(status().isBadRequest()); //deve lançar um erro 400
    }

    @Test
    @DisplayName("Deve retornar erro 422 quando um MENTOR tenta atualizar o perfil de DEV")
    @WithMockUser(username = "mentor@test.com", roles = "MENTOR_BUDDY") // simulando um mentor conectado.
    void updateDevProfile_deveThrowsMinhaExcecao_quandoPerfilForErrado() throws Exception {
        UpdateDevProfileDTO dto = new UpdateDevProfileDTO(); //apenas com os args essenciais para passar na validaçõa
        dto.setCurrentSkillsLevel("Junior");
        dto.setProfileName("lp.dev25");
        dto.setHeadline("Especialista em Java e Spring");

        String dtoJSON = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/profiles/dev/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJSON))
                .andExpect(status().isUnprocessableEntity()); //deve lançar um erro 422
    }

    @Test
    @DisplayName("Deve atualizar o perfil MENTOR com sucesso quando autenticado e com dados válidos")
    @WithMockUser(username = "mentor@test.com", roles = "MENTOR_BUDDY")
    void updateMentorProfile_deveAtualizar_quandoDadosSaoValidos() throws Exception {

        //cria o dto de parametro
        UpdateMentorProfileDTO dto = new UpdateMentorProfileDTO();
        dto.setProfileName("lp.dev25");
        dto.setHeadline("Especialista em Java e Spring");
        dto.setProfileGithubUrl("https://github.com/mentorsenior");
        dto.setProfileStacks(Set.of("Java", "Spring Boot", "Docker"));
        dto.setExperienceYears(22);
        dto.setProfessionalTitle("Java Sênior");

        //converter esse dto para JSON.
        String dtoJSON = objectMapper.writeValueAsString(dto);

        //agora o mockMvc faz as requisições
        mockMvc.perform(put("/api/profiles/mentor/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJSON)) //dto que transformamos em JSON
                .andExpect(status().isNoContent());

        //buscar o usuario no banco para ver se realmente foi salvo.
        User mentorUser = userRepository.findUserByEmail("mentor@test.com").orElseThrow();
        MentorProfile updatedProfile = (MentorProfile) mentorUser.getUserProfile();

        //agora ver se os dados batem
        assertThat(updatedProfile.getProfileName()).isEqualTo("lp.dev25");
        assertThat(updatedProfile.getHeadline()).isEqualTo("Especialista em Java e Spring");
        assertThat(updatedProfile.getExperienceYears()).isEqualTo(22);
        assertThat(updatedProfile.getProfileStacks()).containsExactlyInAnyOrder("Java", "Spring Boot", "Docker");
    }

    @Test
    @DisplayName("Deve retornar erro 422 quando um DEV tenta atualizar o perfil de MENTOR")
    @WithMockUser(username = "dev@test.com", roles = "DEV_BUDDY") // simulando um dev conectado agora.
    void updateMentorProfile_deveThrowsMinhaExcecao_quandoPerfilForErrado() throws Exception {

        UpdateMentorProfileDTO dto = new UpdateMentorProfileDTO();

        dto.setProfileName("lp.dev25");
        dto.setHeadline("Especialista em Java e Spring");
        dto.setExperienceYears(22);
        dto.setProfessionalTitle("SENIOR");

        String dtoJSON = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/profiles/mentor/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJSON))
                .andExpect(status().isUnprocessableEntity()); //deve lançar um erro 422
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando um MENTOR tenta atualizar seu perfil com dados inválidos")
    @WithMockUser(username = "mentor@test.com", roles = "MENTOR_BUDDY")
    void updateMentorProfile_deveLancarErro400_quandoDadosInvalidos() throws Exception {

        UpdateMentorProfileDTO invalidDto = new UpdateMentorProfileDTO();

        String dtoJSON = objectMapper.writeValueAsString(invalidDto);

        mockMvc.perform(put("/api/profiles/mentor/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJSON))
                .andExpect(status().isBadRequest());
    }

}
//testes basicos implementados.