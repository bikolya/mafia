package utils.vfs;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class VFSImpl implements VFS {

    private String root = "src/main/resources/";

    public VFSImpl() {}

    public VFSImpl(String rootPath) {
        root = rootPath;
    }

    @Override
    public boolean isExist(String path) {
        return (new File(getFullPath(path))).exists();
    }

    @Override
    public boolean isDirectory(String path) {
        return new File(root + path).isDirectory();
    }

    @Override
    public String getAbsolutePath(String file) {
        return (new File(getFullPath(file))).getAbsolutePath();
    }

    @Override
    public byte[] getBytes(String file)
    {
        DataInputStream ds = getInputStream(file);

        byte[] data = new byte[0];
        try {
            data = new byte[ds.available()];
            ds.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ds.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Override
    public String getUFT8Text(String file)
    {
        DataInputStream ds = getInputStream(file);;
        try {
            InputStreamReader isr = new InputStreamReader(ds, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            while (true) {
                String line = br.readLine();
                if(line == null)
                    break;
                sb.append(line);
            }
            br.close();
            return sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterator<String> getIterator(String startDir) {
        return new VFSIterator(startDir);
    }


    private String getFullPath(String path)
    {
        return root + path;
    }

    private DataInputStream getInputStream(String file)
    {
        FileInputStream fs = null;
        if(isExist(file)) {
            try {
                fs = new FileInputStream(getAbsolutePath(file));
            } catch (FileNotFoundException IGNORE) {}
        } else {
            return null;
        }
        return new DataInputStream(fs);
    }

    private class VFSIterator implements Iterator<String>{

        private Queue<File> files = new LinkedList<File>();

        public VFSIterator(String path){
            files.add(new File(getFullPath(path)));
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        @Override
        public String next() {
            File file = files.peek();
            if(file.isDirectory()){
                for(File subFile : file.listFiles()){
                    files.add(subFile);
                }
            }

            return files.poll().getAbsolutePath();
        }

        @Override
        public void remove() {
            files.poll();
        }
    }
}