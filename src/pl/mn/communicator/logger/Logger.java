package pl.mn.communicator.logger;


/**
 * Klasa fabrykujaca logger.
 *
 * @author mnaglik
 */
public abstract class Logger implements ILogger {
    private static Logger instance;

    protected Logger() {
    }

    /**
     * Zwr�c instancje loggera.
     * @param clazz klasa kt�ra loguje
     * @return instancja loggera
     */
    public static synchronized Logger getLogger(Class clazz) {
        if (instance == null) {
            instance = Logger4J.getLogger(clazz);
        }

        return instance;
    }
}
