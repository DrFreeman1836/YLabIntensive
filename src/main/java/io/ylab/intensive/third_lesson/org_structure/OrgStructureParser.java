package io.ylab.intensive.third_lesson.org_structure;

import java.io.File;
import java.io.IOException;

public interface OrgStructureParser {
  Employee parseStructure(File csvFile) throws IOException;

}
