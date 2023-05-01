package com.google.archivepatcher.shared;

import java.util.zip.Deflater;

public class DefaultDeflater implements IDeflater {
    private final Deflater deflater;

    public DefaultDeflater() {
        this(new Deflater());
    }

    public DefaultDeflater(int level) {
        this(new Deflater(level));
    }

    public DefaultDeflater(int level, boolean nowrap) {
        this(new Deflater(level, nowrap));
    }

    public DefaultDeflater(Deflater deflater) {
        this.deflater = deflater;
    }

    @Override
    public void setInput(byte[] b, int off, int len) {
        deflater.setInput(b, off, len);
    }

    @Override
    public void setInput(byte[] b) {
        deflater.setInput(b);
    }

    @Override
    public void setDictionary(byte[] b, int off, int len) {
        deflater.setDictionary(b, off, len);
    }

    @Override
    public void setDictionary(byte[] b) {
        deflater.setDictionary(b);
    }

    @Override
    public void setStrategy(int strategy) {
        deflater.setStrategy(strategy);
    }

    @Override
    public void setLevel(int level) {
        deflater.setLevel(level);
    }

    @Override
    public boolean needsInput() {
        return deflater.needsInput();
    }

    @Override
    public void finish() {
        deflater.finish();
    }

    @Override
    public boolean finished() {
        return deflater.finished();
    }

    @Override
    public int deflate(byte[] b, int off, int len) {
        return deflater.deflate(b, off, len);
    }

    @Override
    public int deflate(byte[] b) {
        return deflater.deflate(b);
    }

    @Override
    public int deflate(byte[] b, int off, int len, int flush) {
        return deflater.deflate(b, off, len, flush);
    }

    @Override
    public int getAdler() {
        return deflater.getAdler();
    }

    @Override
    public int getTotalIn() {
        return deflater.getTotalIn();
    }

    @Override
    public long getBytesRead() {
        return deflater.getBytesRead();
    }

    @Override
    public int getTotalOut() {
        return deflater.getTotalOut();
    }

    @Override
    public long getBytesWritten() {
        return deflater.getBytesWritten();
    }

    @Override
    public void reset() {
        deflater.reset();
    }

    @Override
    public void end() {
        deflater.end();
    }
}
