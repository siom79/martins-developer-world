package enumset;

public class FilePermissionBitmap {
    public static short OWNER_READ = 0b100000000;
    public static short OWNER_WRITE = 0b010000000;
    public static short OWNER_EXECUTE = 0b001000000;
    public static short GROUP_READ = 0b000100000;
    public static short GROUP_WRITE = 0b000010000;
    public static short GROUP_EXECUTE = 0b000001000;
    public static short OTHER_READ = 0b000000100;
    public static short OTHER_WRITE = 0b000000010;
    public static short OTHER_EXECUTE = 0b000000001;
    private short permissions = 0b0;

    public void setPermissions(short permission1) {
        permissions = (short) (permissions | permission1);
    }

    public void setPermissions(short permission1, short permission2) {
        permissions = (short) ((permissions | permission1) | (permissions | permission2));
    }

    public void setPermissions(short permission1, short permission2, short permission3) {
        permissions = (short) ((permissions | permission1) | (permissions | permission2) | (permissions | permission3));
    }

    public boolean hasOwnerPermission(short permission) {
        return (permissions & permission) != 0;
    }
}
