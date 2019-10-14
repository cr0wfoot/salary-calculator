package controller.persistence;

public abstract class LoaderFactory {

    private final static String CLASS_NAME = "controller.persistence.txt.TxtLoader";
    private static volatile LoaderFactory instance;

    public static LoaderFactory getInstance()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (instance == null) {
            synchronized (LoaderFactory.class) {
                if (instance == null) instance = (LoaderFactory) Class.forName(CLASS_NAME).newInstance();
            }
        }
        return instance;
    }

    public abstract void upload(String path) throws Exception;

    public abstract void download(String path) throws Exception;

}
