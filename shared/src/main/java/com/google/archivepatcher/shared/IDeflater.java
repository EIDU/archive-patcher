package com.google.archivepatcher.shared;

public interface IDeflater {
    void setInput(byte[] b, int off, int len);
    void setInput(byte[] b);
    void setDictionary(byte[] b, int off, int len);
    void setDictionary(byte[] b);
    void setStrategy(int strategy);
    void setLevel(int level);
    boolean needsInput();
    void finish();
    boolean finished();
    int deflate(byte[] b, int off, int len);
    int deflate(byte[] b);
    int deflate(byte[] b, int off, int len, int flush);
    int getAdler();
    int getTotalIn();
    long getBytesRead();
    int getTotalOut();
    long getBytesWritten();
    void reset();
    void end();
}
