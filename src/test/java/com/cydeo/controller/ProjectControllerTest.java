package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    static String token;

    static UserDTO manager;   //beforeAll method static if we use static method field can be must static
    static ProjectDTO project;

    @BeforeAll  // i am create sample data projectDto object
    static void setUp(){
        token="Bearer "+"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJZeU8xRi14YmJrVGlibloxSnNlWFY0TnVyOWZvVVFCRzY0T2pIOEVBQmFzIn0.eyJleHAiOjE2ODc0MTc5MDAsImlhdCI6MTY4NzM5OTkwMCwianRpIjoiZTFhYzk5YjctMDk0Ny00MjE0LTg2YjAtNDBlMjQ4MTY3YjQ1IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI2MDc2MTAzMi0wY2IwLTRkODctOTY5ZC03OGMwOGMxOTM0ZGIiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjZjNjQ2ZmJhLWFmMzMtNDIzYS1hZmZmLTIwNWMzMTgxMGU0MSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtY3lkZW8tZGV2IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIk1hbmFnZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwiLCJzaWQiOiI2YzY0NmZiYS1hZjMzLTQyM2EtYWZmZi0yMDVjMzE4MTBlNDEiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicHJlZmVycmVkX3VzZXJuYW1lIjoib3p6eSJ9.GJnlP8DUsZvsT5PBXVU_6SBgxfWFoPNSS7rIfisrFgbY0QtucVeUoacGTJi-Cj1MB8rfuh1oXH6qGPCEkqaLGNQpp3k1z5aOQtRcKUxgnWlPXlBAs0J-4r3xj24qM3dWkwwxcVwohqmOQa3FhYGkaU_tWCOIo5Wf3V47lVjBmCkrQIrGgCSH_p-GDf_nJ2cx-4c3Sh30XX3eT3-g36OIaeoh7jceEIRxxCZ-cmreTxWklaD_7mpri2gRmySgz6QFNRHaVJSv93vyUvmYE3tlU9ppafifOjd-YKBQ4wga-pIFzBL76nkEpfgEPWjhq2ridSPxSeL6iDA4zmVITLNicg";
        manager=new UserDTO(2L,
                "",
                "",
                "ozzy",
                "abc1",
                "",
                true,
                "",
                new RoleDTO(2L,"Manager"),
                Gender.MALE);
        project = new ProjectDTO(
                "API Project",
                "PE001",
                manager,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Some details",
                Status.OPEN

        );

    }
    @Test
    void givenNoToken_getProjects() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project"))
                .andExpect(status().is4xxClientError());


    }
    @Test
    void givenToken_getProjects() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project")
                .header("Authorization",token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].projectCode").exists())//give first element inside first element//first project
                .andExpect(jsonPath("$.data[0].assignedManager.userName").exists()) //username in here or not
                .andExpect(jsonPath("$.data[0].assignedManager.userName").isNotEmpty())//username empty or not
                .andExpect(jsonPath("$.data[0].assignedManager.userName").isString())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").value("ozzy"));

    }
    @Test
    void givenToken_createProject() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/project")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJsonString(project)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Project is successfully created"));

    }
    @Test
    void givenToken_updateProject() throws Exception {
        project.setProjectName("API Project-2");

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/project")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJsonString(project)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Project is successfully updated"));
    }

    @Test
    void givenToken_deleteProject() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/project/"+ project.getProjectCode())
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Project is successfully deleted"));

    }
    private String toJsonString(final Object obj) throws JsonProcessingException {//instead of hard code
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);//false only use date not we dont need time
        objectMapper.registerModule(new JavaTimeModule()); // convert to 2022,12,18  -> 2022/12/18
        return objectMapper.writeValueAsString(obj);       //{"projectCode"::Code",..}
    }

}