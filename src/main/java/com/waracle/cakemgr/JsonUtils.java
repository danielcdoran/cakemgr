package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.Cake;
import java.io.ByteArrayInputStream;
import java.io.File;
// import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
// import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.core.io.ClassPathResource;
import java.util.stream.Collectors;

public class JsonUtils {

  public static <T> Predicate<T> distinctByKey(
    Function<? super T, ?> keyExtractor
  ) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  public static List<Cake> jsonArrayToCakeObjects(String jsonInput)
    throws IOException {
    var objectMapper = new ObjectMapper();
    TypeReference<List<Cake>> typeReference = new TypeReference<List<Cake>>() {};
    InputStream inputStream = new ByteArrayInputStream(jsonInput.getBytes());
    var cakes = objectMapper.readValue(inputStream, typeReference);

    // The cakes may not be valid so need to remove non-valid items from list
    List<Cake> result = cakes
      .stream()
      .filter(x -> x.isValid())
      .collect(Collectors.toList());
// Now remove non-unique cakes  - cannot have 2 cakes with same title
    List<Cake> unique = result
      .stream()
      .filter(distinctByKey(p -> p.getTitle()))
      .collect(Collectors.toList());

    return unique;
  }
}
