package bai2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/** Demonstrates OS-independent path construction on all three runners. */
class PathCompatibilityTest {

  @Test
  void pathsArePortableAcrossOperatingSystems() throws IOException {
    Path temporaryDirectory = Files.createTempDirectory("ltnc-bank-");
    Path reportsDirectory = temporaryDirectory.resolve("reports");
    Path report = reportsDirectory.resolve("result.txt");
    try {
      Files.createDirectories(reportsDirectory);
      Files.writeString(report, "SUCCESS");
      assertTrue(Files.exists(report));
      assertEquals("SUCCESS", Files.readString(report));
    } finally {
      Files.deleteIfExists(report);
      Files.deleteIfExists(reportsDirectory);
      Files.deleteIfExists(temporaryDirectory);
    }
  }
}
