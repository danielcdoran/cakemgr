package com.waracle.cakemgr;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.repository.CakeEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(CakeEntityController.class)
public class CakeEntityControllerTests {

  @MockBean
  private CakeEntityRepository CakeEntityRepository;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Value("${application.security.api-key}")
  private String apiKey;

  @Test
  void shouldCreatecake() throws Exception {
    CakeEntity cake = new CakeEntity(1L, "Spring Boot @WebMvcTest", "Description", "really ");

    mockMvc.perform(post("/cakes").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(cake))
        .header("ApiKey", apiKey))
        .andExpect(status().isCreated())
        .andDo(print());
  }

  @Test
  void shouldReturncake() throws Exception {
    long id = 1L;
    CakeEntity cake = new CakeEntity(id, "Spring Boot @WebMvcTest", "Description", "fftrue");

    when(CakeEntityRepository.findById(id)).thenReturn(Optional.of(cake));
    mockMvc.perform(get("/cakes/{id}", id)
    .header("APIKEY", "waracle"))
    .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.title").value(cake.getTitle()))
        .andExpect(jsonPath("$.description").value(cake.getDescription()))
        .andExpect(jsonPath("$.image").value(cake.getImage()))
        .andDo(print());
  }

  @Test
  void shouldReturnNotFoundcake() throws Exception {
    long id = 1L;

    when(CakeEntityRepository.findById(id)).thenReturn(Optional.empty());
    mockMvc.perform(get("/cakes/{id}", id)
    .header("APIKEY", "waracle"))
         .andExpect(status().isNotFound())
         .andDo(print());
  }

  @Test
  void shouldReturnListOfcakes() throws Exception {
    List<CakeEntity> cakes = new ArrayList<>(
        Arrays.asList(new CakeEntity(1L, "Spring Boot @WebMvcTest 1", "Description 1", "really true"),
            new CakeEntity(2, "Spring Boot @WebMvcTest 2", "Description 2", "really true"),
            new CakeEntity(3, "Spring Boot @WebMvcTest 3", "Description 3", "really true")));

    when(CakeEntityRepository.findAll()).thenReturn(cakes);
    mockMvc.perform(get("/cakes")
    .header("APIKEY", "waracle"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(cakes.size()))
        .andDo(print());
  }

  @Test
  void shouldReturnListOfcakesWithFilter() throws Exception {
    List<CakeEntity> cakes = new ArrayList<>(
        Arrays.asList(new CakeEntity(1L, "Spring Boot @WebMvcTest", "Description 1", "really true"),
            new CakeEntity(3, "Spring Boot Web MVC", "Description 3", "really true")));

    String title = "Boot";
    MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
    paramsMap.add("title", title);

    when(CakeEntityRepository.findByTitleContaining(title)).thenReturn(cakes);
    mockMvc.perform(get("/cakes").params(paramsMap)
    .header("APIKEY", "waracle"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(cakes.size()))
        .andDo(print());
  }
  
  @Test
  void shouldReturnNoContentWhenFilter() throws Exception {
    String title = "BezKoder";
    MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
    paramsMap.add("title", title);
    
    List<CakeEntity> cakes = Collections.emptyList();

    when(CakeEntityRepository.findByTitleContaining(title)).thenReturn(cakes);
    mockMvc.perform(get("/cakes").params(paramsMap)
    .header("APIKEY", "waracle"))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  @Test
  void shouldUpdatecake() throws Exception {
    long id = 1L;

    CakeEntity cake = new CakeEntity(id, "Spring Boot @WebMvcTest", "Description", "hhfalse");
    CakeEntity updatedcake = new CakeEntity(id, "Updated", "Updated", "really true");

    when(CakeEntityRepository.findById(id)).thenReturn(Optional.of(cake));
    when(CakeEntityRepository.saveAndFlush(any(CakeEntity.class))).thenReturn(updatedcake);

    mockMvc.perform(put("/cakes/{id}", id).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedcake))
        .header("APIKEY", "waracle"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value(updatedcake.getTitle()))
        .andExpect(jsonPath("$.description").value(updatedcake.getDescription()))
        .andExpect(jsonPath("$.image").value(updatedcake.getImage()))
        .andDo(print());
  }
  
  @Test
  void shouldReturnNotFoundUpdatecake() throws Exception {
    long id = 1L;

    CakeEntity updatedcake = new CakeEntity(id, "Updated", "Updated", "really true");

    when(CakeEntityRepository.findById(id)).thenReturn(Optional.empty());
    when(CakeEntityRepository.saveAndFlush(any(CakeEntity.class))).thenReturn(updatedcake);

    mockMvc.perform(put("/cakes/{id}", id).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedcake))
        .header("APIKEY", "waracle"))
        .andExpect(status().isNotFound())
        .andDo(print());
  }
  
  @Test
  void shouldDeletecake() throws Exception {
    long id = 1L;

    doNothing().when(CakeEntityRepository).deleteById(id);
    mockMvc.perform(delete("/cakes/{id}", id)
    .header("APIKEY", "waracle"))
         .andExpect(status().isNoContent())
         .andDo(print());
  }
  
  @Test
  void shouldDeleteAllcakes() throws Exception {
    doNothing().when(CakeEntityRepository).deleteAll();
    mockMvc.perform(delete("/cakes")
    .header("APIKEY", "waracle"))
         .andExpect(status().isNoContent())
         .andDo(print());
  }

  @Test
  void givenWrongAPIKeyValue_whenListOfcakes_thenUnauthorised() throws Exception {
    List<CakeEntity> cakes = new ArrayList<>(
        Arrays.asList(new CakeEntity(1L, "Spring Boot @WebMvcTest 1", "Description 1", "really true"),
            new CakeEntity(2, "Spring Boot @WebMvcTest 2", "Description 2", "really true"),
            new CakeEntity(3, "Spring Boot @WebMvcTest 3", "Description 3", "really true")));

    when(CakeEntityRepository.findAll()).thenReturn(cakes);
    mockMvc.perform(get("/cakes")
    .header("APIKEY", "wrongAPIKeyvalue"))
        .andExpect(status().isUnauthorized())
        .andDo(print());
  }
}
