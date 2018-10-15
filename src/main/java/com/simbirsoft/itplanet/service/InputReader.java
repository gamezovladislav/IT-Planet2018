package com.simbirsoft.itplanet.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class InputReader {
    StringTokenizer tokenizer;
    BufferedReader reader;

    public InputReader(InputStreamReader inputStreamReader) {
        reader = new BufferedReader(inputStreamReader, 1 << 16);
        tokenizer = null;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }
}
