package com.exercises.exercise01.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public abstract class SystemEnv {

    private static final Logger log = LogManager.getLogger(SystemEnv.class);

    private static final String ENV_TEMP_OUTPUT_DIR = "output.dir"; //  directory used to store files

    private static final String DEFAULT_TEMP_OUTPUT_DIR = FileUtils.getTempDirectory().getPath() + File.separator;

    public static String getTempOutputDir() {
        String outputDir = System.getProperty(ENV_TEMP_OUTPUT_DIR);
        if (StringUtils.isEmpty(outputDir)) {
            log.warn("No System property defined for '" + ENV_TEMP_OUTPUT_DIR + "'. Using defaults (" + DEFAULT_TEMP_OUTPUT_DIR + ")");
            return FilenameUtils.normalize(DEFAULT_TEMP_OUTPUT_DIR);
        } else {
            return FilenameUtils.normalize(outputDir + '/');
        }
    }
}
