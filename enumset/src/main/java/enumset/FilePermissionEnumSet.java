package enumset;

import java.util.EnumSet;

public class FilePermissionEnumSet {
    private EnumSet<FilePermission> ownerPermissions;
    private EnumSet<FilePermission> groupPermissions;
    private EnumSet<FilePermission> otherPermissions;

    public void setOwnerPermissions(FilePermission filePermission) {
        ownerPermissions = EnumSet.of(filePermission);
    }

    public void setOwnerPermissions(FilePermission filePermission1, FilePermission filePermission2) {
        ownerPermissions = EnumSet.of(filePermission1, filePermission2);
    }

    public void setOwnerPermissions(FilePermission filePermission1, FilePermission filePermission2, FilePermission filePermission3) {
        ownerPermissions = EnumSet.of(filePermission1, filePermission2, filePermission3);
    }

    public boolean hasOwnerPermission(FilePermission filePermission) {
        return ownerPermissions.contains(filePermission);
    }

    public void setGroupPermissions(FilePermission filePermission) {
        groupPermissions = EnumSet.of(filePermission);
    }

    public void setGroupPermissions(FilePermission filePermission1, FilePermission filePermission2) {
        groupPermissions = EnumSet.of(filePermission1, filePermission2);
    }

    public void setGroupPermissions(FilePermission filePermission1, FilePermission filePermission2, FilePermission filePermission3) {
        groupPermissions = EnumSet.of(filePermission1, filePermission2, filePermission3);
    }

    public boolean hasGroupPermission(FilePermission filePermission) {
        return groupPermissions.contains(filePermission);
    }

    public void setOtherPermissions(FilePermission filePermission) {
        otherPermissions = EnumSet.of(filePermission);
    }

    public void setOtherPermissions(FilePermission filePermission1, FilePermission filePermission2) {
        otherPermissions = EnumSet.of(filePermission1, filePermission2);
    }

    public void setOtherPermissions(FilePermission filePermission1, FilePermission filePermission2, FilePermission filePermission3) {
        otherPermissions = EnumSet.of(filePermission1, filePermission2, filePermission3);
    }

    public boolean hasOtherPermission(FilePermission filePermission) {
        return otherPermissions.contains(filePermission);
    }
}
