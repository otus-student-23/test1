package org.example;

import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        var outputStream = new ByteArrayOutputStream();
        var authorDatumWriter = new SpecificDatumWriter<>(Author.class);
        var encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
        authorDatumWriter.write(new Author("author_a"), encoder);
        authorDatumWriter.write(new Author("author_a"), encoder);
        encoder.flush();

        byte[] bytes = outputStream.toByteArray();
        System.out.println(Arrays.toString(bytes));

        var authorDatumReader = new SpecificDatumReader<>(Author.class);
        var inputStream = new ByteArrayInputStream(bytes);
        var decoder = DecoderFactory.get().binaryDecoder(inputStream, null);
        Author author = authorDatumReader.read(null, decoder);
        System.out.println(author);
        author = authorDatumReader.read(null, decoder);
        System.out.println(author);
    }
}