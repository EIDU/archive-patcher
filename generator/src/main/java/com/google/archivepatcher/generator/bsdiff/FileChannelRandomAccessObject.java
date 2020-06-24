package com.google.archivepatcher.generator.bsdiff;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelRandomAccessObject implements RandomAccessObject {
    private ByteBuffer sharedBuffer = ByteBuffer.allocate(4 * 1024);
    private FileChannel channel;

    public FileChannelRandomAccessObject(FileChannel channel) {
        this.channel = channel;
    }

    @Override
    public long length() throws IOException {
        return channel.size();
    }

    @Override
    public void seek(long pos) throws IOException {
        channel.position(pos);
    }

    @Override
    public void seekToIntAligned(long pos) throws IOException {
        seek(pos * 4);
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        while (buffer.position() < buffer.limit())
            if (channel.read(sharedBuffer) < 0)
                throw new EOFException();
    }

    private void readExactly(int len) throws IOException {
        readFully(sharedBuffer.array(), 0, len);
        sharedBuffer.position(0);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(b, off, len);
        while (buffer.position() < buffer.limit())
            if (channel.read(buffer) < 0)
                throw new EOFException();
    }

    @Override
    public int skipBytes(int n) throws IOException {
        int bytesToSkip = Math.min(n, (int)channel.size() - (int)channel.position() - 1);
        channel.position(channel.position() + n);
        return bytesToSkip;
    }

    @Override
    public boolean readBoolean() throws IOException {
        return readByte() != 0;
    }

    @Override
    public byte readByte() throws IOException {
        readExactly(1);
        return sharedBuffer.get(0);
    }

    @Override
    public int readUnsignedByte() throws IOException {
        readExactly(1);
        return ((int)sharedBuffer.get(0)) & 0xFF;
    }

    @Override
    public short readShort() throws IOException {
        readExactly(2);
        return sharedBuffer.getShort();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        readExactly(2);
        return ((int)sharedBuffer.getShort()) & 0xFFFF;
    }

    @Override
    public char readChar() throws IOException {
        readExactly(1);
        return sharedBuffer.getChar();
    }

    @Override
    public int readInt() throws IOException {
        readExactly(4);
        return sharedBuffer.getInt();
    }

    @Override
    public long readLong() throws IOException {
        readExactly(8);
        return sharedBuffer.getLong();
    }

    @Override
    public float readFloat() throws IOException {
        readExactly(4);
        return sharedBuffer.getFloat();
    }

    @Override
    public double readDouble() throws IOException {
        readExactly(8);
        return sharedBuffer.getDouble();
    }

    @Override
    public String readLine() throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String readUTF() throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void write(int b) throws IOException {
        writeByte(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        channel.write(ByteBuffer.wrap(b));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        channel.write(ByteBuffer.wrap(b, off, len));
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
        writeByte(v ? 1 : 0);
    }

    @Override
    public void writeByte(int v) throws IOException {
        sharedBuffer.position(0);
        sharedBuffer.put((byte)v);
        channel.write(ByteBuffer.wrap(sharedBuffer.array(), 0, 1));
    }

    @Override
    public void writeShort(int v) throws IOException {
        sharedBuffer.position(0);
        sharedBuffer.putShort((short)v);
        channel.write(ByteBuffer.wrap(sharedBuffer.array(), 0, 2));
    }

    @Override
    public void writeChar(int v) throws IOException {
        sharedBuffer.position(0);
        sharedBuffer.putChar((char)v);
        channel.write(ByteBuffer.wrap(sharedBuffer.array(), 0, 1));
    }

    @Override
    public void writeInt(int v) throws IOException {
        sharedBuffer.position(0);
        sharedBuffer.putInt(v);
        channel.write(ByteBuffer.wrap(sharedBuffer.array(), 0, 4));
    }

    @Override
    public void writeLong(long v) throws IOException {
        sharedBuffer.position(0);
        sharedBuffer.putLong(v);
        channel.write(ByteBuffer.wrap(sharedBuffer.array(), 0, 8));
    }

    @Override
    public void writeFloat(float v) throws IOException {
        sharedBuffer.position(0);
        sharedBuffer.putFloat(v);
        channel.write(ByteBuffer.wrap(sharedBuffer.array(), 0, 4));
    }

    @Override
    public void writeDouble(double v) throws IOException {
        sharedBuffer.position(0);
        sharedBuffer.putDouble(v);
        channel.write(ByteBuffer.wrap(sharedBuffer.array(), 0, 8));
    }

    @Override
    public void writeBytes(String s) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void writeChars(String s) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void writeUTF(String s) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
