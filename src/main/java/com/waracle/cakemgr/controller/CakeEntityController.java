package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.JsonUtils;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.repository.CakeEntityRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
// @CrossOrigin(origins = "http://localhost:8081")
@RestController
// @RequestMapping("/api")
public class CakeEntityController {


  @Autowired
  CakeEntityRepository cakeEntityRepository;

  @Setter
  private int serverPort = 8282;

  // @Value("${application.download.url}")
  private String inputURL =
    "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

  @GetMapping("/cakes")
  public ResponseEntity<List<CakeEntity>> getAllcakes(
    @RequestParam(required = false) String title
  ) {
    log.info("GET /cakes");
    try {
      List<CakeEntity> cakes = new ArrayList<CakeEntity>();

      if (title == null) cakeEntityRepository
        .findAll()
        .forEach(cakes::add); else cakeEntityRepository
        .findByTitleContaining(title)
        .forEach(cakes::add);

      if (cakes.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(cakes, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/cakes/{id}")
  public ResponseEntity<CakeEntity> getcakeById(@PathVariable long id) {
    log.info("GET /cakes/" + id);
    Optional<CakeEntity> cakeData = cakeEntityRepository.findById(id);

    if (cakeData.isPresent()) {
      return new ResponseEntity<>(cakeData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/cakes")
  public ResponseEntity<CakeEntity> createcake(@RequestBody CakeEntity cake) {
    log.info("POST /cakes");
    try {
      CakeEntity _cake = cakeEntityRepository.saveAndFlush(
        new CakeEntity(cake.getTitle(), cake.getDescription(), " isfalse")
      );
      return new ResponseEntity<>(_cake, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/cakes/{id}")
  public ResponseEntity<CakeEntity> updatecake(
    @PathVariable long id,
    @RequestBody CakeEntity cake
  ) {
    log.info("PUT /cakes/" + id);
    Optional<CakeEntity> cakeData = cakeEntityRepository.findById(id);

    if (cakeData.isPresent()) {
      CakeEntity _cake = cakeData.get();
      _cake.setTitle(cake.getTitle());
      _cake.setDescription(cake.getDescription());
      _cake.setImage(cake.getImage());
      return new ResponseEntity<>(
        cakeEntityRepository.saveAndFlush(_cake),
        HttpStatus.OK
      );
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/cakes/{id}")
  public ResponseEntity<HttpStatus> deletecake(@PathVariable long id) {
    log.info("DELETE /cakes/" + id);
    try {
      cakeEntityRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/cakes")
  public ResponseEntity<HttpStatus> deleteAllcakes() {
    log.info("DELETE /cakes");
    try {
      cakeEntityRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/download")
  public ResponseEntity<HttpStatus> download() {
    final String infoText = "GET /download ";
    log.info(infoText);
    ResponseEntity<String> response ;
    try {
      RestTemplate restTemplete = new RestTemplate();
      response = restTemplete.getForEntity(
        inputURL,
        String.class
      );
    } catch (Exception e) {
      log.debug(infoText + e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Save each entity individually so , when importing a list of items, those that do not provoke an integrity
    // violation will be saved to the DB
    try {
      var cakes = JsonUtils.jsonArrayToCakeObjects(response.getBody());
      log.debug(infoText + "Cakes to be added to DB -" + cakes.size());
      var cakeEntitys = new ArrayList<CakeEntity>();
      cakes.forEach(x -> cakeEntitys.add(new CakeEntity(x)));
      var iter = 0;
      for (CakeEntity cake : cakeEntitys) {
        try {
      cakeEntityRepository.save(cake);
        } catch (DataIntegrityViolationException ex) {
          log.debug(infoText + "Unable to save record " + (iter +1) + " " + ex.getMessage());
        }
        iter++;
      }
      log.debug(infoText + iter + " Downloaded items added to DB");
      if (iter == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      log.debug(infoText + e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/public/greetings")
  public ResponseEntity<String> getPublicGreetings() {
      return ResponseEntity.ok("Greetings from a public endpoint!");
  }

  @GetMapping("/cakes/greetings")
  public ResponseEntity<String> getProtectedGreetings() {
      return ResponseEntity.ok("Greetings from a protected endpoint!");
  }  
}
