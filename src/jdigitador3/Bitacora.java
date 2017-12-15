/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdigitador3;

import java.io.*;
import java.util.logging.*;


/**
 *
 * @author LUIS
 */
class Bitacora {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    Logger logger = Logger.getLogger("MyLog");
    void escribir(String s) throws IOException {
        
        FileHandler fh;
        String direc;

        try {

            // This block configure the logger with handler and formatter
            direc = ".\\bitacora\\bitacora.log";
            fh = new FileHandler(direc, true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);

            SimpleFormatter formatter = new SimpleFormatter();
            //  fh.setFormatter(formatter);
            //SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord rec) {
                    StringBuilder buf = new StringBuilder(1000);
                    buf.append(new java.util.Date());
                    buf.append(" ");
                    buf.append(rec.getLevel());
                    buf.append(" ");
                    buf.append(formatMessage(rec));
                    buf.append(LINE_SEPARATOR);

                    return buf.toString();
                }
            });

            // the following statement is used to log any messages   
            logger.log(Level.ALL, s);

        } catch (SecurityException e) {
        }

    }

    public static String relative(final File base, final File file) {
        final int rootLength = base.getAbsolutePath().length();
        final String absFileName = file.getAbsolutePath();
        final String relFileName = absFileName.substring(rootLength + 1);
        return relFileName;
    }
}
