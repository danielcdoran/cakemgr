package com.waracle.cakemgr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.waracle.cakemgr.model.*;
import com.waracle.cakemgr.repository.CakeEntityRepository;
import jakarta.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class CakeEntityRepositoryTests {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private CakeEntityRepository cakeEntityRepository;

  CakeEntity cake1, cake2;

  @BeforeEach
  void init() {
    cake1 = new CakeEntity("anytitle1", "random description1", "imagefor URL1");
    cake2 = new CakeEntity("anytitle2", "random description2", "imagefor URL2");
  }

  @Test
  void checkObjectMapperExists() {
    assertNotNull(dataSource);
    assertNotNull(entityManager);
    assertNotNull(cakeEntityRepository);
  }

  @Test
  public void testEmptyDatabase() {
    assertEquals(0, cakeEntityRepository.findAll().size());

    assertNotNull(dataSource);
    assertNotNull(entityManager);
  }

  @Test
  void givenCake_whenSaved_thenCanGetFromDB() {
    var cake = new CakeEntity("anytitle", "random description", "imagefor URL");
    var savedCake = cakeEntityRepository.saveAndFlush(cake);
    assertEquals(entityManager.find(CakeEntity.class, savedCake.getId()), cake);
  }

  @Test
  void givenCakes_whenSaved_thenReturnsAsCakeEntityListFromDB() {
    var cake1 = new CakeEntity(
      "anytitle1",
      "random description1",
      "imagefor URL1"
    );
    var cake2 = new CakeEntity(
      "anytitle2",
      "random description2",
      "imagefor URL2"
    );

    var savedCake = cakeEntityRepository.saveAndFlush(cake1);
    var savedCake2 = cakeEntityRepository.saveAndFlush(cake2);
    List<CakeEntity> cakes = cakeEntityRepository.findAll();
    assertEquals(cakes.size(), 2);
  }

  @Test
  void givenCakeObject_whenSaved_thenExistsInDB() {
    var cake = new CakeEntity(
      "anytitle1",
      "random description1",
      "imagefor URL1"
    );

    var expected = new CakeEntity(
      "anytitle1",
      "random description1",
      "imagefor URL1"
    );
    var actual = cakeEntityRepository.saveAndFlush(cake);
    var returned = entityManager.find(CakeEntity.class, actual.getId());
    assertTrue(returned.equalContent(expected));
  }

  @Test
  void givenCakeObjects_whenSaved_thenExistInDB() throws IOException {
    File resource = new ClassPathResource("cakes.json").getFile();
    String cakeString = new String(Files.readAllBytes(resource.toPath()));
    assertTrue(cakeString.length() > 1);
    var cakes = JsonUtils.jsonArrayToCakeObjects(cakeString);
    assertTrue(cakes.size() == 5);
    var cakeEntitys = new ArrayList<CakeEntity>();
    cakes.forEach(x -> cakeEntitys.add(new CakeEntity(x)));
    cakeEntityRepository.saveAll(cakeEntitys);
    List<CakeEntity> actualCakesinDB = cakeEntityRepository.findAll();
    assertEquals(actualCakesinDB.size(), 5);
  }

  @Test
  void givenCakeEntity_whenSaved_thenCanRetrieveFromDB() {
    var savedCake = cakeEntityRepository.saveAndFlush(cake1);
    assertEquals(
      entityManager.find(CakeEntity.class, savedCake.getId()),
      cake1
    );
  }

  @Test
  void givenCakeEntitys_whenSaved_thenReturnsListFromDB() {
    cakeEntityRepository.saveAndFlush(cake1);
    cakeEntityRepository.saveAndFlush(cake2);
    List<CakeEntity> cakes = cakeEntityRepository.findAll();
    assertEquals(cakes.size(), 2);
  }

  @Test
  void given2CakeObjectWithSameTitle_whenSaved_thenErrorAndNotSaved() {
    cakeEntityRepository.saveAndFlush(cake1);
    cake2.setTitle(cake1.getTitle());
    assertThrows(
      DataIntegrityViolationException.class,
      () -> {
        cakeEntityRepository.saveAndFlush(cake2);
      }
    );
  }

  @Test
  void givenTitleGreaterThan100_whenSaved_thenErrorAndNotSaved() {
    cake1.setTitle("x".repeat(101));
    assertThrows(
      DataIntegrityViolationException.class,
      () -> {
        cakeEntityRepository.saveAndFlush(cake1);
      }
    );
  }

  @Test
  void givenDescriptionGreaterThan300_whenSaved_thenErrorAndNotSaved() {
    cake1.setDescription("x".repeat(301));
    assertThrows(
      DataIntegrityViolationException.class,
      () -> {
        cakeEntityRepository.saveAndFlush(cake1);
      }
    );
  }

  @Test
  void givenImageGreaterThan300_whenSaved_thenErrorAndNotSaved() {
    cake1.setImage("x".repeat(301));
    assertThrows(
      DataIntegrityViolationException.class,
      () -> {
        cakeEntityRepository.saveAndFlush(cake1);
      }
    );
  }

  @Test
  void givenCakeEntity_whenDelete_thenNoInDB() {
    var savedcake1 = cakeEntityRepository.saveAndFlush(cake1);
    cakeEntityRepository.saveAndFlush(cake2);
    assertEquals(cakeEntityRepository.findAll().size(), 2);
    cakeEntityRepository.deleteById(savedcake1.getId());
    var actual = entityManager.find(CakeEntity.class, savedcake1.getId());
    assertNull(actual);
  }

  @Test
  void givenNoExistentId_whenDeleteById_thenNoDBChange() {
    cakeEntityRepository.saveAndFlush(cake1);
    cakeEntityRepository.saveAndFlush(cake2);
    assertEquals(cakeEntityRepository.findAll().size(), 2);
    cakeEntityRepository.deleteById(111111L);
    assertEquals(cakeEntityRepository.findAll().size(), 2);
  }

  @Test
  void givenCakeEntityInDB_whenUpdated_thenChangeDB() {
    var savedCake = cakeEntityRepository.saveAndFlush(cake1);
    CakeEntity actual = cakeEntityRepository.findById(savedCake.getId()).get();
    assertEquals(actual,savedCake);

    // Modiy entity, save it, recall it from DB and then check it
    actual.setTitle("very long title to show it is changed");
    var newSaved  = cakeEntityRepository.saveAndFlush(actual);
    CakeEntity actualnewSaved = cakeEntityRepository.findById(newSaved.getId()).get(); 
    assertEquals(actualnewSaved,newSaved);
  }

  @Test
  void givenCakeEntityInDB_when2TitlesAreSame_thenUpdateFails() {
    DataIntegrityViolationException thrown = assertThrows(
      DataIntegrityViolationException.class,
      () -> {
        cakeEntityRepository.saveAndFlush(cake1);
        var savedCake2 = cakeEntityRepository.saveAndFlush(cake2);
        assertEquals(cakeEntityRepository.findAll().size(), 2);
        savedCake2.setTitle(cake1.getTitle());
        cakeEntityRepository.saveAndFlush(savedCake2);
      },
      "Unique constraints violated"
    );
    assertEquals(thrown.getClass(), DataIntegrityViolationException.class);
  }
}
