package utils.vfs;

import java.util.Iterator;

public interface VFS {

    public boolean isExist(String path);
    public boolean isDirectory(String path);
    public String getAbsolutePath(String file);
    public byte[] getBytes(String file);
    public String getUFT8Text(String file);
    public Iterator<String> getIterator(String startDir);
}
