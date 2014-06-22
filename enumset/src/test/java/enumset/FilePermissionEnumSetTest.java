package enumset;

import org.junit.Test;

public class FilePermissionEnumSetTest {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            FilePermissionEnumSet filePermissions = new FilePermissionEnumSet();
            filePermissions.setOwnerPermissions(FilePermission.READ, FilePermission.WRITE, FilePermission.EXECUTE);
            filePermissions.setGroupPermissions(FilePermission.READ, FilePermission.EXECUTE);
            filePermissions.setOtherPermissions(FilePermission.READ, FilePermission.EXECUTE);
            for (FilePermission filePermission : FilePermission.values()) {
                filePermissions.hasOwnerPermission(filePermission);
                filePermissions.hasGroupPermission(filePermission);
                filePermissions.hasOtherPermission(filePermission);
            }
        }
        long total = System.currentTimeMillis() - start;
        System.out.println(String.format("Total time: %s ms", total));
    }
}