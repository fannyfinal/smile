/*
 * Copyright (c) 2010-2025 Haifeng Li. All rights reserved.
 *
 * Smile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Smile.  If not, see <https://www.gnu.org/licenses/>.
 */
package smile.clustering;

import smile.io.Read;
import smile.io.Write;
import smile.datasets.GaussianMixture;
import smile.datasets.USPS;
import smile.math.MathEx;
import smile.validation.metric.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Haifeng Li
 */
public class KMeansTest {
    GaussianMixture mixture = GaussianMixture.generate();
    double[][] x = mixture.x();
    int[] y = mixture.y();

    public KMeansTest() {

    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testBBD4() {
        System.out.println("BBD 4");
        MathEx.setSeed(19650218); // to get repeatable results.
        KMeans model = KMeans.fit(x, 4);
        System.out.println(model);

        double r = RandIndex.of(y, model.y);
        double r2 = AdjustedRandIndex.of(y, model.y);
        System.out.format("Training rand index = %.2f%%, adjusted rand index = %.2f%%%n", 100.0 * r, 100.0 * r2);
        assertEquals(0.6111, r, 1E-4);
        assertEquals(0.2475, r2, 1E-4);

        System.out.format("MI = %.2f%n", MutualInformation.of(y, model.y));
        System.out.format("NMI.joint = %.2f%%%n", 100 * NormalizedMutualInformation.joint(y, model.y));
        System.out.format("NMI.max = %.2f%%%n", 100 * NormalizedMutualInformation.max(y, model.y));
        System.out.format("NMI.min = %.2f%%%n", 100 * NormalizedMutualInformation.min(y, model.y));
        System.out.format("NMI.sum = %.2f%%%n", 100 * NormalizedMutualInformation.sum(y, model.y));
        System.out.format("NMI.sqrt = %.2f%%%n", 100 * NormalizedMutualInformation.sqrt(y, model.y));
    }

    @Test
    public void testLloyd4() {
        System.out.println("Lloyd 4");
        MathEx.setSeed(19650218); // to get repeatable results.
        KMeans model = KMeans.lloyd(x, 4);
        System.out.println(model);

        double r = RandIndex.of(y, model.y);
        double r2 = AdjustedRandIndex.of(y, model.y);
        System.out.format("Training rand index = %.2f%%, adjusted rand index = %.2f%%%n", 100.0 * r, 100.0 * r2);
        assertEquals(0.6111, r, 1E-4);
        assertEquals(0.2475, r2, 1E-4);

        System.out.format("MI = %.2f%n", MutualInformation.of(y, model.y));
        System.out.format("NMI.joint = %.2f%%%n", 100 * NormalizedMutualInformation.joint(y, model.y));
        System.out.format("NMI.max = %.2f%%%n", 100 * NormalizedMutualInformation.max(y, model.y));
        System.out.format("NMI.min = %.2f%%%n", 100 * NormalizedMutualInformation.min(y, model.y));
        System.out.format("NMI.sum = %.2f%%%n", 100 * NormalizedMutualInformation.sum(y, model.y));
        System.out.format("NMI.sqrt = %.2f%%%n", 100 * NormalizedMutualInformation.sqrt(y, model.y));
    }

    @Test
    public void testBBD64() {
        System.out.println("BBD 64");
        MathEx.setSeed(19650218); // to get repeatable results.
        KMeans model = KMeans.fit(x, 64);
        System.out.println(model);

        double r = RandIndex.of(y, model.y);
        double r2 = AdjustedRandIndex.of(y, model.y);
        System.out.format("Training rand index = %.2f%%, adjusted rand index = %.2f%%%n", 100.0 * r, 100.0 * r2);
        assertEquals(0.4714, r, 1E-4);
        assertEquals(0.0185, r2, 1E-4);

        System.out.format("MI = %.2f%n", MutualInformation.of(y, model.y));
        System.out.format("NMI.joint = %.2f%%%n", 100 * NormalizedMutualInformation.joint(y, model.y));
        System.out.format("NMI.max = %.2f%%%n", 100 * NormalizedMutualInformation.max(y, model.y));
        System.out.format("NMI.min = %.2f%%%n", 100 * NormalizedMutualInformation.min(y, model.y));
        System.out.format("NMI.sum = %.2f%%%n", 100 * NormalizedMutualInformation.sum(y, model.y));
        System.out.format("NMI.sqrt = %.2f%%%n", 100 * NormalizedMutualInformation.sqrt(y, model.y));
    }

    @Test
    public void testLloyd64() {
        System.out.println("Lloyd 64");
        MathEx.setSeed(19650218); // to get repeatable results.
        KMeans model = KMeans.lloyd(x, 64);
        System.out.println(model);

        double r = RandIndex.of(y, model.y);
        double r2 = AdjustedRandIndex.of(y, model.y);
        System.out.format("Training rand index = %.2f%%, adjusted rand index = %.2f%%%n", 100.0 * r, 100.0 * r2);
        assertEquals(0.4714, r, 1E-4);
        assertEquals(0.0185, r2, 1E-4);

        System.out.format("MI = %.2f%n", MutualInformation.of(y, model.y));
        System.out.format("NMI.joint = %.2f%%%n", 100 * NormalizedMutualInformation.joint(y, model.y));
        System.out.format("NMI.max = %.2f%%%n", 100 * NormalizedMutualInformation.max(y, model.y));
        System.out.format("NMI.min = %.2f%%%n", 100 * NormalizedMutualInformation.min(y, model.y));
        System.out.format("NMI.sum = %.2f%%%n", 100 * NormalizedMutualInformation.sum(y, model.y));
        System.out.format("NMI.sqrt = %.2f%%%n", 100 * NormalizedMutualInformation.sqrt(y, model.y));
    }

    @Test
    public void testUSPS() throws Exception {
        System.out.println("USPS");
        MathEx.setSeed(19650218); // to get repeatable results.
        var usps = new USPS();
        double[][] x = usps.x();
        int[] y = usps.y();
        double[][] testx = usps.testx();
        int[] testy = usps.testy();

        KMeans model = KMeans.fit(x, 10, 100, 4);
        System.out.println(model);

        double r = RandIndex.of(y, model.y);
        double r2 = AdjustedRandIndex.of(y, model.y);
        System.out.format("Training rand index = %.2f%%, adjusted rand index = %.2f%%%n", 100.0 * r, 100.0 * r2);
        assertEquals(0.9063, r, 1E-4);
        assertEquals(0.5148, r2, 1E-4);

        System.out.format("MI = %.2f%n", MutualInformation.of(y, model.y));
        System.out.format("NMI.joint = %.2f%%%n", 100 * NormalizedMutualInformation.joint(y, model.y));
        System.out.format("NMI.max = %.2f%%%n", 100 * NormalizedMutualInformation.max(y, model.y));
        System.out.format("NMI.min = %.2f%%%n", 100 * NormalizedMutualInformation.min(y, model.y));
        System.out.format("NMI.sum = %.2f%%%n", 100 * NormalizedMutualInformation.sum(y, model.y));
        System.out.format("NMI.sqrt = %.2f%%%n", 100 * NormalizedMutualInformation.sqrt(y, model.y));

        int[] p = new int[testx.length];
        for (int i = 0; i < testx.length; i++) {
            p[i] = model.predict(testx[i]);
        }
            
        r = RandIndex.of(testy, p);
        r2 = AdjustedRandIndex.of(testy, p);
        System.out.format("Testing rand index = %.2f%%, adjusted rand index = %.2f%%%n", 100.0 * r, 100.0 * r2);
        assertEquals(0.8942, r, 1E-4);
        assertEquals(0.4540, r2, 1E-4);

        java.nio.file.Path temp = Write.object(model);
        Read.object(temp);
    }
}