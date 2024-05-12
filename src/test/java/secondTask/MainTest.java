package secondTask;

import static org.junit.jupiter.api.Assertions.*;
import static org.secondTask.Main.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

/**
 * Test class.
 */
public class MainTest {
    @Test
    void testMin() {
        List<Integer> numbers = new ArrayList<>(new Random().ints(100).boxed().toList());
        numbers.add(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, _min(numbers));
    }

    @Test
    void testMax() {
        List<Integer> numbers = new ArrayList<>(new Random().ints(100).boxed().toList());
        numbers.add(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, _max(numbers));
    }

    @Test
    void testSum() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        assertEquals(49, _sum(numbers));
    }

    @Test
    void testMultZero() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        assertEquals(0, _mult(numbers));
    }

    @Test
    void testMult() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            numbers.add(i);
        }
        assertEquals(362880, _mult(numbers));
    }

    @Test
    void testReadFile() {
        assertThrows(FileNotFoundException.class, () -> readFile("differentFile.xlk"));
    }

    @Test
    void testTimeOfExecution() throws IOException {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries firstSeries = new XYSeries("График зависимости кол-ва элементов от времени");
        firstSeries.add(0, 0);
        for (int sizeOfArray = 40; sizeOfArray <= 4000000; sizeOfArray *= 10) {
            List<Integer> array = buildArray(sizeOfArray);
            firstSeries.add(sizeOfArray, getAverageTime(array));
            System.out.println(sizeOfArray);
        }
        dataset.addSeries(firstSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "График зависимости кол-ва элементов от времени",
                "Длина массива, шт",
                "Время, мс",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
        XYPlot xyPlot = chart.getXYPlot();
        xyPlot.setAxisOffset(new RectangleInsets(1, 1, 1, 1));
        ValueAxis axis = xyPlot.getDomainAxis();
        axis.setAxisLineVisible(false);
        java.io.File file = new java.io.File("chart.png");
        ChartUtils.saveChartAsPNG(file, chart, 1000, 1000);
    }

    private List<Integer> buildArray(int length) {
        return new ArrayList<>(new Random().ints(length).boxed().toList());
    }

    private long getAverageTime(List<Integer> array) {
        long time = 0;

        for (int i = 0; i < 3; ++i) {
            long startTime = System.nanoTime();
            _min(array);
            _max(array);
            _mult(array);
            _sum(array);
            long endTime = System.nanoTime();
            time += endTime - startTime;
        }
        return time / 3000000;
    }
}
