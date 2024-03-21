package com.waracle.cakemgr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.waracle.cakemgr.model.Cake;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@JsonTest
public class jsonTests {


  private JSONObject jo;
  private JSONArray jsonArray;

  @BeforeEach
  void setup() throws JSONException {
    jo = new JSONObject();
    jo.put("title", "title1"); // title string is zero length
    jo.put("desc", "desc1");
    jo.put("image", "image1");

    jsonArray = new JSONArray();
    jsonArray.put(jo); // now a json array
  }

  @Test
  public void whenJsonInFile_thenChangeToCakeObjectsIsSuccessful()
    throws IOException {
    File resource = new ClassPathResource("cakes.json").getFile();
    String cakeString = new String(Files.readAllBytes(resource.toPath()));
    assertTrue(cakeString.length() > 1);
    var cakes = JsonUtils.jsonArrayToCakeObjects(cakeString);
    assertEquals(cakes.size(),5);
  }

  @Test
  public void whenJsonObjectsDoNotHaveUniqueTitle_thenFilter()
    throws IOException {
    File resource = new ClassPathResource("cakes-formatted.json").getFile();
    String cakeString = new String(Files.readAllBytes(resource.toPath()));
    assertTrue(cakeString.length() > 1);
    var cakes = JsonUtils.jsonArrayToCakeObjects(cakeString);
    assertEquals(cakes.size(),5);
  } 
  
  @Test
  public void whenSingleObjectInJsonArray_thenChangeToObjectsSuccessful()
    throws IOException, JSONException {
    var cakes = JsonUtils.jsonArrayToCakeObjects(jsonArray.toString());
    assertTrue(cakes.size() == 1);
    var expected = new Cake("title1", "desc1", "image1");
    assertEquals(expected, cakes.get(0));
  }

  @Test
  public void whenTitleLengthInJsonGTMax_thenNoCakeCreated()
    throws IOException, JSONException {
    jo.put("title", "a".repeat(101)); // title string is zero length
    jsonArray.put(jo); // now a json array

    var cakes = JsonUtils.jsonArrayToCakeObjects(jsonArray.toString());
    assertEquals(cakes.size(), 0);
  }

  @Test
  public void whenZeroLengthTitleInJson_thenNoCakeCreated()
    throws IOException, JSONException {
    jo.put("title", ""); // title string is zero length
    jsonArray.put(jo); // now a json array

    var cakes = JsonUtils.jsonArrayToCakeObjects(jsonArray.toString());
    assertEquals(cakes.size(), 0);
  }  

  @Test
  public void whenCakeTitle101Chars_thenConstructorFails() 
    throws IOException, JSONException {
      jo.put("title", "a".repeat(101)); // title string is zero length
      jsonArray.put(jo); // now a json array
  
      var cakes = JsonUtils.jsonArrayToCakeObjects(jsonArray.toString());
      assertEquals(cakes.size(), 0);
    }  
  

  @Test
  public void whenCakeTitleNull_thenConstructorFails() {
    Exception thrown = assertThrows(
      Exception.class,
      () -> {
        new Cake(null, "desc", "image");
      },
      "Title too long"
    );
    assertEquals(thrown.getClass(), IllegalArgumentException.class);
  }

  @Test
  public void whenCakeTitleZeroLength_thenConstructorFails() {
    Exception thrown = assertThrows(
      Exception.class,
      () -> {
        new Cake("", "desc", "image");
      },
      "Title too long"
    );
    assertEquals(thrown.getClass(), IllegalArgumentException.class);
  }

  
  
  
  
  @Test
  public void whenZeroLengthDescInJson_thenNoCakeCreated()
    throws IOException, JSONException {
    jo.put("desc", ""); // title string is zero length
    jsonArray.put(jo); // now a json array

    var cakes = JsonUtils.jsonArrayToCakeObjects(jsonArray.toString());
    assertEquals(cakes.size(), 0);
  }  

  @Test
  public void whenCakeDesc101Chars_thenConstructorFails() 
    throws IOException, JSONException {
      jo.put("desc", "a".repeat(101)); // title string is zero length
      jsonArray.put(jo); // now a json array
  
      var cakes = JsonUtils.jsonArrayToCakeObjects(jsonArray.toString());
      assertEquals(cakes.size(), 0);
    }  
  

  @Test
  public void whenCakeDescNull_thenConstructorFails() {
    Exception thrown = assertThrows(
      Exception.class,
      () -> {
        new Cake("title1", null, "image");
      }
    );
    assertEquals(thrown.getClass(), IllegalArgumentException.class);
  }


  
  
  
  
  
  
  @Test
  public void whenZeroLengthImageInJson_thenNoCakeCreated()
    throws IOException, JSONException {
    jo.put("image", ""); // title string is zero length
    jsonArray.put(jo); // now a json array

    var cakes = JsonUtils.jsonArrayToCakeObjects(jsonArray.toString());
    assertEquals(cakes.size(), 0);
  }  

  @Test
  public void whenCakeImage301Chars_thenConstructorFails() 
    throws IOException, JSONException {
      jo.put("image", "a".repeat(301)); // title string is zero length
      jsonArray.put(jo); // now a json array
  
      var cakes = JsonUtils.jsonArrayToCakeObjects(jsonArray.toString());
      assertEquals(cakes.size(), 0);
    }  
  

  @Test
  public void whenCakeImageNull_thenConstructorFails() {
    Exception thrown = assertThrows(
      Exception.class,
      () -> {
        new Cake("title1", "desc1", null);
      }
    );
    assertEquals(thrown.getClass(), IllegalArgumentException.class);
  }
  
  
  
  
  
  
  // This is what happens when zero length string returned
  @Test
  public void whenZeroLengthStringFromDownload_thenException() {
    Throwable exception = assertThrows(
      MismatchedInputException.class,
      () -> {
        var cakes = JsonUtils.jsonArrayToCakeObjects("");
      }
    );
    assertEquals(exception.getClass(), MismatchedInputException.class);
  }

  // This is what happens when whitespace
  @Test
  public void whenWhiteSpaceOnlyFromDownload_thenException() {
    Throwable exception = assertThrows(
      MismatchedInputException.class,
      () -> {
        var cakes = JsonUtils.jsonArrayToCakeObjects("  ");
      }
    );
    assertEquals(exception.getClass(), MismatchedInputException.class);
  }

  @Test
  public void whenEnclosingSquareBrackets_thenZeroLengthListOfCakes()
    throws IOException {
      var cakes = JsonUtils.jsonArrayToCakeObjects("[]");
    assertTrue(cakes.size() == 0);
  }

  @Test
  public void whenObjectNotACake_thenConstructionFails()
    throws IOException, JSONException {
    var jsonObject = new JSONObject();
    //    jsonObject.put("title","title");
    jsonObject.put("desc", "desc");
    jsonObject.put("image", "imag");
    var jsonArray2 = new JSONArray();
    jsonArray2.put(jsonObject);

    var cakes = JsonUtils.jsonArrayToCakeObjects(jsonArray2.toString());
    assertTrue(cakes.size() == 0);
  }

}
