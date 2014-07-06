package shared;

public enum DataSource
{
    DEFAULT("default"),
    TEST("test");

    private final String name;

    private DataSource(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
