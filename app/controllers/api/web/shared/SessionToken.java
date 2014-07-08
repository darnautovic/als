package controllers.api.web.shared;

public enum SessionToken
{
    ALS("session-token");

    private final String name;

    private SessionToken(String name)
    {
        this.name = name;
    }

    public String getTokenName()
    {
        return name;
    }
}
