package me.ODINN.MCCustomCreation.MultiVersion;

public class VersionRange {

    public static final String PLUGIN_VERSION = "1.19";

    private String fromVersion;
    private String toVersion;

    public VersionRange(String fromVersion, String toVersion) {
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
    }

    public VersionRange(String fromVersion) {
        this(fromVersion,PLUGIN_VERSION);
    }
}
