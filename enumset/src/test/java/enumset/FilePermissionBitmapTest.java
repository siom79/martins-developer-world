package enumset;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilePermissionBitmapTest {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        for(int i=0; i<1000; i++) {
            FilePermissionBitmap filePermissions = new FilePermissionBitmap();
            filePermissions.setPermissions(FilePermissionBitmap.OWNER_READ, FilePermissionBitmap.OWNER_WRITE, FilePermissionBitmap.OWNER_EXECUTE);
            filePermissions.setPermissions(FilePermissionBitmap.GROUP_READ, FilePermissionBitmap.GROUP_EXECUTE);
            filePermissions.setPermissions(FilePermissionBitmap.OTHER_READ, FilePermissionBitmap.OTHER_EXECUTE);
            filePermissions.hasOwnerPermission(FilePermissionBitmap.OWNER_READ);
            filePermissions.hasOwnerPermission(FilePermissionBitmap.OWNER_WRITE);
            filePermissions.hasOwnerPermission(FilePermissionBitmap.OWNER_EXECUTE);
            filePermissions.hasOwnerPermission(FilePermissionBitmap.GROUP_READ);
            filePermissions.hasOwnerPermission(FilePermissionBitmap.GROUP_WRITE);
            filePermissions.hasOwnerPermission(FilePermissionBitmap.GROUP_EXECUTE);
            filePermissions.hasOwnerPermission(FilePermissionBitmap.OTHER_READ);
            filePermissions.hasOwnerPermission(FilePermissionBitmap.OTHER_WRITE);
            filePermissions.hasOwnerPermission(FilePermissionBitmap.OTHER_EXECUTE);
        }
        long total = System.currentTimeMillis() - start;
        System.out.println(String.format("Total: %s ms", total));
    }
}