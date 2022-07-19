package com.project.model;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class Adaptor {
     public abstract Map<String, List<Reading>> processFile(final File file);

}
