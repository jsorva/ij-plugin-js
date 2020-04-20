package fi.aalto.cs.apluscourses.intellij.model;

import com.intellij.testFramework.HeavyPlatformTestCase;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.Test;

public class ScalaSdkTest extends HeavyPlatformTestCase {

  @Test
  public void testCreateTempFile() throws IOException {
    //  given
    APlusProject aplusProject = new APlusProject(getProject());
    ScalaSdk scalaSdk = new ScalaSdk("scala-sdk-5.5.5", aplusProject, 1);

    //  when
    File tempFile = scalaSdk.createTempFile();
    Path absolutePath = tempFile.toPath();

    //  then
    assertTrue("Created file path part is correct.", absolutePath.endsWith("scala-5.5.5.zip"));
  }

  @Test
  public void testFileName() {
    //  given
    APlusProject aplusProject = new APlusProject(getProject());
    ScalaSdk scalaSdk = new ScalaSdk("scala-sdk-2.12.10", aplusProject, 1);

    //  when
    String fileName = scalaSdk.getFileName();

    //  then
    assertEquals("The correct file name is returned.", "scala-2.12.10", fileName);
  }

  @Test
  public void testGetUrisNoArguments() {
    //  given
    APlusProject aplusProject = new APlusProject(getProject());
    ScalaSdk scalaSdk = new ScalaSdk("scala-sdk-2.12.10", aplusProject, 1);

    //  when
    String[] uris = scalaSdk.getUris();

    // then
    assertEquals("Two elements are present", 2, uris.length);
    assertTrue("Contains first required .jar",
        uris[0].contains("/lib/scala-sdk-2.12.10/scala-library.jar"));
    assertTrue("Contains second required .jar",
        uris[1].contains("/lib/scala-sdk-2.12.10/scala-reflect.jar"));
  }

  @Test
  public void testGetUrisWithValidArguments() {
    //  given
    APlusProject aplusProject = new APlusProject(getProject());
    ScalaSdk scalaSdk = new ScalaSdk("scala-sdk-2.12.10", aplusProject, 1);
    final String[] allClasses = {
        "scala-compiler.jar",
        "scala-library.jar",
        "scala-reflect.jar"
    };

    //  when
    String[] uris = scalaSdk.getUris(allClasses);

    // then
    assertEquals("Two elements are present", 3, uris.length);
    assertTrue("Contains first required .jar",
        uris[0].contains("/lib/scala-sdk-2.12.10/scala-compiler.jar"));
    assertTrue("Contains second required .jar",
        uris[1].contains("/lib/scala-sdk-2.12.10/scala-library.jar"));
    assertTrue("Contains third required .jar",
        uris[2].contains("/lib/scala-sdk-2.12.10/scala-reflect.jar"));
  }

  @Test
  public void testGetUrisWithInvalidArguments() {
    //  given
    APlusProject aplusProject = new APlusProject(getProject());
    ScalaSdk scalaSdk = new ScalaSdk("scala-sdk-2.12.10", aplusProject, 1);
    final String[] allClasses = {
        "scala-compiler.jar",
        "",
        "scala-reflect.jar"
    };

    //  when
    String[] uris = scalaSdk.getUris(allClasses);

    // then
    assertEquals("Two elements are present", 2, uris.length);
    assertTrue("Contains first required .jar",
        uris[0].contains("/lib/scala-sdk-2.12.10/scala-compiler.jar"));
    assertTrue("Contains second required .jar",
        uris[1].contains("/lib/scala-sdk-2.12.10/scala-reflect.jar"));
  }
}
