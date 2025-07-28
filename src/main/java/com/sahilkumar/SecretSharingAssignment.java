package com.sahilkumar;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SecretSharingAssignment {

    private static BigInteger decodeValue(String val, int base) {
        return new BigInteger(val, base);
    }

    private static BigInteger lagrangeInterpolationAtZero(List<BigInteger> xs, List<BigInteger> ys) {
        int k = xs.size();
        BigInteger secret = BigInteger.ZERO;

        for (int j = 0; j < k; j++) {
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;
            for (int m = 0; m < k; m++) {
                if (m != j) {
                    numerator = numerator.multiply(xs.get(m).negate()); // (0 - x_m) = -x_m
                    denominator = denominator.multiply(xs.get(j).subtract(xs.get(m)));
                }
            }
            BigInteger term = ys.get(j).multiply(numerator).divide(denominator);
            secret = secret.add(term);
        }
        return secret;
    }
    private static BigInteger reconstructSecret(String jsonContent) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonContent);
        JsonNode keysNode = rootNode.get("keys");
        int n = keysNode.get("n").asInt();
        int k = keysNode.get("k").asInt();

        List<BigInteger> xs = new ArrayList<>();
        List<BigInteger> ys = new ArrayList<>();

        int collected = 0;
        for (var it = rootNode.fieldNames(); it.hasNext(); ) {
            String key = it.next();
            if ("keys".equals(key)) continue;
            if (collected == k) break; // only need k points

            JsonNode shareNode = rootNode.get(key);
            int x = Integer.parseInt(key);
            int base = Integer.parseInt(shareNode.get("base").asText());
            String val = shareNode.get("value").asText();

            BigInteger y = decodeValue(val, base);
            xs.add(BigInteger.valueOf(x));
            ys.add(y);
            collected++;
        }

        return lagrangeInterpolationAtZero(xs, ys);
    }

    public static void main(String[] args) throws Exception {
        String test1Json = Files.readString(Path.of("/Users/sahilkumar/Desktop/HASHIRA/src/main/java/com/sahilkumar/test1.json"));
        String test2Json = Files.readString(Path.of("/Users/sahilkumar/Desktop/HASHIRA/src/main/java/com/sahilkumar/test2.json"));


        BigInteger secret1 = reconstructSecret(test1Json);
        BigInteger secret2 = reconstructSecret(test2Json);

        System.out.println("Secret for test case 1: " + secret1);
        System.out.println("Secret for test case 2: " + secret2);
    }
}
